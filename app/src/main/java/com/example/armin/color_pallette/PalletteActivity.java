package com.example.armin.color_pallette;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PalletteActivity extends AppCompatActivity {

    public static final String LOG_CAT = PalletteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pallette);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addColor();

            }
        });
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

    private void addColor() {
        Intent intent = new Intent(PalletteActivity.this, ColorActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pallette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            addColor();
            return true;
        }else  if (id == R.id.action_clear){

        }



        return super.onOptionsItemSelected(item);
    }
}
