package com.addapp.izum.OtherClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Transformation;

/**
 * Created by ILDAR on 28.07.2015.
 */
public class PicassoRound2Transformation implements Transformation {

    private static final String TAG = "RoundTransformation";
    private static final boolean DEBUG = false;
    private TextDrawable drawable = null;
    private Bitmap drawable_bitmap;

    public PicassoRound2Transformation() {
    }

    public PicassoRound2Transformation(TextDrawable drawable) {

        if(drawable != null){

            if(drawable.getIntrinsicWidth() <= 0
                    || drawable.getIntrinsicHeight() <= 0){
                if (DEBUG) Log.e(TAG, "size <= 0 ");
                drawable_bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                if (DEBUG) Log.e(TAG, "size > 0 ");
                drawable_bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(drawable_bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        this.drawable = drawable;
    }

    @Override
    public Bitmap transform(Bitmap source) {

        if (drawable != null){
            if (DEBUG) Log.e(TAG, "drawable != null ");

            source.recycle();

            return convertToCircle(drawable_bitmap, false);

        } else {
            if (DEBUG) Log.e(TAG, "drawable = null ");
            return convertToCircle(source, false);
        }
    }

    @Override
    public String key() {
        return "circle";
    }

    public static Bitmap convertToCircle(Bitmap source, boolean recycleSource) {

        int x, y;
        int size = Math.min(source.getWidth(), source.getHeight());
        x = (source.getWidth() - size) / 2;
        y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap!=source){
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        canvas.drawPath(getPath(size), paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    private static Path getPath(int size){
        Path path = new Path();
        if (DEBUG) Log.e(TAG, "Size: " + size);
        float half = size / 2;
        if (DEBUG) Log.e(TAG, "Half: " + half);
        float arcLength = half / 100 * 18;
        float uarcLength = half - arcLength;
        if (DEBUG) Log.e(TAG, "ArcLength: " + arcLength);
        float cp21 = uarcLength / 100 * 30;
        if (DEBUG) Log.e(TAG, "cp21: " + cp21);
        float cp22 = arcLength / 100 * 8;
        if (DEBUG) Log.e(TAG, "cp22: " + cp22);
        float cp31 = uarcLength / 100 * 70;
        if (DEBUG) Log.e(TAG, "cp31: " + cp31);
        float cp32 = arcLength / 100 * 14;
        if (DEBUG) Log.e(TAG, "cp32: " + cp32);

        path.moveTo(0, half);
        path.cubicTo(cp22, half + cp21, cp32, half + cp31, arcLength, size - arcLength);
        path.cubicTo(half - cp31 , size - cp32, half - cp21, size - cp22, size / 2, size);
        path.cubicTo(half + cp21, size - cp22, half + cp31, size - cp32, half + uarcLength, size - arcLength);
        path.cubicTo(size - cp32, half + cp31, size - cp22, half + cp21, size, half);
        path.cubicTo(size - cp22, half - cp21, size - cp32, half - cp31, size - arcLength, arcLength);
        path.cubicTo(half + cp31, cp32, half + cp21, cp22, half, 0);
        path.cubicTo(half - cp21, cp22, half - cp31, cp32, arcLength, arcLength);
        path.cubicTo(cp32, half - cp31, cp22, half - cp21, 0, half);

        path.close();

        return path;
    }
}
