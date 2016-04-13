package com.bearwithheadphones.mooder;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class SquareImageView extends ImageView {

    static int id =0;
    int myId;

    public SquareImageView(Context context) {
        super(context);
        myId = id++;
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }


    @Override
    public Object getTag(){
        return myId;
    }
}

