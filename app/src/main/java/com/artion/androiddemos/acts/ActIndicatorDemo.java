package com.artion.androiddemos.acts;

import android.os.Bundle;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by caijinsheng on 17/11/20.
 */

public class ActIndicatorDemo extends BaseActivity {

    private AVLoadingIndicatorView avi1, avi2, avi3, avi4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_indicator_demo);

        initLayout();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        avi1.hide();
        avi2.hide();
        avi3.hide();
        avi4.hide();
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        avi1 = (AVLoadingIndicatorView) findViewById(R.id.avi1);
        avi2 = (AVLoadingIndicatorView) findViewById(R.id.avi2);
        avi3 = (AVLoadingIndicatorView) findViewById(R.id.avi3);
        avi4 = (AVLoadingIndicatorView) findViewById(R.id.avi4);

        avi1.show();
        avi2.show();
        avi3.smoothToShow();
        avi4.smoothToShow();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
