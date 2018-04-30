package com.addapp.izum.ServiceAndReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.widget.Toast;

import com.addapp.izum.Activity.Login;
import com.addapp.izum.R;

public class NotificationService extends Service {

    private NotificationManager mNM;

    private int NOTIFICATION = 1000;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            long endTime = System.currentTimeMillis() + 10*1000;
            while(System.currentTimeMillis() < endTime){
                synchronized (this){
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {

        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        showNotification();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mNM.cancel(NOTIFICATION);
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    private void showNotification(){
        CharSequence text = "Какой то текст\nВ две строки";

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Login.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.user_click_close)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentInfo("Content info")
                .setContentTitle("Content title")
                .setContentText("Content text")
                .setContentIntent(contentIntent)
                .build();

        mNM.notify(NOTIFICATION, notification);
    }
}
