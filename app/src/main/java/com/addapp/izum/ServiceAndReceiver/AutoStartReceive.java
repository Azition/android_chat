package com.addapp.izum.ServiceAndReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.addapp.izum.ServiceAndReceiver.NotificationService;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class AutoStartReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        context.startService(new Intent(context, NotificationService.class));
    }
}
