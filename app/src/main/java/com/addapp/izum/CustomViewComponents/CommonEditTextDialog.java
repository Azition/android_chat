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
import android.widget.EditText;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 27.07.2015.
 */
public class CommonEditTextDialog extends Dialog implements View.OnClickListener {

    private TextView yes, no, statusTitleText;
    private EditText editStatus;
    private OnClickSaveListener clickListener;
    private String hintText, titleText, mainText;
    private boolean multiLine = false;

    public CommonEditTextDialog(Context context, OnClickSaveListener clickListener) {
        super(context);
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_common_edit);
        statusTitleText = (TextView)findViewById(R.id.status_title);
        statusTitleText.setText(titleText);
        statusTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        yes = (TextView)findViewById(R.id.yes_button);
        no = (TextView)findViewById(R.id.no_button);
        yes.setTypeface(MainUserData.getCommonTextFont(getContext()));
        no.setTypeface(MainUserData.getCommonTextFont(getContext()));


        yes.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        no.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(22));
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        yes.setTextColor(getContext().getResources().getColor(R.color.hint_color));
        no.setTextColor(getContext().getResources().getColor(R.color.izum_color));

        editStatus = (EditText)findViewById(R.id.status_edit);
        editStatus.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(18));
        editStatus.setText(mainText);
        editStatus.setHint(hintText);
        if (multiLine){
            editStatus.setSingleLine(false);
            editStatus.setLines(5);
            editStatus.setMinLines(4);
            editStatus.setMaxLines(6);
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.yes_button:
                Log.e("CommonEditTextDialog", "Yes button");
                clickListener.onClickSave(editStatus.getText().toString());
                hideKeyBoard((TextView) view);
                dismiss();
                break;
            case R.id.no_button:
                Log.e("CommonEditTextDialog", "No button");
                hideKeyBoard((TextView) view);
                dismiss();
                break;
        }
    }

    public void setTitleText(String titleText){
        this.titleText = titleText;
    }

    public void setHintText(String hintText){
        this.hintText = hintText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public boolean isMultiLine() {
        return multiLine;
    }

    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    public interface OnClickSaveListener{
        void onClickSave(String text);
    }

    private void hideKeyBoard(TextView button){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
