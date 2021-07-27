package com.example.sokoban;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PlaceableGrid extends BaseAdapter {
    private Context mContext;
    private final String[] placeableArray;
    private final int[] placeableId;

    public PlaceableGrid(Context c,String[] placeableArray, int[] placeableId) {
        mContext = c;
        this.placeableArray = placeableArray;
        this.placeableId = placeableId;
    }

    @Override
    public int getCount() {
        return placeableArray.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.grid_placeable, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            imageView.setImageResource(placeableId[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
