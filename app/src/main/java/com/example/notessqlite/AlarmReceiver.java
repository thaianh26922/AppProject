package com.example.notessqlite;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm received", Toast.LENGTH_SHORT).show();

        Intent nextActivity = new Intent(context, NotificationActivity.class);
        nextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nextActivity, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Set sound URI for the notification
        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.toy_ducks_quacking);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "SE1732_CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Hello SE1732")
                .setContentText("Wake up")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Wake up"))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(soundUri); // Set the sound for the notification

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Notification channel setup (for Android Oreo and higher)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SE1732_CHANNEL_ID", "SE1732_XIN_CHAO", NotificationManager.IMPORTANCE_HIGH);
            channel.setVibrationPattern(new long[]{1, 2, 3, 4, 5});
            channel.setLightColor(255);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("SE1732_CHANNEL_ID");
        }

        Notification notification = builder.build();

        // Issue the notification
        notificationManager.notify(10, notification);
        notificationManager.notify(11, notification);
    }
}
