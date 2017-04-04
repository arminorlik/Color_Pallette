package com.example.armin.color_pallette;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String COLOR_IN_HEX_KEY = "COLOR_IN_HEX_KEY";
    public static final String OLD_COLOR_KEY = "old color";
    @BindView(R.id.redSeekBar)
    SeekBar redSeekBar;
    @BindView(R.id.greenSeekBar)
    SeekBar greenSeekBar;
    @BindView(R.id.blueSeekBar)
    SeekBar blueSeekBar;
    @BindView(R.id.generateButton)
    Button generateButton;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.idLinearLayout)
    LinearLayout idLinearLayout;
    @BindView(R.id.redSeekBartv)
    TextView redSeekBartv;
    @BindView(R.id.greenSeekBartv)
    TextView greenSeekBartv;
    @BindView(R.id.blueSeekBartv)
    TextView blueSeekBartv;
    private ActionBar actionBar;
    Random random = new Random();
    private int red;
    private int blue;
    private int green;
    private String oldColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);

        actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        redSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        Intent intent = getIntent();
        oldColor = intent.getStringExtra(OLD_COLOR_KEY);

        if (oldColor != null) {
            int color = Color.parseColor(oldColor);
            red = Color.red(color);
            blue = Color.blue(color);
            green = Color.green(color);

            updateSeekBars();
            updateBackgroundColor();

            generateButton.setVisibility(View.GONE);
            actionBar.setTitle(R.string.edit_color);
        }
    }

    @OnClick(R.id.generateButton)
    public void generate() {

        red = random.nextInt(256);
        green = random.nextInt(256);
        blue = random.nextInt(256);

        updateSeekBars();

        updateBackgroundColor();
    }

    private void updateSeekBars() {
        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);
    }

    private void updateBackgroundColor() {
        int color = Color.rgb(red, green, blue);
        int textColor = PalletteActivity.getTextColorFromColor(color);

        blueSeekBartv.setTextColor(textColor);
        greenSeekBartv.setTextColor(textColor);
        redSeekBartv.setTextColor(textColor);

        idLinearLayout.setBackgroundColor(color);
    }

    @OnClick(R.id.saveButton)
    public void save() {
        Intent intent = new Intent();
        intent.putExtra(COLOR_IN_HEX_KEY, String.format("#%02X%02X%02X", red, green, blue));
        if (oldColor != null) {
            intent.putExtra(OLD_COLOR_KEY, oldColor);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.redSeekBar:
                red = i;
                break;
            case R.id.blueSeekBar:
                blue = i;
                break;
            case R.id.greenSeekBar:
                green = i;
                break;
        }
        updateBackgroundColor();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("blue", blue);
        outState.putInt("green", green);
        outState.putInt("red", red);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        blue = savedInstanceState.getInt("blue");
        red = savedInstanceState.getInt("red");
        green = savedInstanceState.getInt("green");
    }
}
