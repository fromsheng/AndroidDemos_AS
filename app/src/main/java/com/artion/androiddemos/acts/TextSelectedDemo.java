package com.artion.androiddemos.acts;

import android.os.Bundle;
import android.widget.TextView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;

/**
 * Created by caijinsheng on 16/12/14.
 */

public class TextSelectedDemo extends BaseActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_text_selected);

        initLayout();
        initListener();
    }

    @Override
    protected void initLayout() {
        super.initLayout();

//        textView = (TextView) findViewById(R.id.text_selected);
//        textView.setTextIsSelectable(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
