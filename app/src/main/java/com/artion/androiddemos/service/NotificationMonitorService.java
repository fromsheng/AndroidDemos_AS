package com.artion.androiddemos.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.artion.androiddemos.utils.DebugTool;

/**
 * Created by caijinsheng on 17/2/15.
 */

@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        DebugTool.info("NotificationMonitorService", "Notification removed " + notificationTitle + " & " + notificationText);

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        DebugTool.info("NotificationMonitorService", "Notification posted " + notificationTitle + " & " + notificationText);

    }

}
