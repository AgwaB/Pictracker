package com.example.pictracker.PhothDetailPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pictracker.R;

/**
 * Created by leesd on 2017-09-09.
 */

public class PicDetail extends Activity {

    ImageButton peakShot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_detail);


        peakShot = (ImageButton)findViewById(R.id.peakshot_button);

        peakShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
