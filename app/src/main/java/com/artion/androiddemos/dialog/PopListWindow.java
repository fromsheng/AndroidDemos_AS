package com.artion.androiddemos.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.artion.androiddemos.R;

import java.util.ArrayList;
import java.util.List;

public class PopListWindow {
	
	public static class ListItem {
		public String title;
		public ListItemLister itemLister;

		public interface ListItemLister {
			void onClick(int pos, String title);
		}
	}
	
	private PopupWindow mPopWin;
	private Context mContext;
	private TextView tvExtra;

	private List<ListItem> listItems = null;
	private PopAdapter mAdapter = null;

	public PopListWindow(Context context) {
		this.mContext = context;
		
		initPopWind();
	}
	
	private void initPopWind() {
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View popupWindow = layoutInflater.inflate(R.layout.pop_list, null);
		ListView lv = (ListView) popupWindow.findViewById(R.id.pop_listview);
		tvExtra = (TextView) popupWindow.findViewById(R.id.pop_list_extra);
		listItems = new ArrayList<ListItem>();
		mAdapter = new PopAdapter(mContext, listItems);
		lv.setAdapter(mAdapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListItem item = listItems.get(position);
				if(item != null) {
					item.itemLister.onClick(position, item.title);
				}
				mPopWin.dismiss();
			}
		});

		mPopWin = new PopupWindow(popupWindow, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPopWin.setOutsideTouchable(true);  
		mPopWin.update();  
		mPopWin.setTouchable(true);  
		mPopWin.setFocusable(true);
		mPopWin.setBackgroundDrawable(new ColorDrawable());
	}

	public void resetListItems(View parentView, List<ListItem> items) {
		if(parentView == null || items == null || items.isEmpty()) {
			return;
		}
		int[] location = new  int[2] ;
//		parentView.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
		parentView.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
//		mPopWin.showAsDropDown(parentView, (parentView.getWidth()-mPopWin.getWidth())/2, -parentView.getHeight()/2);
		mPopWin.showAtLocation(parentView, Gravity.NO_GRAVITY, location[0] + 10, location[1] + parentView.getHeight()/2);
		this.listItems.clear();
		this.listItems.addAll(items);
		mAdapter.notifyDataSetChanged();
	}
	
	private class PopAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<ListItem> mDatas;
		
		public PopAdapter(Context context, List<ListItem> list) {
			this.mInflater = LayoutInflater.from(context);
			this.mDatas = list;
		}

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.pop_list_item, null);
			}
			
			ListItem item = mDatas.get(position);
			
			TextView title = (TextView) convertView.findViewById(R.id.pop_list_item_title);
			title.setText(item.title);
			
			return convertView;
		}
		
		
	}

}
