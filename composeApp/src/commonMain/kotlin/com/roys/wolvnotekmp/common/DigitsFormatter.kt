package com.roys.wolvnotekmp.common

fun digitsFormatter(input: String): String {
    val digits = input.filter { it.isDigit() }
    val number = digits.toLongOrNull() ?: return ""
    return number.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}