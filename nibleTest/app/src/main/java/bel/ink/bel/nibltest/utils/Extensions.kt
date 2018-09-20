package bel.ink.bel.nibltest.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
	val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
	return df.format(this)
}

fun Long.toDateStringFull(dateFormat: Int = DateFormat.FULL): String {
	val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
	return df.format(this)
}

fun Long.toTimeLong(): String {
	val date = Date(this)
	val formatter = SimpleDateFormat("HH:mm:ss")
	formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
	val dateFormatted = formatter.format(date)
	return dateFormatted
}

fun Long.toDateStringMinute(dateFormat: Int = DateFormat.MINUTE_FIELD): String {
	val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
	return df.format(this)
}

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)