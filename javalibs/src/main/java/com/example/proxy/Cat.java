package com.example.proxy;

/**
 * Created by caijinsheng on 17/6/22.
 */

public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public String voice() {
        return "喵喵";
    }
}
