package com.example.pictracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;

public class NewPhotoActivity extends Activity {
    private ArrayList<File> fileList = new ArrayList<File>();
    private ArrayList pathList = new ArrayList();
    String[] item;
    GridView gridView;
    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 100 :
                fileList = new ArrayList();
                File dcim = new File("/storage/emulated/0/DCIM/");
                getFile(dcim);
                File download = new File("/storage/emulated/0/Download/");
                getFile(download);
                File pictures = new File("/storage/emulated/0/Pictures/");
                getFile(pictures);
                for (int i = 0; i < fileList.size(); i++) {
                    String stringFile = fileList.get(i).toString();
                    Log.d("TAG", "stringFile : " + stringFile);

                }
                break;
            default :
        }
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);

        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(new ImageAdapter(this));

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//            // Send intent to SingleViewActivity
//            Intent i = new Intent(getApplicationContext(), FullViewActivity.class);
//
//            // Pass image index
//            i.putExtra("id", position);
//            startActivity(i);
//            }
//        });
        Log.d("TAG", "TEST LOG!!!!!!!!!!!!!!!!!!!!!");
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(Build.VERSION.SDK_INT >= 23 && permission == PackageManager.PERMISSION_DENIED) {
            String arr[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(arr, 100);
        } else {
            ArrayList fileList = new ArrayList();
            //File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            File dcim = new File("/storage/emulated/0/DCIM/");
            getFile(dcim);
            File download = new File("/storage/emulated/0/Download/");
            getFile(download);
            File pictures = new File("/storage/emulated/0/Pictures/");
            getFile(pictures);
            for (int i = 0; i < fileList.size(); i++) {
                String stringFile = fileList.get(i).toString();
                Log.d("TAG", "stringFile : " + stringFile);
            }
        }
    }

    public ArrayList<File> getFile(File dir) {
        final ArrayList fileList = new ArrayList();

        final File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if(listFile[i].isDirectory()&&!(listFile[i].getName().contains(".thumbnails"))){
                    fileList.add(listFile[i]);
                    Log.d("TAG", "listFile : "+ listFile[i].getName().toString());
                    getFile(listFile[i]);
                } else {
                    if ((listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".png")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif"))
                            && !(listFile[i].getName().contains(".thumbnails"))) {
                        fileList.add(listFile[i]);
                        Log.d("TAG", listFile[i].getName().toString());
                    }
                }
            }
        }

        for (int i = 0; i < fileList.size(); i++) {
            String stringFile = fileList.get(i).toString();
            Log.d("TAG", "file : " + stringFile);
            String[] aa = stringFile.split("/");
            String myPath = "";
            for (int j = 0; j < aa.length - 1; j++) {
                myPath += aa[j] + "/";
            }
            if (!pathList.contains(myPath)) {
                pathList.add(pathList.size(), myPath);
                Log.d("MyTag", "path : " + myPath);
            }
        }

        item = new String[pathList.size()];
        for (int i = 0; i < pathList.size(); i++) {
            String[] bb = pathList.get(i).toString().split("/");
            item[i] = bb[bb.length-1];
        }

        Spinner spinner = (Spinner)findViewById(R.id.gallery_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return fileList;
    }

    public void open_main_activity(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void openNewPhotoToPicshotSetting(View view){
        Intent i = new Intent(this, NewPicshotActivity.class);
        startActivity(i);
    }
//        public void pass(View view){
//        EditText editText = (EditText)findViewById(R.id.editText);
//        String st = editText.getText().toString();
//
//        Intent intent = new Intent(this, DataPassActivity.class);
//        intent.putExtra("string", st);
//        startActivity(intent);
//    }
}
