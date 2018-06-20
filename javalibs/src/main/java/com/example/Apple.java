package com.example;

/**
 * Created by caijinsheng on 18/6/7.
 */

public class Apple {

    static {
        System.out.println("static init");
    }

    @Fruit(name="apple", color="red")
    String name;

    public Apple() {
        System.out.println("Apple init");
    }

}
