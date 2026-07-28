package com.agychat.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.stripAnsi(): String {
    return RegexPatterns.ANSI_ESCAPE.replace(this, "")
}

fun ByteArray.toUtf8String(): String {
    return String(this, Charsets.UTF_8)
}

fun Long.toFormattedTime(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(this))
}

fun String.truncate(maxLen: Int): String {
    return if (this.length > maxLen) this.take(maxLen) + "..." else this
}
