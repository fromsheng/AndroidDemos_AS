package com.artion.androiddemos.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by caijinsheng on 16/9/6.
 */
public class MyViewUtils {

    public enum SCREEN_NOTICE_ITEM_ID {
        NEVERUSED, LOGO, TITLE, CONTENT, TIME, ITEM
    }

    public static int ITEM_VIEW_BGCOLOR_NORMAL = Color.parseColor("#66ffffff");
    public static int ITEM_VIEW_BGCOLOR_PRESSED = Color.parseColor("#4dffffff");

    public static int FLOAT_VIEW_BGCOLOR_NORMAL = Color.parseColor("#e8ff00ff");
    public static int FLOAT_VIEW_BGCOLOR_PRESSED = Color.parseColor("#b4ff00ff");

    private static View getItemView(Context context) {
        LinearLayout view = new LinearLayout(context);
        view.setId(SCREEN_NOTICE_ITEM_ID.ITEM.ordinal());
        view.setBackgroundColor(ITEM_VIEW_BGCOLOR_NORMAL);
        int padding4 = AndroidUtils.dip2px(context, 8);
        //设置根布局的样式,更换RecycleView时调整点击效果需要加rootview
//        ViewGroup.LayoutParams viewParams = new ViewGroup.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
        view.setPadding(padding4, padding4, padding4, padding4);
        view.setGravity(Gravity.CENTER_VERTICAL);

        view.setOrientation(LinearLayout.HORIZONTAL);
        //设置图标的样式
        ImageView logo = new ImageView(context);
        logo.setId(SCREEN_NOTICE_ITEM_ID.LOGO.ordinal());
        ViewGroup.LayoutParams logoParams = new ViewGroup.LayoutParams(
                AndroidUtils.dip2px(context, 44),
                AndroidUtils.dip2px(context, 44));
        logo.setImageResource(context.getApplicationInfo().icon);
        logo.setLayoutParams(logoParams);
        view.addView(logo);//add logo
        //设置标题跟内容的父布局
        LinearLayout.LayoutParams lp_child = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout childView = new LinearLayout(context);
        childView.setPadding(padding4, 0, 0, 0);
        childView.setLayoutParams(lp_child);
        childView.setOrientation(LinearLayout.VERTICAL);
        //设置标题的样式
        TextView tvTitle = new TextView(context);
        tvTitle.setId(SCREEN_NOTICE_ITEM_ID.TITLE.ordinal());
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvTitle.setTextColor(Color.BLACK);
        tvTitle.setSingleLine();
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);
        childView.addView(tvTitle);//将TextView 添加到子View 中
        tvTitle.setText("title");
        //设置内容的样式
        TextView tvContent = new TextView(context);
        tvContent.setId(SCREEN_NOTICE_ITEM_ID.CONTENT.ordinal());
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvContent.setTextColor(Color.DKGRAY);
        tvContent.setSingleLine();
        tvContent.setEllipsize(TextUtils.TruncateAt.END);
        childView.addView(tvContent);//将TextView 添加到子View 中
        tvContent.setText("content");
        view.addView(childView);

        //设置时间的样式
        TextView tvTime = new TextView(context);
        tvTime.setId(SCREEN_NOTICE_ITEM_ID.TIME.ordinal());
        tvTime.setPadding(padding4, 0, padding4, 0);
        tvTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tvTime.setTextColor(Color.DKGRAY);
        tvTime.setSingleLine();
        tvTime.setEllipsize(TextUtils.TruncateAt.END);
        tvTime.setText(AndroidUtils.getCurrentTime());
        view.addView(tvTime);

//        root.addView(view);
//        return root;
        return view;
    }

    /**
     * 初始化锁屏通知列表的item布局
     * @param context
     * @return
     */
    public static View initConvertView(Context context) {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, AndroidUtils.dip2px(context, 64));
        View view = getItemView(context);
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 创建selector。colorValue使用Color.parseColor,但是对view的setbackground不行，目前未知原因
     * @param normalColorValue
     * @param pressedColorValue
     * @param focusColorValue
     * @param unableColorValue
     * @return
     */
    public static StateListDrawable newSelector(int normalColorValue, int pressedColorValue, int focusColorValue,
                                                int unableColorValue) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = normalColorValue == -1 ? null : new ColorDrawable(normalColorValue);
        Drawable pressed = pressedColorValue == -1 ? null : new ColorDrawable(pressedColorValue);
        Drawable focused = focusColorValue == -1 ? null : new ColorDrawable(focusColorValue);
        Drawable unable = unableColorValue == -1 ? null : new ColorDrawable(unableColorValue);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

    public static StateListDrawable newSelector(int normalColorValue, int pressedColorValue) {
        Drawable normal = normalColorValue == -1 ? null : getDrawble(4, FLOAT_VIEW_BGCOLOR_NORMAL, FLOAT_VIEW_BGCOLOR_NORMAL, 16);
        Drawable pressed = pressedColorValue == -1 ? null : getDrawble(4, FLOAT_VIEW_BGCOLOR_PRESSED, FLOAT_VIEW_BGCOLOR_PRESSED, 16);

        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, pressed);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, normal);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

    public static GradientDrawable getDrawble(int strokeWidth, int strokeColor, int fillColor, int roundRadius) {

        int colors[] = { 0xFFFFFF , 0xFFFFFF, 0xFFFFFF };//分别为开始颜色，中间夜色，结束颜色

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    public static View getFloatNotificationView(Context context) {
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, AndroidUtils.dip2px(context, 64));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, AndroidUtils.dip2px(context, 64));
        View view = getItemView(context);
        view.setLayoutParams(lp);

        Drawable selector = newSelector(
                FLOAT_VIEW_BGCOLOR_NORMAL, FLOAT_VIEW_BGCOLOR_PRESSED, FLOAT_VIEW_BGCOLOR_PRESSED, FLOAT_VIEW_BGCOLOR_NORMAL);
        view.setBackgroundColor(Color.TRANSPARENT);
        if(Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(selector);
        } else {
            view.setBackground(selector);
        }
        view.setDuplicateParentStateEnabled(false);

        MyViewHolder holder = new MyViewHolder(view);
        view.setTag(holder);

        return view;
    }

    public static View getToastFloatView(Context context) {
        LinearLayout view = new LinearLayout(context);
        WindowManager wm = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                dm.widthPixels - 40, AndroidUtils.dip2px(context, 64));
        View itemView = getItemView(context);
        itemView.setLayoutParams(lp);
        view.addView(itemView);

//        Drawable selector = newSelector(
//                FLOAT_VIEW_BGCOLOR_NORMAL, FLOAT_VIEW_BGCOLOR_PRESSED, FLOAT_VIEW_BGCOLOR_PRESSED, FLOAT_VIEW_BGCOLOR_NORMAL);
        Drawable selector = newSelector(
                FLOAT_VIEW_BGCOLOR_NORMAL, FLOAT_VIEW_BGCOLOR_PRESSED);
        if(Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(selector);
        } else {
            view.setBackground(selector);
        }

        MyViewHolder holder = new MyViewHolder(view);
        view.setTag(holder);

        return view;
    }

    public static MyViewHolder getMyViewHolder(View convertView) {
        if(convertView == null) {
            return null;
        }
        return (MyViewHolder) convertView.getTag();
    }

    public static class MyViewHolder {
        public TextView tvTitle, tvContent, tvTime;
        public ImageView logo;

        public MyViewHolder(View convertView) {
            tvTitle = (TextView) convertView.findViewById(SCREEN_NOTICE_ITEM_ID.TITLE.ordinal());
            tvContent = (TextView) convertView.findViewById(SCREEN_NOTICE_ITEM_ID.CONTENT.ordinal());
            tvTime = (TextView) convertView.findViewById(SCREEN_NOTICE_ITEM_ID.TIME.ordinal());
            logo = (ImageView) convertView.findViewById(SCREEN_NOTICE_ITEM_ID.LOGO.ordinal());
        }
    }

}
