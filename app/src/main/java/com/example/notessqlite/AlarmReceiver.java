package com.example.notessqlite;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm recived", Toast.LENGTH_SHORT).show();

        Intent nextActivity = new Intent(context, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nextActivity, PendingIntent.FLAG_IMMUTABLE|  PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Hello SE1732")
                .setContentText("Wakeup")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(new Notification.BigTextStyle().bigText("Wakeup"))
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SE1732_CHANNEL_ID", "SE1732_XIN_CHAO", NotificationManager.IMPORTANCE_HIGH);
            channel.setVibrationPattern(new long[]{1, 2, 3, 4, 5});
            channel.setLightColor(255);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("SE1732_CHANNEL_ID");
        }
        Notification notification = builder.build();


        notificationManager.notify(10, notification);
        notificationManager.notify(11, notification);

    }
}