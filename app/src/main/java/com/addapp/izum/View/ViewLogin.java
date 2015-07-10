package com.addapp.izum.View;


import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by ILDAR on 15.06.2015.
 */
public class ViewLogin{

    private LinearLayout mainLayout;

    private TextView textView;
    private Button btn, startServiceBtn, stopServiceBtn;
    private ProgressBar progressBar;

    private Context context;

    public final static int BUTTON = 0;
    public final static int STARTBUTTON = 1;
    public final static int STOPBUTTON = 2;

    public ViewLogin(Context context){
        this.context = context;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        textView = new TextView(context);
        textView.setText("This is a TextView");
        textView.setLayoutParams(params);

        btn = new Button(context);
        btn.setText("This is a Button");
        btn.setLayoutParams(params);

        startServiceBtn = new Button(context);
        startServiceBtn.setText("Старт сервис");
        startServiceBtn.setLayoutParams(params);

        stopServiceBtn = new Button(context);
        stopServiceBtn.setText("Стоп сервис");
        stopServiceBtn.setLayoutParams(params);

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        progressBar.setIndeterminate(true);

        mainLayout.addView(textView);
        mainLayout.addView(btn);
        mainLayout.addView(startServiceBtn);
        mainLayout.addView(stopServiceBtn);
        mainLayout.addView(progressBar);
    }

    public Button getButton(){
        return btn;
    }

    public Button getStartServiceBtn(){
        return startServiceBtn;
    }

    public Button getStopServiceBtn(){
        return stopServiceBtn;
    }

    public void setText(String text){
        this.textView.setText(text);
    }

    public Context getContext(){
        return this.context;
    }

    public LinearLayout getLayout(){
        return this.mainLayout;
    }
}
