package com.example.duan1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.duan1.R;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "lich_hoc_channel";
    private static final String CHANNEL_NAME = "Lịch học Channel";
    private static final String CHANNEL_DESC = "Notifications for Lịch học";
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // Tạo hoặc lấy NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo channel cho thông báo (cần cho Android O trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lich Hoc Channel";
            String description = "Thông báo lịch học";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        // Tạo thông báo
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Lịch học")
                .setContentText("Đến giờ học rồi! Đừng quên tham gia!")
                .setSmallIcon(R.drawable.icon_notification)
                .build();

        // Hiển thị thông báo
        notificationManager.notify(1, notification);
    }
}