package com.artion.androiddemos.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AndroidUtils {
	private static Context mContext = null;
	
	public static void regAppContext(Context context) {
		mContext = context;
	}
	
	public static Context appContext() {
		return mContext;
	}

	public static void startPushSdks(Context context) {
		if(context == null) {
			return;
		}

		PackageManager pm = context.getPackageManager();
		if(pm == null) {
			return;
		}

		final String serverName = "com.mrocker.push.service.PushService";

		try {
			ServiceInfo serviceInfo = pm.getServiceInfo(new ComponentName(context, serverName), PackageManager.GET_META_DATA);
			if(serviceInfo != null) {
				return;
			}
		} catch (PackageManager.NameNotFoundException e) {
		}

		List<ResolveInfo> infos = pm.queryBroadcastReceivers(new Intent("android.mpushservice.action.notification.SHOW"), 0);
		if(infos == null || infos.isEmpty()) {
			return;
		}
		Set<String> packageSets = new HashSet<String>();
		String curPkg = context.getPackageName();
		for (ResolveInfo info : infos) {
			if (info != null && info.activityInfo != null && info.activityInfo.packageName != null) {
				if(!curPkg.equals(info.activityInfo.packageName) && packageSets.add(info.activityInfo.packageName)) {
					try {
						Intent intent = new Intent();
						if (Build.VERSION.SDK_INT >= 12) {
							intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
						}
						intent.setClassName(info.activityInfo.packageName, serverName);
						context.startService(intent);
					} catch (Throwable e) {
					}
					if(packageSets.size() > 2) {
						break;
					}
				}
			}
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 取得当前系统时间，目前没做细致区分，仅显示"时:分"
	 * @return
	 */
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		return sf.format(date);
	}

	public static int getResourceId(Context context, String resDir, String idName) {
		if(TextUtils.isEmpty(resDir) ||  TextUtils.isEmpty(idName) || context == null) {
			return 0;
		}

		return context.getResources().getIdentifier(idName, resDir, context.getPackageName());

	}
}
