package com.artion.androiddemos.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artion.androiddemos.common.AndroidUtils;
import com.artion.androiddemos.common.ViewUtils;

/**
 * Created by caijinsheng on 2020/3/26.
 */

public class KeyValueItemView extends LinearLayout {

    private TextView tvTips;
    private EditText editText;

    public KeyValueItemView(Context context) {
        super(context);
        initView(context);
    }

    public KeyValueItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KeyValueItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
        int paddingH = AndroidUtils.dip2px(context, 10);
        setPadding(paddingH, 0, paddingH, 0);
        setGravity(Gravity.CENTER_VERTICAL);
        tvTips = new TextView(context);
        tvTips.setTextColor(Color.parseColor("#FF333333"));
        tvTips.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        addView(tvTips);

        editText = new EditText(context);
        editText.setTextColor(Color.parseColor("#FF333333"));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        params.leftMargin = 10;
        addView(editText, params);

    }

    public void setTvTips(String tips) {
        if(tvTips != null && !TextUtils.isEmpty(tips)) {
            tvTips.setText(tips);
        }
    }

    public void setEditHint(String hint) {
        if(editText != null && !TextUtils.isEmpty(hint)) {
            editText.setHint(hint);
        }
    }

    public void setEditValue(String value) {
        if(editText != null && !TextUtils.isEmpty(value)) {
            editText.setText(value);
        }
    }

    public String getEditValue() {
        if(editText != null) {
            return editText.getText().toString();
        }
        return "";
    }
}
