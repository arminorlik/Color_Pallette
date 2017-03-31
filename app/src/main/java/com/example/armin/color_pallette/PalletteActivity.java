package com.example.armin.color_pallette;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PalletteActivity extends AppCompatActivity {

    public static final String LOG_CAT = PalletteActivity.class.getSimpleName();
    public static final int REQUEST_CODE_CREATE = 1;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pallette);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addColor();
            }
        });
    }

    private void addColor() {
        Intent intent = new Intent(PalletteActivity.this, ColorActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE && resultCode == RESULT_OK) {
            String colorHEX = data.getStringExtra(ColorActivity.COLOR_IN_HEX);
            Snackbar.make(fab, getString(R.string.new_color_created, colorHEX), Snackbar.LENGTH_LONG)
                    .show();

        }
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
        } else if (id == R.id.action_clear) {

        }

        return super.onOptionsItemSelected(item);
    }
}
