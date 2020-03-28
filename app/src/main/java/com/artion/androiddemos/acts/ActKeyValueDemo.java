package com.artion.androiddemos.acts;

import android.os.Bundle;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.view.KeyValueExecView;

import org.json.JSONObject;

/**
 * Created by caijinsheng on 2020/3/26.
 */

public class ActKeyValueDemo extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KeyValueExecView execView = new KeyValueExecView(this);
        setContentView(execView);

        execView.setOnExecListener(new KeyValueExecView.ExecListener() {
            @Override
            public void onClick(JSONObject result) {
                ToastUtils.showMessage(mAct, result.toString());
            }
        });
    }

}
