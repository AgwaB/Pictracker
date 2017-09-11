package com.example.pictracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pictracker.RetrofitService.APIService;
import com.example.pictracker.RetrofitService.ApiUtils;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity{
    private  String OAUTH_CLIENT_ID = "NKAQU3DVjYs2fffxjTQ4";
    private  String OAUTH_CLIENT_SECRET = "iv9VMvr2Y6";
    private  String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";
    private OAuthLogin mOAuthLoginModule;
    private OAuthLoginButton mOAuthLoginButton;
    public Map<String,String>       mUserInfoMap;

    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = getApplicationContext();


        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setBgResourceId(R.drawable.naver_login_green);
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOAuthLoginModule.startOauthLoginActivity(MainActivity.this, mOAuthLoginHandler);
            }
        });



        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                mContext
                ,OAUTH_CLIENT_ID
                ,OAUTH_CLIENT_SECRET
                ,OAUTH_CLIENT_NAME
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );

    }




    /**
     * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
     객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                String tokenType = mOAuthLoginModule.getTokenType(mContext);
                Log.d("TAG",accessToken);
                Log.d("TAG",refreshToken);
                Log.d("TAG",String.valueOf(expiresAt));
                Log.d("TAG",tokenType);
                Log.d("TAG", mOAuthLoginModule.getState(mContext).toString());
                new RequestApiTask().execute();
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> { //네이버와 애플리케이션간의 연동해제
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = mOAuthLoginModule.logoutAndDeleteToken(mContext);

            if (!isSuccessDeleteToken) {
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                Log.d("TAG", "errorCode:" + mOAuthLoginModule.getLastErrorCode(mContext));
                Log.d("TAG", "errorDesc:" + mOAuthLoginModule.getLastErrorDesc(mContext));
            }

            return null;
        }
        protected void onPostExecute(Void v) {
        }
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> { // 로그인
        @Override
        protected void onPreExecute() {
         //   mApiResultText.setText((String) "");
        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginModule.getAccessToken(mContext);
            return mOAuthLoginModule.requestApi(mContext, at, url);
        }
        protected void onPostExecute(String content) {// doInBackground return 값을 content로 받음

            Intent intent = new Intent(getApplicationContext(), Nickname.class);
            intent.putExtra("userInfoParse",content);
            startActivity(intent);
           // mApiResultText.setText((String) content);
        }
    }

    private class RefreshTokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return mOAuthLoginModule.refreshAccessToken(mContext);
        }
        protected void onPostExecute(String res) {
          //  updateView();
        }
    }
}
