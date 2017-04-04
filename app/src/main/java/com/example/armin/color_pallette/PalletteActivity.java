package com.example.armin.color_pallette;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PalletteActivity extends AppCompatActivity implements ColorAdapter.ColorClickedListener {

    public static final String LOG_CAT = PalletteActivity.class.getSimpleName();
    public static final int REQUEST_CODE_CREATE = 1;
    public static final int REQUEST_CODE_EDIT = 2;
    @BindView(com.example.armin.color_pallette.R.id.colorRecyclerView)
    RecyclerView colorRecyclerView;
    private FloatingActionButton fab;
    private ColorAdapter colorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.armin.color_pallette.R.layout.activity_pallette);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(com.example.armin.color_pallette.R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(com.example.armin.color_pallette.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addColor();
            }
        });

        colorAdapter = new ColorAdapter(getLayoutInflater(),
                PreferenceManager.getDefaultSharedPreferences(this));
        colorAdapter.setColorClickedListener(this);
        colorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        colorRecyclerView.setAdapter(colorAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                colorAdapter.remove(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(colorRecyclerView);
    }

    private void addColor() {
        Intent intent = new Intent(PalletteActivity.this, ColorActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE_CREATE) {
                String colorHEX = data.getStringExtra(ColorActivity.COLOR_IN_HEX_KEY);
                Snackbar.make(fab, getString(com.example.armin.color_pallette.R.string.new_color_created, colorHEX), Snackbar.LENGTH_LONG)
                        .show();
                colorAdapter.add(colorHEX);
            } else if (requestCode == REQUEST_CODE_EDIT) {
                String colorHEX = data.getStringExtra(ColorActivity.COLOR_IN_HEX_KEY);
                String oldColor = data.getStringExtra(ColorActivity.OLD_COLOR_KEY);

                colorAdapter.replace(oldColor, colorHEX);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.armin.color_pallette.R.menu.menu_pallette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.example.armin.color_pallette.R.id.action_add) {
            addColor();
            return true;
        } else if (id == com.example.armin.color_pallette.R.id.action_clear) {
            colorAdapter.clear();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorClicked(String colorInHex) {
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.OLD_COLOR_KEY, colorInHex);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }
}
