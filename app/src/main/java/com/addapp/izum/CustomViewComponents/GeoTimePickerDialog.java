package com.addapp.izum.CustomViewComponents;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 11.09.2015.
 */
public class GeoTimePickerDialog extends Dialog
        implements DialogInterface.OnShowListener , View.OnClickListener{

    private int[] arrayTime = {60, 180, 360};
    private View lastID = null;
    private MainUserData userData = MainUserData.getInstance();
    private int widthDialog = MainUserData.percentToPix(95, MainUserData.WIDTH);

    public GeoTimePickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setOnShowListener(this);
        TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setBackgroundResource(R.drawable.shape_common_window);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(getContext());
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        final TableRow rowButtons = new TableRow(getContext());
        TableRow rowSave = new TableRow(getContext());
        rowSave.setGravity(Gravity.CENTER);

        TextView statusText = new TextView(getContext());
        statusText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(22));
        statusText.setGravity(Gravity.CENTER);
        statusText.setTextColor(getContext().getResources()
                .getColor(R.color.izum_color));
        statusText.setTypeface(Typeface
                .createFromAsset(getContext().getAssets(), "Roboto-Light.ttf"));

        statusText.setText("Сколько минут вас показывать на карте?");

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
        rowParams.topMargin = 22;
        rowParams.bottomMargin = 18;
        rowParams.leftMargin =
                rowParams.rightMargin = (int) (widthDialog * 0.1);
        rowParams.span = 3;
        rowTitle.addView(statusText, rowParams);

        final int[] arrayButtonsID = new int[arrayTime.length];

        for (int i = 0; i < arrayTime.length; i++){
            Button buttonTime = new Button(getContext()){
                @Override
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

                    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

                    int newMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);

                    super.onMeasure(newMeasureSpec, newMeasureSpec);
                }
            };
            buttonTime.setBackground(getContext()
                    .getResources()
                    .getDrawable(R.drawable.circle_button_normal));
            buttonTime.setText(Integer.toString(arrayTime[i]));
            buttonTime.setTextColor(getContext().getResources()
                    .getColor(R.color.izum_color));
            buttonTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    MainUserData.setTextSize(30));
            buttonTime.setTypeface(Typeface
                    .createFromAsset(getContext().getAssets(), "Roboto-Light.ttf"));
            buttonTime.setOnClickListener(this);
            int ID = Utils.generateViewId();
            arrayButtonsID[i] = ID;
            buttonTime.setId(ID);

            rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rowParams.gravity = Gravity.CENTER;
            rowButtons.addView(buttonTime, rowParams);
        }

        rowButtons.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Button tempButton;
                        int maxTextSize = 0;

                        for (int i = 0; i <  arrayTime.length; i++){
                            tempButton = (Button)rowButtons.findViewById(arrayButtonsID[i]);
                            String buttonText = tempButton.getText().toString();
                            Rect bounds = new Rect();
                            Paint textPaint = tempButton.getPaint();
                            textPaint.getTextBounds(buttonText, 0, buttonText.length(), bounds);
                            if (maxTextSize < bounds.width())
                                maxTextSize = bounds.width();
                        }

                        Log.e("GeoTimePickerDialog", "maxTextSize: " + maxTextSize);

                        int newButtonSize;
                        int loop = 17;
                        maxTextSize *= 1.7;

//                        do {
//                            loop++;
//                            newButtonSize = MainUserData.percentToPix(loop, MainUserData.WIDTH);
//
//                            Log.e("GeoTimePickerDialog", "maxTextSize: " + maxTextSize +
//                                    " newButtonSize: " + newButtonSize);
//                        } while (newButtonSize < maxTextSize);

                        for (int i = 0; i <  arrayTime.length; i++){
                            tempButton = (Button)rowButtons
                                    .findViewById(arrayButtonsID[i]);
                            ViewGroup.LayoutParams tempParams =
                                    tempButton.getLayoutParams();
                            tempParams.height = maxTextSize;
                            tempParams.width = maxTextSize;

                            tempButton.setLayoutParams(tempParams);
                        }

                        rowButtons.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        Button buttonSave = new Button(getContext());
        buttonSave.setBackground(getContext()
                .getResources().getDrawable(R.drawable.button));
        buttonSave.setTextColor(Color.WHITE);
        buttonSave.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(22));
        buttonSave.setText("Сохранить");

        rowParams = new TableRow.LayoutParams();
        rowParams.topMargin = 24;
        rowParams.bottomMargin = 28;
        rowParams.leftMargin = rowParams.rightMargin = 26;
        rowParams.span = 3;

        rowSave.addView(buttonSave, rowParams);

        tableLayout.addView(rowTitle);
        tableLayout.addView(rowButtons);
        tableLayout.addView(rowSave);

        setContentView(tableLayout);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

        Window window = getWindow();
        int width = (int) (userData.getScreenWidth() * 0.95);
        window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view) {

//        Log.e("GeoTimePickerDialog", "onClick");

        if (lastID != null){
//            Log.e("GeoTimePickerDialog", "checkout: " + lastID.getId());
            ((Button)lastID).setTextColor(getContext().getResources()
                    .getColor(R.color.izum_color));
            lastID.setBackground(getContext()
                    .getResources()
                    .getDrawable(R.drawable.circle_button_normal));
        }

        lastID = view;

//        Log.e("GeoTimePickerDialog", "check: " + lastID.getId());
        ((Button)view).setTextColor(Color.WHITE);
        view.setBackground(getContext()
            .getResources()
                .getDrawable(R.drawable.circle_button_pressed));
    }
}
