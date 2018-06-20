package com.artion.androiddemos.acts

import android.content.Intent
import android.os.Bundle
import com.artion.androiddemos.common.ToastUtils

/**
 * Created by caijinsheng on 18/3/15.
 */

class MyKotlinAct : CommonBtnDemo() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initLayout() {
        super.initLayout()
        btn1.text = "MyKotlinBtn1"
    }

    override fun initListener() {
        super.initListener()
        btn1.setOnClickListener {
            startActivity(Intent(mAct, MyKotlinAct::class.java))
            ToastUtils.showMessage(mAct, "MyKotlinBtn1")
        }
    }
}
