package com.example.proxy;

/**
 * Created by caijinsheng on 17/6/22.
 */

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public String voice() {
        return "汪汪";
    }
}
