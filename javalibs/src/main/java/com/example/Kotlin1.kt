package com.example

/**
 * Created by caijinsheng on 17/6/9.
 *
 */
fun sum(a: Int, b: Int): Int {
    return a + b
}

fun main(args: Array<String>) {
    print("sum of 3 and 5 is ")
    println(sum(3, 5))


    var list = listOf("AA", "BB", "CC");
    for ((i, item) in list.withIndex()) {
        print(i)
        print("${item} ")
    }

    var person = Person()
    println(person.toString())

    person.name="dfdfd"
    person.age=20
    println(person.toString())

    var v=11
    when(v) {
        10 -> println("${v}=10")
        is Int -> println("${v} is Int")
    }

    when {
        v < 0 ->println("${v}<0")
        v >1 && v < 10 ->println("1<${v}<10")
        else ->println("${v}")
    }

    var s1: String? = null
    println(s1)
    var s2 = s1!!.toInt()
    println(s2)

}