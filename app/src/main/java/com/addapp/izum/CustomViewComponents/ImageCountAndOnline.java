package com.addapp.izum.CustomViewComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.addapp.izum.R;

import java.util.Random;

/**
 * Created by ILDAR on 01.09.2015.
 */
public class ImageCountAndOnline extends ImageOnlineIndication{

    private int numCount = 1;
    private static float radius = 16;
    private static float textSize = 30;


    private Rect mTextBoundRect = new Rect();
    private Paint mPaint = new Paint();

    public ImageCountAndOnline(Context context) {
        this(context, null);
    }

    public ImageCountAndOnline(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mTextWidth, mTextHeight;
        String text = Integer.toString(numCount);

        float width, height;

        width = getWidth() * 0.3f;
        height = getHeight() * 0.3f;

        while(radius > height * 0.35){
            radius--;
        }
        do {
            mPaint.setTextSize(textSize);

            mPaint.getTextBounds(text, 0, text.length(), mTextBoundRect);
            mTextWidth = mPaint.measureText(text);
            mTextHeight = mTextBoundRect.height();
            Log.e("ImageCountAndOnline", "Rect Height: " + height +
                    "  Text Height: " + mTextHeight);
            if (height * 0.7 < mTextHeight){
                textSize--;
            } else {
                break;
            }
        } while(true);

        while(width * 0.9 < mTextWidth){
            width++;
        }

        mPaint.setColor(getResources().getColor(R.color.izum_color));
        mPaint.setAlpha(0xbb);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, radius, radius, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        canvas.drawText(text,
                (width / 2.1f) - (mTextWidth / 2f),
                (height / 2.2f) + (mTextHeight / 2f),
                mPaint);
    }

    public void setNumCount(int numCount){
        this.numCount = numCount;
    }
}
