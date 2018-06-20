package com.artion.androiddemos.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.artion.androiddemos.R;
import com.artion.androiddemos.acts.AnimationDemo;
import com.artion.androiddemos.service.SysBroadcastRecevier;

/**
 * Notification相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class NotificationUtils {
	
	public static void showNotification(Context context, String title, String content) {
		Bitmap btm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.message_img_new_normal);
		NotificationCompat.Builder builder = null;
		//判断是否是8.0上设备
		if(Build.VERSION.SDK_INT >= 26) {
			String channelID = "1";
			String channelName = "papush";
			try {
				NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
				NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				manager.createNotificationChannel(channel);
				builder = new NotificationCompat.Builder(context, channelID);
			} catch (Throwable e) {
				builder = new NotificationCompat.Builder(context);
			}
		} else {
			builder = new NotificationCompat.Builder(context);
		}

		//TODO targetSDK>=26后，需要调整Builder()增加channelId，原Deprecated
		builder.setSmallIcon(R.drawable.common_img_toolbarnew_normal)
				.setContentTitle(title)
				.setContentText(content);
		builder.setTicker("微信红包");//第一次提示消息的时候显示在通知栏上
		builder.setNumber(12);
		builder.setLargeIcon(btm);
		builder.setAutoCancel(true);//自己维护通知的消失

		//构建一个Intent
		Intent resultIntent = new Intent(context,
				AnimationDemo.class);
		//封装一个Intent
		PendingIntent resultPendingIntent = PendingIntent.getActivity(
				context, 0, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// 设置通知主题的意图
		builder.setContentIntent(resultPendingIntent);

		Intent delIntent = new Intent("com.artion.androiddemos.service.ACTION.DELETE_NOTIFICATION");
		delIntent.setClass(context, SysBroadcastRecevier.class);
		PendingIntent delPI = PendingIntent.getBroadcast(context, 0, delIntent, 0);
		builder.setDeleteIntent(delPI);

		//获取通知管理器对象
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		int notifyId = (int) (System.currentTimeMillis() % 10000000);
//		if(Build.VERSION.SDK_INT >= 24) {//TODO 设置不收起通知添加如下代码
//			mBuilder.setGroup("push");
//			mBuilder.setGroupSummary(true);
//		}
		mNotificationManager.notify(notifyId, builder.build());
	}

	public static void showNotification(Context context, int smallIcon, String title, String content, PendingIntent pendingIntent, int notifyId) {
		if(context == null) {
			return;
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(smallIcon)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
						context.getApplicationInfo().icon))
				.setTicker(content)
				.setContentTitle(title)
				.setContentText(content)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(content))//设置为多文本显示
				.setContentIntent(pendingIntent);

		//横幅通知
		//因为最新的支持包,只支持到Android 4.1包含此API,低版本只是将浮动通知显示为普通通知,因此这里对运行的版本进行过滤
//		if (Build.VERSION.SDK_INT > 16) {
//			//这里预先设置了震动参数,只是为了实现浮动通知效果,真正的震动设置在需要的时候,正式设置
//			builder.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);
//		}

		Notification notification = builder.build();

		//声音
//		notification.defaults |= Notification.DEFAULT_SOUND;
		int resId = AndroidUtils.getResourceId(context, "raw", "beep");
		if(resId != 0) {
			Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
			notification.sound = soundUri;
		} else {
			notification.defaults |= Notification.DEFAULT_SOUND;
		}

		//震动
		notification.defaults |= Notification.DEFAULT_VIBRATE;

		//可删除
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(notifyId, notification);
	}

}
