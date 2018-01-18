package com.artion.androiddemos.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.artion.androiddemos.common.AndroidUtils;
import com.artion.androiddemos.common.NetworkUtils;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;
import com.artion.androiddemos.utils.DeviceTool;

/**
 * Created by caijinsheng on 16/11/24.
 */

public class SysBroadcastRecevier extends BroadcastReceiver {

    private final String TAG = "SysBroadcastRecevier";
    public static final String ACTION_TEST = "com.artion.androiddemos.service.ACTION.TEST";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null) {
            return;
        }

        String action = intent.getAction();
        if (action == null) {
            return;
        }
        DebugTool.info(TAG, "uninstall action: " + action);
        if(action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName=intent.getDataString();
            DebugTool.info(TAG, "uninstall packageName: " + packageName);
            ToastUtils.showMessage(context, "uninstall packageName: " + packageName);

            PackageManager pm = context.getPackageManager();
            try {//TODO 取不到applicationInfo,仅有包名
                ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);

                if(appInfo != null && appInfo.metaData != null) {
                    String gateway = appInfo.metaData.getString("MPUSH_GATEWAY");
                    DebugTool.info(TAG, "uninstall packageName gateway: " + gateway);
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else if(action.equals(ACTION_TEST)) {
            ToastUtils.showMessage(context, ACTION_TEST);
        } else if(action.equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName=intent.getDataString();
            DebugTool.info(TAG, "add packageName: " + packageName);
            ToastUtils.showMessage(context, "add packageName: " + packageName);


        } else if(action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName=intent.getDataString();
            DebugTool.info(TAG, "replace packageName: " + packageName);
            ToastUtils.showMessage(context, "replace packageName: " + packageName);
        } else if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            DebugTool.info(TAG, "ACTION_NEW_OUTGOING_CALL: ");
        } else if(action.equals("android.intent.action.PHONE_STATE")){
            DebugTool.info("NetworkUtils", "电话状态：" + NetworkUtils.isTelephonyCalling(context));

        }
    }
}
