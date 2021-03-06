package com.artion.androiddemos.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.artion.androiddemos.utils.DebugTool;

/**
 * Network相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class NetworkUtils {

	/**
	 * 判断wifi网络是否正常
	 * @param context
	 * @return
	 */
	public static boolean isWifiNetConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm == null) {
			return false;
		}
		
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && "WIFI".equalsIgnoreCase(ni.getTypeName()) && ni.isAvailable();
	}

	public static boolean isTelephonyCalling(Context context){
		if(context == null) {
			return false;
		}
		try {
			boolean calling = false;
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			DebugTool.info("NetworkUtils", "isTelephonyCalling getCallState=" + telephonyManager.getCallState());
			if(TelephonyManager.CALL_STATE_OFFHOOK == telephonyManager.getCallState()
					|| TelephonyManager.CALL_STATE_RINGING == telephonyManager.getCallState()){
				calling = true;
			}
			return calling;
		} catch (Exception e) {

		}
		return false;
	}
}
