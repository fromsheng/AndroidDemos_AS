package com.example;

import java.lang.reflect.Field;

/**
 * Created by caijinsheng on 18/6/7.
 */

public class AppleDemo {
    public static void main(String[] args) {
        System.out.println("Hello java world");

        Apple apple = new Apple();


        Field[] fields = Apple.class.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Fruit.class)) {
                Fruit fruit = field.getAnnotation(Fruit.class);
                System.out.println(fruit.name() + ":" + fruit.color());
            }
        }
    }
}
