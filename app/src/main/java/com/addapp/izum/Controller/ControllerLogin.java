package com.addapp.izum.Controller;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.addapp.izum.Model.ModelLogin;
import com.addapp.izum.View.ViewLogin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ControllerLogin {
    private ModelLogin modelLogin;
    private ViewLogin viewLogin;
    private OnClickListener onClickListener;

    public ControllerLogin(ModelLogin modelLogin, ViewLogin viewLogin){
        this.modelLogin = modelLogin;
        this.viewLogin = viewLogin;
    }

    public void control(){

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkBtnAndText();
            }
        };

        viewLogin.getButton().setOnClickListener(onClickListener);
        viewLogin.getStartServiceBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Izum", "StartServiceBtn нажата");

                String msg = "azition93@gmail.ru";
                Socket s = null;
                try {
                    s = new Socket(InetAddress.getByName("91.201.53.242"), 10000);
                    s.getOutputStream().write(msg.getBytes());

                    byte buf[] = new byte[64*1024];
                    int r = s.getInputStream().read(buf);
                    String data = new String(buf, 0, r);

                    viewLogin.setText(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        viewLogin.getStopServiceBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Izum", "StopServiceBtn нажата");
                viewLogin.setText("Сервис остановлен");
            }
        });
    }

    public LinearLayout getView(){
        return viewLogin.getLayout();
    }

    private void linkBtnAndText(){
        viewLogin.setText(modelLogin.getName());
        viewLogin.getButton().setText("Изменить текст");
    }
}
