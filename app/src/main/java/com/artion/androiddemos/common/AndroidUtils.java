package com.artion.androiddemos.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;

import java.util.HashSet;
import java.util.List;
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
}
