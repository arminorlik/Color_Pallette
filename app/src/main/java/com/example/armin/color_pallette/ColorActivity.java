package com.example.armin.color_pallette;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class ColorActivity extends AppCompatActivity {

    public static final String LOG_CAT = ColorActivity.class.getSimpleName();
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        Log.d(LOG_CAT, "onCreate");

    }




    @Override
    protected void onDestroy() {
        Log.d(LOG_CAT, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.d(LOG_CAT, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_CAT, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_CAT, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_CAT, "onResume");
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
