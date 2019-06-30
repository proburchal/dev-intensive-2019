package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils.inclineAfterNumeral
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

const val MILLISECOND_ms = 1L
const val SECOND_ms = 1000 * MILLISECOND_ms
const val MINUTE_ms = 60 * SECOND_ms
const val HOUR_ms = 60 * MINUTE_ms
const val DAY_ms = 24 * HOUR_ms

enum class TimeUnits(val accusatives: Triple<String, String, String>, val inMs: Long) {
    MILLISECOND(
        accusatives = Triple("миллисекунду", "миллисекунды", "миллисекунд"),
        inMs = MILLISECOND_ms
    ),
    SECOND(
        accusatives = Triple("секунду", "секунды", "секунд"),
        inMs = SECOND_ms
    ),
    MINUTE(
        accusatives = Triple("минуту", "минуты", "минут"),
        inMs = MINUTE_ms
    ),
    HOUR(
        accusatives = Triple("час", "часа", "часов"),
        inMs = HOUR_ms
    ),
    DAY(
        accusatives = Triple("день", "дня", "дней"),
        inMs = DAY_ms
    )
}

private fun optimalTimeUnit(value: Long): TimeUnits {
    for (unit in TimeUnits.values().reversed())
        if (value >= unit.inMs)
            return unit
    return TimeUnits.MILLISECOND
}

private fun normalHumanizeDiff(diffInMs: Long): String {
    val timeUnit = optimalTimeUnit(diffInMs)
    val diffInTimeUnit = diffInMs / timeUnit.inMs
    val timeUnitName = inclineAfterNumeral(diffInTimeUnit, timeUnit.accusatives)
    return "$diffInTimeUnit $timeUnitName"
}

fun Date.add(value: Long, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += value * units.inMs
    return this
}

fun Date.humanizeDiff(other: Date = Date()): String {
    val diff = other.time - this.time
    val strDiff: String =
        when (abs(diff)) {
            in 1 * SECOND_ms..45 * SECOND_ms -> "несколько секунд"
            in 45 * SECOND_ms..75 * SECOND_ms -> "минуту"
            in 75 * SECOND_ms..45 * MINUTE_ms -> normalHumanizeDiff(abs(diff))
            in 45 * MINUTE_ms..75 * MINUTE_ms -> "час"
            in 75 * MINUTE_ms..22 * HOUR_ms -> normalHumanizeDiff(abs(diff))
            in 22 * HOUR_ms..26 * HOUR_ms -> "день"
            in 26 * HOUR_ms..360 * DAY_ms -> normalHumanizeDiff(abs(diff))
            else -> ""
        }

    return when {
        abs(diff) in 0..1 * SECOND_ms -> "только что"
        diff > 360 * DAY_ms -> "более года назад"
        diff < -360 * DAY_ms -> "более чем через год"
        diff > 0 -> "$strDiff назад"
        else -> "через $strDiff"
    }
}