package com.bearwithheadphones.mooder;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.graphics.Bitmap;

import java.util.ArrayList;
//// TODO: 08.08.2016 should be able to bind SquareImageView with Mood id.  
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public ArrayList<SquareImageView> squareImageViews = new ArrayList<SquareImageView>();

    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }


    public void addSquareImageView(Bitmap image){
        SquareImageView squareImageView = new SquareImageView(mContext);

        squareImageView.setImageBitmap(image);
        squareImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        squareImageViews.add(squareImageView);

        Log.d("squareImageViews", squareImageViews.toString());
    }

    @Override
    public int getCount() {
        return squareImageViews.size();
    }

    @Override
    public Object getItem(int position) {
        return squareImageViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return squareImageViews.get(position);
    }

}

