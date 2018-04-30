package com.addapp.izum.OtherClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.addapp.izum.Adapter.AdapterMarkerMap;
import com.addapp.izum.R;
import com.squareup.picasso.Transformation;

import java.io.IOException;

/**
 * Created by ILDAR on 08.09.2015.
 */
public class ImageLoader implements Runnable {

    private AdapterMarkerMap.MapItem item;
    private Thread t;
    private Bitmap bm = null;
    private Context context;

    public ImageLoader(Context context, AdapterMarkerMap.MapItem item) {
        this.item = item;
        this.context = context;
        t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void run() {
        try {
            if (!"http://im.topufa.org/".equals(item.getUrl())) {
                bm = ImageHandler.getSharedInstance(context)
                        .load(item.getUrl())
                        .transform(new Transformation() {
                            @Override
                            public Bitmap transform(Bitmap source) {
                                int x, y;
                                int size = Math.min(source.getWidth(), source.getHeight());
                                x = (source.getWidth() - size) / 2;
                                y = (source.getHeight() - size) / 2;
                                Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                                if (squaredBitmap != source) {
                                    source.recycle();
                                }
                                Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
                                Canvas canvas = new Canvas(bitmap);
                                Paint paint = new Paint();
                                BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                                paint.setShader(shader);
                                paint.setAntiAlias(true);
                                float radius = size / 2f;
                                canvas.drawCircle(radius, radius, radius, paint);
                                //                            paint = new Paint();
                                //                            paint.setStyle(Paint.Style.STROKE);
                                //                            paint.setColor(context.getResources().getColor(R.color.izum_color));
                                //                            paint.setStrokeWidth(5);
                                //                            paint.setAntiAlias(true);
                                //                            canvas.drawCircle(radius, radius, radius - 2f, paint);
                                squaredBitmap.recycle();
                                return bitmap;
                            }

                            @Override
                            public String key() {
                                return "circle";
                            }
                        })
                        .stableKey(item.getUserID())
                        .get();
            } else {
                bm = ImageHandler.getSharedInstance(context)
                        .load(R.drawable.nophoto)
                        .transform(new Transformation() {
                            @Override
                            public Bitmap transform(Bitmap source) {
                                int x, y;
                                int size = Math.min(source.getWidth(), source.getHeight());
                                x = (source.getWidth() - size) / 2;
                                y = (source.getHeight() - size) / 2;
                                Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                                if (squaredBitmap != source) {
                                    source.recycle();
                                }
                                Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
                                Canvas canvas = new Canvas(bitmap);
                                Paint paint = new Paint();
                                BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                                paint.setShader(shader);
                                paint.setAntiAlias(true);
                                float radius = size / 2f;
                                canvas.drawCircle(radius, radius, radius, paint);
//                            paint = new Paint();
//                            paint.setStyle(Paint.Style.STROKE);
//                            paint.setColor(context.getResources().getColor(R.color.izum_color));
//                            paint.setStrokeWidth(5);
//                            paint.setAntiAlias(true);
//                            canvas.drawCircle(radius, radius, radius - 2f, paint);
                                squaredBitmap.recycle();
                                return bitmap;
                            }

                            @Override
                            public String key() {
                                return "circle";
                            }
                        })
                        .noFade()
                        .get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        return bm;
    }
}
