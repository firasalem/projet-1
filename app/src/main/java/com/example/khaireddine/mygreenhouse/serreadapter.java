package com.example.khaireddine.mygreenhouse;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class serreadapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public serreadapter(Context context, String[]mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.itemserre, null);
            // set value into textview

        } else {
            gridView = (View) convertView;
        }
        TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
        textView.setText(mobileValues[position]);
        // set image based on selected text
        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

        imageView.setImageResource(R.drawable.item_greenhouse);
        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;

    }

}