package com.helpyapps.unforgetty.view.common.calendar.model

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun rememberCalendarState(localDate: LocalDate = LocalDate.now()): CalendarState {
    return rememberSaveable(saver = CalendarState.Saver) {
        CalendarState(localDate)
    }
}

class CalendarState(
    initialDate: LocalDate = LocalDate.now(),
    private val locale: Locale = Locale.getDefault()
) {

    // Текущая дата для сравнения
    private val currentDate = LocalDate.now()

    // Основная дата
    private var _baseDate by mutableStateOf(initialDate)

    // Выбранная дата
    private var _selectedDate by mutableStateOf(initialDate)

    // Дни с задачами
    private val _daysWithTasks = mutableStateListOf<Int>()

    val year: Int get() = _baseDate.year
    val month: Month get() = _baseDate.month
    val selectedDay: Int get() = _selectedDate.dayOfMonth
    val monthName: String get() =
        month.getDisplayName(TextStyle.FULL_STANDALONE, locale).replaceFirstChar { it.uppercase() }

    // Смещение первого дня месяца (0-6)
    val firstDayOffset: Int get() = _baseDate.withDayOfMonth(1).dayOfWeek.ordinal

    val days: List<CalendarDay> get() = List(_baseDate.lengthOfMonth()) { dayNumber ->
        val day = dayNumber + 1
        CalendarDay(
            number = day,
            haveTasks = _daysWithTasks.contains(day),
            isCurrent = isCurrentDay(day),
            isSelected = isSelectedDay(day)
        )
    }

    val viewDays: List<CalendarDay?> get() {
        val daysList = mutableListOf<CalendarDay?>().apply {
            Log.d("AAA", firstDayOffset.toString())
            repeat(firstDayOffset) { add(null) }
            addAll(days)
        }

        return daysList
    }

    private fun isCurrentDay(day: Int): Boolean {
        return _baseDate.year == currentDate.year &&
                _baseDate.month == currentDate.month &&
                day == currentDate.dayOfMonth
    }

    private fun isSelectedDay(day: Int): Boolean {
        return _baseDate.year == _selectedDate.year &&
                _baseDate.month == _selectedDate.month &&
                day == _selectedDate.dayOfMonth
    }

    // Обновляет дни с задачами
    fun updateDaysWithTasks(days: List<Int>) {
        _daysWithTasks.clear()
        _daysWithTasks.addAll(days)
    }

    // Добавляет день с задачами
    fun addDayWithTask(day: Int) {
        if (!_daysWithTasks.contains(day)) _daysWithTasks.add(day)
    }

    fun nextMonth() {
        _baseDate = _baseDate.plusMonths(1)
    }

    fun prevMonth() {
        _baseDate = _baseDate.minusMonths(1)
    }

    fun selectDay(day: Int) {
        _selectedDate = _baseDate.withDayOfMonth(day)
    }

    companion object {
        val Saver: Saver<CalendarState, *> = listSaver(
            save = {
                listOf(
                    it._baseDate,
                    it._selectedDate
                )
            },
            restore = {
                CalendarState(it[0]).apply {
                    _selectedDate = it[1]
                }
            }
        )
    }

}

data class CalendarDay(
    val number: Int,
    val haveTasks: Boolean = false,
    var isCurrent: Boolean = false,
    var isSelected: Boolean = false
)