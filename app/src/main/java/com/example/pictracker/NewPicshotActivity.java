package com.example.pictracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class NewPicshotActivity extends Activity {

    static boolean first = true;
    Context mContext;
    // Button completeButton = (Button)findViewById(R.id.btn_complete);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_picshot);

        if(first == false){
            Intent intent = getIntent();
            String data = intent.getStringExtra("PicshotTip");
            Log.d("TIP","TIP : "+data);
        }
    }


    public void openNewPhotoAdd(View view){
        Intent i = new Intent(this, NewPhotoActivity.class);
        startActivity(i);
    }

    public void canclePopup(View view){
        Intent i = new Intent(this, NewPicshotActivity.class);
        startActivity(i);
    }

    public void completeTag(View vienw){
        Intent intent = new Intent(this, NewPicshotActivity.class);
        startActivity(intent);
    }


    public void completePicshotTip(View view){
        final EditText editText = (EditText)findViewById(R.id.picshot_tip);
        String tip =  editText.getText().toString();
        Log.d("TipTAG","PicshotTip : "+tip);

        //String data = "testing string data";

        first = false;

        Intent intent = new Intent(getApplicationContext(), NewPicshotActivity.class);
        intent.putExtra("PicshotTip", tip);
        startActivity(intent);



//        Intent i = new Intent(this, NewPicshotActivity.class);
//        startActivity(i);
    }

    public void openAlbumUpload (View view){
        Intent i = new Intent(this, AlbumUploadActivity.class);
        startActivity(i);
    }

    public void open_pickshot_tip_popup(View v){
        //클릭시 팝업 윈도우 생성
        PopupWindow popup = new PopupWindow(v);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //팝업으로 띄울 커스텀뷰를 설정하고
        View view = inflater.inflate(R.layout.popup_pickshot_tip, null);
        popup.setContentView(view);
        //팝업의 크기 설정
        popup.setWindowLayoutMode(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //팝업 뷰 터치 되도록
        popup.setTouchable(true);
        //팝업 뷰 포커스도 주고
        popup.setFocusable(true);
        //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        //popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        //인자로 넘겨준 v 아래로 보여주기
        popup.showAsDropDown(v);
    }




    public void open_tag_popup(View v){
        //클릭시 팝업 윈도우 생성
        PopupWindow popup = new PopupWindow(v);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //팝업으로 띄울 커스텀뷰를 설정하고
        View view = inflater.inflate(R.layout.popup_tag, null);
        popup.setContentView(view);
        //팝업의 크기 설정
        popup.setWindowLayoutMode(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //팝업 뷰 터치 되도록
        popup.setTouchable(true);
        //팝업 뷰 포커스도 주고
        popup.setFocusable(true);
        //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        //popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        //인자로 넘겨준 v 아래로 보여주기
        popup.showAsDropDown(v);
    }
}
