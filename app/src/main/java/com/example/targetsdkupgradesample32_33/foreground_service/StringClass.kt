package com.example.targetsdkupgradesample32_33.foreground_service

class StringClass {
    val number = 5
    val message = String.format("The number is: %0$03d", number)

// Output: "The number is: 005"
}

fun main() {
    val stringClass = StringClass()
    println(stringClass.message)

}