package com.example.duan1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

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
        // Nhận dữ liệu từ Intent
        String title = intent.getStringExtra("title");  // Tiêu đề thông báo
        String message = intent.getStringExtra("message");  // Nội dung thông báo

        if (title == null) title = "Thông báo";  // Tiêu đề mặc định
        if (message == null) message = "Bạn có một thông báo mới!";  // Nội dung mặc định

        // Tạo hoặc lấy NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo channel cho thông báo (cần cho Android O trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            notificationManager.createNotificationChannel(channel);
        }

        // Tạo thông báo
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)  // Tiêu đề từ Intent
                .setContentText(message)  // Nội dung từ Intent
                .setSmallIcon(R.drawable.icon_notification)
                .build();

        // Hiển thị thông báo
        notificationManager.notify(1, notification);
    }
}
