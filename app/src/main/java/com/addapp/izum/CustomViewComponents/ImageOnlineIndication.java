package com.addapp.izum.CustomViewComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ILDAR on 24.08.2015.
 */
public class ImageOnlineIndication extends ImageView{

    private boolean status;
    private Paint paint;

    public ImageOnlineIndication(Context context) {
        this(context, null);
    }

    public ImageOnlineIndication(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStrokeWidth(1);
    }

    public void setOnline(boolean status){
        this.status = status;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();
        final int radius = (int) (Math.max(height, width) * 0.08);
        final int chutchut = (int) (radius * 0.6);
        super.onDraw(canvas);

        if (status) {
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#5bca1e"));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(height - radius - chutchut,
                    width - radius - chutchut, radius, paint);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(height - radius - chutchut,
                    width - radius - chutchut, radius, paint);
        }
    }
}
