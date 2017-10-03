package com.example.pictracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.Toast;

import com.example.pictracker.PhothDetailPage.PicDetail;
import com.example.pictracker.RetrofitService.APIService;
import com.example.pictracker.RetrofitService.ApiUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

///////////https://developers.facebook.com/docs/facebook-login/android/?locale=ko_KR 페이스북 로그인 참고 현재 로그인만 되고 정보 안가져옴
///////////네이버 로그인창으로 이동하기, 필요한 정보 알아서 저장하기


public class MainActivity extends Activity{
    ///////////////네이버/////////////////
    private  String OAUTH_CLIENT_ID = "NKAQU3DVjYs2fffxjTQ4";
    private  String OAUTH_CLIENT_SECRET = "iv9VMvr2Y6";
    private  String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";
    private OAuthLogin mOAuthLoginModule;
    private OAuthLoginButton mOAuthLoginButton;
    ///////////////////////////////////////////////

    /////카카오///
    private SessionCallback callback;      //콜백 선언
    private com.kakao.usermgmt.LoginButton kakaoLoginButton;
    ///////

    //////////////페이스북///////////////
    LoginButton loginButton;
    CallbackManager callbackManager;
    /////////////////////

    private static Context mContext;

    Button kakaoButton, naverButton, facebookButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTheme(R.style.TranslucentStatusBar);
        setContentView(R.layout.activity_main);



        try{
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("keyhash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){
        }catch (NoSuchAlgorithmException e){
        }





        //////////////////카카오///////////////////////////
        callback = new SessionCallback();                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        kakaoLoginButton = (com.kakao.usermgmt.LoginButton)findViewById(R.id.kakao);
    ////////////////////////////////////////////////////////
        kakaoButton = (Button)findViewById(R.id.kakao_login_button);
        naverButton = (Button)findViewById(R.id.naver_login_button);
        facebookButton = (Button)findViewById(R.id.facebook_login_button);

        kakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AppMain.class);
//                startActivity(intent);

                kakaoLoginButton.performClick();

            }
        });

        naverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginButton.performClick();
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();

            }
        });


                ////////////////////////페이스북/////////////////////////////
                callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.facebook);

        loginButton.setReadPermissions("public_profile", "email", "user_friends");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.e("토큰",loginResult.getAccessToken().getToken());
                Log.e("유저아이디",loginResult.getAccessToken().getUserId());
                Log.e("퍼미션 리스트",loginResult.getAccessToken().getPermissions()+"");

                //loginResult.getAccessToken() 정보를 가지고 유저 정보를 가져올수 있습니다.
                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) { //object에 정보가 저장됨.
                                try {
                                    Log.i("user profile",object. toString());
                                    Log.i("user profile",loginResult.getAccessToken().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        }); //loginButton에 callback 등록하면 loginManager에는 등록 안해도 됨.

        ////////////////////////////////////////////////////////


/////////////////////////네이버////////////////////////
        mContext = getApplicationContext();

        OAuthLoginDefine.DEVELOPER_VERSION = true;

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

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
            ///////////////////////////////content에 정보 들어있음
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
/////////////////////////////////////////////////////////////


    /////////////////////Facebook 로그인////////////////////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) { ////카카오
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data); /////페이스북
    }

    ///////////////////////////////////////////////

    ////////////////////////////카카오////////////////////////


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_main); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
///////////////////////////////////////////////
}
