package com.artion.androiddemos.acts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.common.ViewUtils;

public class LocalReceiverDemo extends CommonBtnDemo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initLayout() {
        super.initLayout();
    }

    @Override
    protected void initListener() {
        super.initListener();

        btn1.setText("send");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.onViewClickTimes(v, 500, new ViewUtils.OnViewClickListener() {
                    @Override
                    public void onClicked(View view, int clickTimes) {
                        if(clickTimes%2 == 1) {
                            sendFinishAllAnswerReceiver(mAct);
                        } else {
                            sendFinishAnswerReceiver(mAct, "1234");
                        }
                    }

                    @Override
                    public void onClicking(View view, int clickTimes) {

                    }
                });
            }
        });

        btn2.setText("unRegister");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unRegisterBroadcast();
            }
        });

        btn3.setText("register");
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBroadcast();
            }
        });
    }

    public static final String BUNDLE_EXTRA_MEETING_VIDEO_ID = "EXTRA_MEETING_VIDEO_ID";
    public static final String ACTION_RM_FINISH_ALL = "ACTION_RM_FINISH_ALL";
    public static final String ACTION_RM_FINISH_CURRENT = "ACTION_RM_FINISH_CURRENT";

    protected BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null) {
                return;
            }
            String action = intent.getAction();
            if(action == null) {
                return;
            }

            if(action.equals(ACTION_RM_FINISH_ALL)) {
                ToastUtils.showMessage(context, "ACTION_RM_FINISH_ALL");
            } else if(action.equals(ACTION_RM_FINISH_CURRENT)) {
                String cancelVideoMeetingId = intent.getStringExtra(BUNDLE_EXTRA_MEETING_VIDEO_ID);
                ToastUtils.showMessage(context, "ACTION_RM_FINISH_CURRENT = " + cancelVideoMeetingId);
            }

        }
    };

    protected void registerBroadcast() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_RM_FINISH_ALL);
            intentFilter.addAction(ACTION_RM_FINISH_CURRENT);
            LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void unRegisterBroadcast() {
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendFinishAnswerReceiver(Context context, String videoMeetingId) {
        if(videoMeetingId == null) {
            return ;
        }

        try {
            Intent intent = new Intent(ACTION_RM_FINISH_CURRENT);
            intent.putExtra(BUNDLE_EXTRA_MEETING_VIDEO_ID, videoMeetingId);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendFinishAllAnswerReceiver(Context context) {
        try {
            Intent intent = new Intent(ACTION_RM_FINISH_ALL);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
