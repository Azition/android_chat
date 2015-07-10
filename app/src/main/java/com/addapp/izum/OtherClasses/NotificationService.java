package com.addapp.izum.OtherClasses;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.addapp.izum.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NotificationService extends Service {

    String acc_email;

    public class LocalBinder extends Binder{
        NotificationService getService(){
            return NotificationService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.e("Service", "onCreate");
        startService();
    }

    private void startService() {
        acc_email = "azition93@gmail.ru";
        try{
            Connecting(acc_email);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void Connecting(String msg) throws InterruptedException{
        try{
            Socket s = new Socket(InetAddress.getByName("91.201.53.242"), 10000);
            s.getOutputStream().write(msg.getBytes());

            byte buf[] = new byte[64*1024];
            int r = s.getInputStream().read(buf);
            String data = new String(buf, 0, r);
            Log.e("Service", "Data = " + data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openConnection() throws InterruptedException{
        try{
            WatchData data = new WatchData();
            data.email = acc_email;
            data.ctx = this;
            Log.e("Service", "openConnection");

            new WatchSocket().execute(data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {}

    private class WatchData{
        String email;
        Context ctx;
    }

    private class WatchSocket extends AsyncTask<WatchData, Integer, Integer>{

        Context mCtx;
        Socket mySock;

        @Override
        protected Integer doInBackground(WatchData... params) {
            Log.e("Service", "WatchSocket - doInBackground");
            InetAddress serverAddr;

            mCtx = params[0].ctx;
            String email = params[0].email;

            try{
                while(true){
                    serverAddr = InetAddress.getByName("91.201.53.242");
                    mySock = new Socket(serverAddr, 8889);

                    SocketData data = new SocketData();
                    data.ctx = mCtx;
                    data.sock = mySock;

                    GetPacket pack = new GetPacket();
                    AsyncTask<SocketData, Integer, Integer> running = pack.execute(data);

                    String message = email;

                    try{
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mySock.getOutputStream())), true);
                        out.println(message);
                    } catch (Exception e){}

                    while (running.getStatus().equals(Status.RUNNING)){

                    }

                    try {
                        mySock.close();
                    } catch (Exception e){}
                }
            } catch (Exception e) {
                return -1;
            }
        }
    }

    private class SocketData{
        Socket sock;
        Context ctx;
    }

    private class GetPacket extends AsyncTask<SocketData, Integer, Integer>{

        Context mCtx;
        Socket mySock;
        char[] mData;

        @Override
        protected void onProgressUpdate(Integer... values) {
            try{
                String prop = String.valueOf(mData);
            } catch (Exception e){
                Toast.makeText(mCtx, "Socket error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected Integer doInBackground(SocketData... params) {
            mySock = params[0].sock;
            mCtx = params[0].ctx;
            mData = new char[4096];

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
                int read = 0;

                while((read = reader.read(mData)) >= 0 && !isCancelled()){
                    if(read > 0) publishProgress(read);
                }
                reader.close();
            } catch (IOException e){
                return -1;
            } catch (Exception e){
                return -1;
            }
            return 0;
        }
    }
}
