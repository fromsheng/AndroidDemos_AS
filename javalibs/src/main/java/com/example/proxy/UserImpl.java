package com.example.proxy;

/**
 * Created by caijinsheng on 17/6/22.
 */

public class UserImpl implements User {

    private String name;

    public UserImpl(String name) {
        this.name = name;
    }

    @Override
    public void login() {
        System.out.println(name + "执行login。。。");
    }

    @Override
    public void updateInfo() {
        System.out.println(name + "执行updateInfo。。。");
    }

    @Override
    public void updateInfo(String msg) {
        System.out.println(name + "执行updateInfo。。。" + msg);
    }
}
