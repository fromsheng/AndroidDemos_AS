package com.example

/**
 * Created by caijinsheng on 17/6/9.
 *
 */
class Person {
    var name:String ?= null
    var age:Int ?= 10

    override fun toString(): String {
        return "name=${name}, age=${age}"
    }

}