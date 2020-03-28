package com.artion.androiddemos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * Created by caijinsheng on 2020/3/26.
 */

public class KeyValueView extends LinearLayout {

    KeyValueItemView keyItemView;
    KeyValueItemView valueItemView;

    public KeyValueView(Context context) {
        super(context);
        initView(context);
    }

    public KeyValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KeyValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        keyItemView = new KeyValueItemView(context);
        keyItemView.setTvTips("Key:");
        keyItemView.setEditHint("input key");
        addView(keyItemView);

        valueItemView = new KeyValueItemView(context);
        valueItemView.setTvTips("Value:");
        valueItemView.setEditHint("input value");
        addView(valueItemView);

    }

    public void setKeyTips(String tips) {
        if(keyItemView != null) {
            keyItemView.setTvTips(tips);
        }
    }

    public void setValueTips(String tips) {
        if(valueItemView != null) {
            valueItemView.setTvTips(tips);
        }
    }

    public String getKey() {
        if(keyItemView != null) {
            return keyItemView.getEditValue();
        }
        return "";
    }

    public String getValue() {
        if(valueItemView != null) {
            return valueItemView.getEditValue();
        }
        return "";
    }

    public void setKeyDefault(String value) {
        if(keyItemView != null) {
            keyItemView.setEditValue(value);
        }
    }

    public void setValueDefault(String value) {
        if(valueItemView != null) {
            valueItemView.setEditValue(value);
        }
    }
}
