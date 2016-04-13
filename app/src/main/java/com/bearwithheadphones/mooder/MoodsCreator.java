package com.bearwithheadphones.mooder;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MoodsCreator {

    public Bitmap createMood(int alpha,int red, int green,int blue) {
        int width = 1;
        int height = 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setARGB(alpha,red, green, blue);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);
        return bitmap;
    }
}
