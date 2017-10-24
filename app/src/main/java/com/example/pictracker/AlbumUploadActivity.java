package com.example.pictracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AlbumUploadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_upload);
    }

    public void openNewPicshot (View view){
        Intent i = new Intent(this, NewPicshotActivity.class);
        startActivity(i);

    }
}
