package com.mylearnings.ktorchat.util

import java.text.DateFormat
import java.util.Date

fun Long.formatTimeStamp(): String {
    return DateFormat
        .getTimeInstance(DateFormat.DEFAULT)
        .format(Date(this))
}