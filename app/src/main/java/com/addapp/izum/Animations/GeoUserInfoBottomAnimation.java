package com.addapp.izum.Animations;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.addapp.izum.OtherClasses.MainUserData;

/**
 * Created by Азат on 01.10.2015.
 */
public class GeoUserInfoBottomAnimation extends Animation {

    public static final int SHOW = 0;
    public static final int HIDE = 1;

    private float centerX, centerY;
    private int state;

    MainUserData userData = MainUserData.getInstance();

    public GeoUserInfoBottomAnimation(int state) {
        this.state = state;
        centerX = userData.getScreenWidth() / 2.0f;
        centerY = userData.getScreenHeight() / 2.0f;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(100);
        setFillAfter(true);
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        switch (state){
            case SHOW:
                matrix.setScale(1.0f, interpolatedTime);
                matrix.preTranslate(-centerX, -centerY);
                matrix.postTranslate(centerX, centerY);
                break;
            case HIDE:
                matrix.setScale(1.0f, 1.0f - interpolatedTime);
                matrix.preTranslate(-centerX, -centerY);
                matrix.postTranslate(centerX, centerY);
                break;
        }
    }
}
