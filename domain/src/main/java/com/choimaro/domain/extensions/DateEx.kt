package com.choimaro.domain.extensions

import java.text.SimpleDateFormat
import java.util.Date

fun Date.getFormattedDate(): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
    return this.let { formatter.format(it) } ?: "Unknown date"
}