package com.artion.androiddemos.acts;

import android.os.Bundle;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.view.avi.LoadingIndicatorView;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by caijinsheng on 17/11/20.
 */

public class ActIndicatorDemo extends BaseActivity {

    private AVLoadingIndicatorView avi1, avi2, avi3, avi4;
    LoadingIndicatorView avi5;

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
        avi5.hide();
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        avi1 = (AVLoadingIndicatorView) findViewById(R.id.avi1);
        avi2 = (AVLoadingIndicatorView) findViewById(R.id.avi2);
        avi3 = (AVLoadingIndicatorView) findViewById(R.id.avi3);
        avi4 = (AVLoadingIndicatorView) findViewById(R.id.avi4);
        avi5 = (LoadingIndicatorView) findViewById(R.id.avi5);
        avi5.setIndicatorColor(getResources().getColor(R.color.tab_menu_normal));
//        avi5.setWidthAndHeight(100, 100, 300, 300);
        avi5.setDuration(1000);
        avi1.show();
        avi2.show();
        avi3.smoothToShow();
        avi4.smoothToShow();
        avi5.smoothToShow();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
