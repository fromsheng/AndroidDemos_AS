package com.artion.androiddemos.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.artion.androiddemos.common.AndroidUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caijinsheng on 2020/3/27.
 */

public class KeyValueExecView extends LinearLayout {
    Button btnAdd, btnExec;
    LinearLayout llPart;
    TextView tvResult;

    List<KeyValueView> keyValueList = new ArrayList<>();

    ExecListener mListener = null;

    public KeyValueExecView(Context context) {
        super(context);
        initView(context);
    }

    public KeyValueExecView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KeyValueExecView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        int paddingH = AndroidUtils.dip2px(context, 10);
        setPadding(paddingH, 0, paddingH, 0);
        setGravity(Gravity.CENTER_HORIZONTAL);

        btnAdd = new Button(context);
        btnAdd.setText("添加一对Key:Value");
        addView(btnAdd);

        btnExec = new Button(context);
        btnExec.setText("执行调用");
        addView(btnExec);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyValueView keyValueView = new KeyValueView(getContext());
                keyValueView.setKeyTips(getTips("K"));
                keyValueView.setValueTips(getTips("V"));
                keyValueView.setKeyDefault(getValueDef("key"));
                keyValueView.setValueDefault(getValueDef("value"));
                llPart.addView(keyValueView);
                keyValueList.add(keyValueView);
            }
        });

        btnExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject resJson = getResult();
                if(resJson != null) {
                    tvResult.setText(resJson.toString());
                    if(mListener != null) {
                        mListener.onClick(resJson);
                    }
                }
            }
        });

        ScrollView scrollView = new ScrollView(context);
        LinearLayout.LayoutParams lpPart = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        llPart = new LinearLayout(context);
        llPart.setOrientation(VERTICAL);
        scrollView.addView(llPart);
        addView(scrollView, lpPart);

        tvResult = new TextView(context);
        tvResult.setBackgroundColor(Color.parseColor("#99999999"));
        tvResult.setTextIsSelectable(true);
        LinearLayout.LayoutParams resParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                AndroidUtils.dip2px(context, 100));
        addView(tvResult, resParams);

    }

    private String getTips(String tips) {
        int size = keyValueList.size();
        return (size + 1) + "." + tips;
    }

    private String getValueDef(String def) {
        int size = keyValueList.size();
        return def + (size + 1);
    }

    public void setOnExecListener(ExecListener listener) {
        mListener = listener;
    }

    private JSONObject getResult() {
        JSONObject resJson = new JSONObject();
        for(KeyValueView item : keyValueList) {
            if(item != null) {
                String key = item.getKey();
                String value = item.getValue();
                if(TextUtils.isEmpty(key)) {
                    continue;
                }
                try {
                    resJson.putOpt(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return resJson;
    }

    public interface ExecListener {
        void onClick(JSONObject result);
    }
}
