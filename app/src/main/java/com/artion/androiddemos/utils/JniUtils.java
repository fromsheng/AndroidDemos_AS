package com.artion.androiddemos.utils;

/**
 * Created by caijinsheng on 17/9/21.
 */

public class JniUtils {

    static {
        System.loadLibrary("helloJni");
    }

    public static native String getStringFromC();

}
