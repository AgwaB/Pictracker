package com.example.pictracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by MECSL on 2017-09-08.
 */

public class Nickname extends Activity {
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.TranslucentStatusBar);
        setContentView(R.layout.set_nickname);

//        Intent intent = getIntent();
//
//        textView.setText(intent.getStringExtra("userInfoParse"));
    }
}
