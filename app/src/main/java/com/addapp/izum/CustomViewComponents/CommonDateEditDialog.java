package com.addapp.izum.CustomViewComponents;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;

import java.util.Calendar;

/**
 * Created by ILDAR on 04.08.2015.
 */
public class CommonDateEditDialog extends Dialog implements DatePicker.OnDateChangedListener, View.OnClickListener{

    private final static boolean DEBUG = false;

    private TextView yes, no, statusTitleText;
    private DatePicker datePicker;
    private String titleText;
    private OnClickSaveListener clickListener;
    private int year, month, day;

    public CommonDateEditDialog(Context context, OnClickSaveListener clickListener) {
        super(context);
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_common_date_edit);
        statusTitleText = (TextView)findViewById(R.id.status_title);
        statusTitleText.setText(titleText);
        statusTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        datePicker = (DatePicker)findViewById(R.id.date_edit);
        datePicker.init(year, month, day, this);

        yes = (TextView)findViewById(R.id.yes_button);
        no = (TextView)findViewById(R.id.no_button);
        yes.setTypeface(MainUserData.getCommonTextFont(getContext()));
        no.setTypeface(MainUserData.getCommonTextFont(getContext()));
        yes.setOnClickListener(this);
        no.setOnClickListener(this);


        yes.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        no.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        yes.setTextColor(getContext().getResources().getColor(R.color.hint_color));
        no.setTextColor(getContext().getResources().getColor(R.color.izum_color));
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        if (DEBUG) Log.e("CommonDateEditDialog","" + i + "  " + i1 + "  " + i2 );
        this.year = i;
        this.month = i1;
        this.day = i2;
    }

    public void setTitleText(String titleText){
        this.titleText = titleText;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.yes_button:
                if (DEBUG) Log.e("CommonDateEditDialog", "Yes button");
                if (DEBUG) Log.e("CommonDateEditDialog","" + year + "  " + month + "  " + day );
                clickListener.onClickSave(year, month + 1, day);
                hideKeyBoard((TextView) view);
                dismiss();
                break;
            case R.id.no_button:
                if (DEBUG) Log.e("CommonDateEditDialog", "No button");
                hideKeyBoard((TextView) view);
                dismiss();
                break;
        }
    }

    public interface OnClickSaveListener{
        void onClickSave(int year, int month, int day);
    }

    public void setDate(int year, int month, int day){
        if (DEBUG) Log.e("CommonDateEditDialog","" + year + "  " + month + "  " + day );
        this.year = year;
        this.month = month - 1;
        this.day = day;
    }

    private void hideKeyBoard(TextView button){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
