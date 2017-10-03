package com.example.pictracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

/**
 * Created by leesd on 2017-10-04.
 */

public class AppMain extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;


                switch (item.getItemId()) {
                    case R.id.action_one:
                        break;
                    case R.id.action_two:
                        break;
                    case R.id.action_three:
                        break;
                    case R.id.action_four:
                        break;
                    case R.id.action_five:
                        fragment = new AccountSetting();
                        break;
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment);
                fragmentTransaction.commit();


                return true;
            }
        });
    }
}
