package com.artion.androiddemos;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.service.FloatViewService;
import com.artion.androiddemos.utils.DebugTool;
import com.artion.androiddemos.utils.JniUtils;

public class Demos extends BaseActivity {
	
	private ListView listView;
	public static  List<String> actNames;
	
	private void initActNames() {
		actNames = new ArrayList<String>();
		actNames.add("ActConstraintsDemo");
		actNames.add("RecyclerViewDemo");
		actNames.add("TextSelectedDemo");
		actNames.add("MPushVCodeDemo");
		actNames.add("ShareIntentDemo");
		actNames.add("ImageLockDemo");
		actNames.add("ClipboardDemo");
		actNames.add("AudioManagerDemo");
		actNames.add("CanlanderDemo");
		actNames.add("ActJumpA");
		actNames.add("MainActivity");
		actNames.add("ListDemo");
		actNames.add("ListDownDemo");
		actNames.add("ImageViewSrcDemo");
		actNames.add("GridViewMenuDemo");
		actNames.add("SingleImageViewDemo");
		actNames.add("MultiImageViewDemo");
		actNames.add("OnGestureDemo");
		actNames.add("TextLongDemo");
		actNames.add("TextViewLongDemo");
		actNames.add("TranslateDemo");
		actNames.add("SessionAct");
		actNames.add("PopupWindowDemoActivity");
		actNames.add("SensorTestActivity");
		actNames.add("ViewPopupWindowDemo");
		actNames.add("CroutonDemo");
		actNames.add("TopPopupDemo");
		actNames.add("LoadingDialogDemo");
		actNames.add("MainDemos");
		actNames.add("AnimationDemo");
		actNames.add("StartDoDemo");
		actNames.add("MyDrawViewAct");
		actNames.add("EditTextDemo");
		actNames.add("ImageCompressDemo");
		actNames.add("MoveViewDemo");
		actNames.add("ImagePressedDemo");
		actNames.add("ExtraViewDemo");
		actNames.add("NettyDemo");
		actNames.add("NoticationDemo");
		actNames.add("LuckySettingDemo");
		actNames.add("TabMenuDemo");
		actNames.add("AnimatorDemo");
		actNames.add("SurfaceViewDemo");
		actNames.add("RoundDemo");
		actNames.add("InputBoxDemo");
		actNames.add("OnTouchDemo");
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_demos);
		
		initLayout();
		initListener();

		byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

		long t0 = System.currentTimeMillis();
		String encodeBytes = Base64.encodeToString(ivBytes, Base64.NO_WRAP);
//		byte[] encodeBytes = Base64.encode(ivBytes, Base64.NO_WRAP);
		long t1 = System.currentTimeMillis();
		DebugTool.info(tag, "加密耗时：" + (t1-t0));
		DebugTool.info(tag, "加密后：" + encodeBytes);
//		printBytes(encodeBytes);
		byte[] decodeBytes = Base64.decode(encodeBytes, Base64.NO_WRAP);
		long t2 = System.currentTimeMillis();
		DebugTool.info(tag, "解密密耗时：" + (t2-t1));
		printBytes(decodeBytes);


		String mode = "MD5withRSA";
		String encodeMode = Base64.encodeToString(mode.getBytes(), Base64.NO_WRAP);
		DebugTool.info(tag, "encodeMode加密：" + encodeMode);
		String decodeMode = new String(Base64.decode(encodeMode.getBytes(), Base64.NO_WRAP));
		DebugTool.info(tag, "decodeMode：" + decodeMode);

		Intent intent = new Intent(this, FloatViewService.class);
		startService(intent);

		ToastUtils.showMessage(this, JniUtils.getStringFromC());
	}

	private void printBytes(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (byte b1 : bs) {
			sb.append(b1);
			sb.append(" ");
		}
		DebugTool.info(tag, "原数据：" + sb.toString());
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		listView = (ListView) findViewById(R.id.listview);
		initActNames();
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, actNames));
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String className = mAct.getPackageName() + ".acts." + actNames.get(arg2);
				Class itemClass;
				try {
					itemClass = Class.forName(className);
					
					Intent intent = new Intent(mAct, itemClass);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Intent intent = new Intent(this, FloatViewService.class);
		stopService(intent);
	}
}
