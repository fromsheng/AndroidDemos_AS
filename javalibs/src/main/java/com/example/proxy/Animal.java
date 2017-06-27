package com.example.proxy;

/**
 * Created by caijinsheng on 17/6/22.
 */

public abstract class Animal {
    protected String name;
    public Animal(String name) {
        this.name = name;
    }
    public abstract String voice();

    public void info() {
        System.out.println("这是" + name + ",叫声是" + voice());
    }
}
