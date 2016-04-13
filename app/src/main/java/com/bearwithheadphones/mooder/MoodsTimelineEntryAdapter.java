package com.bearwithheadphones.mooder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
public class MoodsTimelineEntryAdapter extends BaseAdapter {

    public MoodsTimelineEntryAdapter(LayoutInflater inflater){

        this.inflater = inflater;
    }

    private final LayoutInflater inflater;

    public ArrayList<Bitmap> bitmaps = new ArrayList<>();

    @Override
    public int getCount() {
        return bitmaps.size();
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


        View rowView=this.inflater.inflate(R.layout.moods_timeline_entry, null, true);

        TextView username = (TextView) rowView.findViewById(R.id.username);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        username.setText("Bartosz Cwynar");
        date.setText(new java.util.Date().toString());
        imageView.setImageBitmap(bitmaps.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return rowView;
    }
}
