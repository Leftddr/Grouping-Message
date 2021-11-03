package com.example.groupingmessage2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class smsReceiver extends BroadcastReceiver {
    private static final String TAG = "0123456789";
    private static final String channelId = "sdkchatchannel";
    private static final String channelName = "sdkchatchannel";
    private static final int id = 1;
    private static Context mContext;

    smsReceiver(Context context) {
        super();
        mContext = context;
    }

    public void createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("This is example notification")
                .setContentText("This is example content")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }
    @Override
    public void onReceive(Context context, Intent intent){
        Log.d(TAG, "receive request");
        createNotification();
    }
}
