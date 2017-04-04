package com.example.armin.color_pallette;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armin on 2017-04-04.
 */

class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder> {

    public static final String COLORS_KEY = "color";
    private final LayoutInflater layoutInflater;
    private final SharedPreferences sharedPreferences;
    private List<String> colors = new ArrayList<>();
    private ColorClickedListener colorClickedListener;
    private int position;

    public ColorAdapter(LayoutInflater layoutInflater, SharedPreferences sharedPreferences) {
        this.layoutInflater = layoutInflater;
        this.sharedPreferences = sharedPreferences;

        String colorsJson = sharedPreferences.getString(COLORS_KEY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(colorsJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                colors.add(i, jsonArray.getString(i));

            }
            notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //tworzenie widoków
        View view = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        return new ColorViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) { //wyświetlanie w tych widokach

        String colorHex = colors.get(position);

        holder.setColor(colorHex);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public int add(String color) {
        colors.add(color);
        position = colors.size() - 1;
        notifyItemInserted(position);

        storeInPreferences();
        return position;
    }

    private void storeInPreferences() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < colors.size(); i++) {

                jsonArray.put(i, colors.get(i));
            }

            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(COLORS_KEY, jsonArray.toString());
            edit.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void remove(int position) {
        colors.remove(position);
        storeInPreferences();

    }

    public void cliked(int adapterPosition) {
        if (colorClickedListener != null) {
            colorClickedListener.onColorClicked(colors.get(adapterPosition));
        }
    }

    public void setColorClickedListener(ColorClickedListener colorClickedListener) {
        this.colorClickedListener = colorClickedListener;
    }

    public void replace(String oldColor, String colorHEX) {
        int indexOf = colors.indexOf(oldColor);
        colors.set(indexOf, colorHEX);
        notifyItemChanged(indexOf);
        storeInPreferences();

    }

    public void clear() {
        colors.clear();
        notifyDataSetChanged();
        sharedPreferences.edit().clear().apply(); //czyszczenie shared preferences
    }

    public interface ColorClickedListener {
        void onColorClicked(String colorInHex);
    }
}

class ColorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private String color;
    private TextView textView;
    private final ColorAdapter colorAdapter;
    private int backgroundColor;

    public ColorViewHolder(View itemView, ColorAdapter colorAdapter) {
        super(itemView);

        textView = (TextView) itemView;
        this.colorAdapter = colorAdapter;
        textView.setOnClickListener(this);


    }

    public void setColor(String color) {
        this.color = color;
        textView.setText(color);
        backgroundColor = Color.parseColor(color);
        textView.setBackgroundColor(backgroundColor);
        textView.setTextColor(PalletteActivity.getTextColorFromColor(backgroundColor));

    }

    public String getColor() {
        return color;
    }

    @Override
    public void onClick(View view) {
        colorAdapter.cliked(getAdapterPosition());
    }
}

