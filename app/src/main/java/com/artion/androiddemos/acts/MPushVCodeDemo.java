package com.artion.androiddemos.acts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.artion.androiddemos.common.AndroidUtils;
import com.artion.androiddemos.common.LogUtils;
import com.artion.androiddemos.utils.DebugTool;

public class MPushVCodeDemo extends CommonBtnDemo {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initListener() {
		super.initListener();
		btn1.setText("checkVCode");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkVCode(mAct, listPushPackages(mAct));
			}
		});

        btn2.setText("sendFalseBroad");
        btn3.setText("sendTrueBroad");

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "{\"content\":\"您得到了100元奖励,哇咔咔\",\"id\":\"58b4ef7a559dc91b3edd962f\",\"ex\":{\"awardId\":\"123456\"},\n" +
                        "\"app\":\"571f2290559dc9a3df000003\",\"category\":\"three_interactive_action\",\n" +
                        "\"title\":\"奖励通知\",\n" +
                        "\"dna\":\"p14oiodUCNi3OmCnoNo\\/7JgoERSRcBy7KIZ3fgsb1ih4NmmNav+MIxGFshtlmjab99JlcV7yl7Ph5nFboo9q4j73qwXJxX0clKyNDK1g8NZ9Yi\\/eJXoR70Oe2AEhCd9KOldY9gzl6K6iFyWNO5WZS8L1YdvNiEPO200Zb0Sk2Y\\/DqOob56IBJDPuBX1J4bpMj7cw5hnoN+FL0wneuEMSFAh9fso9w3WkZV2suWCbcZWv35zr+wKmdAF\\/iq3kIAmKzPq6qlpJpYrGK1SCe3wPUEQrS8GsSh9TH1MSfZJNu0Hasnw41T1wsE9boNQHT\\/H\\/ndi4a\\/J17rrnUzsP2edibg==\",\"action\":{\"val\":\"\",\"mode\":3,\"tp\":2},\"smode\":3}";

                DebugTool.info(tag, "send msg = " + msg);

                Intent intent = new Intent();
                intent.setAction("android.mpushservice.action.notification.SHOW");
                intent.setPackage("pamip.com.ceshi");
                intent.putExtra("push_show_msg", msg);
                sendBroadcast(intent);
            }
        });

        btn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "{\"content\":\"您得到了100元奖励,哇咔咔\",\"id\":\"58b4ef7a559dc91b3edd962f\",\"ex\":{\"awardId\":\"123456\"},\n" +
                        "\"app\":\"571f2290559dc9a3df000003\",\"category\":\"three_interactive_action\",\n" +
                        "\"title\":\"奖励通知\",\n" +
                        "\"dna\":\"p14oiodUCNi3OmCnoNo\\/7JgoERSRcBy7KIZ3fgsb1ih4NmmNav+MIxGFshtlmjab99JlcV7yl7Ph5nFboo9q4j73qwXJxX0clKyNDK1g8NZ9Yi\\/eJXoR70Oe2AEhCd9KOldY9gzl6K6iFyWNO5WZS8L1YdvNiEPO200Zb0Sk2Y\\/DqOob56IBJDPuBX1J4bpMj7cw5hnoN+FL0wneuEMSFAh9fso9w3WkZV2suWCbcZWv35zr+wKmdAF\\/iq3kIAmKzPq6qlpJpYrGK1SCe3wPUEQrS8GsSh9TH1MSfZJNu0Hasnw41T1wsE9boNQHT\\/H\\/ndi4a\\/J17rrnUzsP2edibg==\",\"action\":{\"val\":\"\",\"mode\":3,\"tp\":2},\"smode\":3}";

                DebugTool.info(tag, "send msg = " + msg);

                Intent intent = new Intent();
                intent.setAction("android.mpushservice.action.notification.SHOW");
                intent.setPackage("pamip.com.ceshi");
                intent.putExtra("push_show_msg", msg);
                sendBroadcast(intent);
            }
        });

	}
	
	public List<String> listPushPackages(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        final Set<String> packageSets = new HashSet<String>();
        for (ResolveInfo info : pm.queryBroadcastReceivers(new Intent("android.mpushservice.action.notification.SHOW"), 0)) {
            packageSets.add(info.activityInfo.packageName);
        }

        List<String> packages = new ArrayList<String>();
        packages.addAll(packageSets);
        return packages;
    }
	
	public String getVCode(Context _context, String pkg) {
		String value = null;
        Context context;
        try {
            if (!TextUtils.isEmpty(pkg)) {
                context = _context.createPackageContext(pkg, Context.CONTEXT_IGNORE_SECURITY);
            } else {
                context = _context;
            }
            LogUtils.info(tag, "getFilePreferences context: " + context.getPackageName() + "<" + pkg + ">");
            String fileName = "mpush_version_preferences_file";
            File f = context.getFileStreamPath(fileName);
            //File f = new File(context.getFileStreamPath(fileName).getPath());
            if (f == null || !f.exists()) {
            	LogUtils.warn(tag, String.format("old sdk doesn't have preference file [%s, %s]", pkg, fileName));
                return null;
            }

            try {
                FileInputStream fis = context.openFileInput(fileName);
                byte[] b = new byte[fis.available()];//新建一个字节数组
                fis.read(b);//将文件中的内容读取到字节数组中
                fis.close();
                value = new String(b);//再将字节数组中的内容转化成字符串形式输出
                LogUtils.info(tag, "getFilePreferences value: " + value);
            } catch (Exception e) {
                LogUtils.error(tag, "read file err", e);
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.error(tag, "find package err", e);
        } catch (SecurityException e) {
            LogUtils.error(tag, "permission err", e);
        }
        return value;
	}
	
	public void checkVCode(final Context ctx, List<String> packages) {
		for (String pkg : packages) {
            String v = getVCode(ctx, pkg);
            LogUtils.info(tag, "startNewerSdk pkg: " + pkg + "> v: " + v);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                AndroidUtils.startPushSdks(ctx);
            }
        }).start();

    }

}
