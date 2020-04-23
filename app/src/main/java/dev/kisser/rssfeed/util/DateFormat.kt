package dev.kisser.rssfeed.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
val simpleFormat = SimpleDateFormat("yyyy-MM-dd\nHH:mm")
@SuppressLint("SimpleDateFormat")
val fullFormat = SimpleDateFormat("EEE yyyy-MMM-dd HH:mm:ss Z")

@SuppressLint("SimpleDateFormat")
val RFC822 = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")

val epochStart = Date(0)

fun String.collapseToAsciiSum(): Int {
    return this.map { char -> char.toInt() }.reduce {a, b -> a+b}
}
