package com.helpyapps.unforgetty.views.calendar.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.LocalDate

private val currentDate = LocalDate.now()

@Composable
fun rememberCalendarState(localDate: LocalDate = currentDate): CalendarState {
    return rememberSaveable(saver = CalendarState.Saver) {
        CalendarState(localDate)
    }
}

class CalendarState(
    private var localDate: LocalDate = LocalDate.now()
) {

    var year by mutableIntStateOf(localDate.year)
        private set
    var month by mutableIntStateOf(localDate.monthValue)
        private set
    private var selectedDate by mutableStateOf(localDate)
    val selectedDay get() = selectedDate.dayOfMonth

    var firstDayOffset by mutableIntStateOf(
        LocalDate.of(localDate.year, localDate.monthValue, 1).dayOfWeek.ordinal
    )
        private set

    private var lengthOfMonth by mutableIntStateOf(localDate.lengthOfMonth())

    private var daysWithTasks = mutableStateListOf<Int>()

    val days get() = List(lengthOfMonth) { i ->
        CalendarDay(
            number = i+1,
            haveTasks = daysWithTasks.contains(i+1)
        )
    }.also {
        if (year == currentDate.year && month == currentDate.monthValue) {
            it[currentDate.dayOfMonth-1].isCurrent = true
        }
        if (year == selectedDate.year && month == selectedDate.monthValue) {
            it[selectedDay-1].isSelected = true
        }
    }

    fun setDaysWithTasks(days: List<Int>) {
        daysWithTasks.clear()
        daysWithTasks.addAll(days)
    }

    fun addDayWithTask(day: Int) {
        if (!daysWithTasks.contains(day)) daysWithTasks.add(day)
    }

    fun nextMonth() {
        localDate = localDate.plusMonths(1)
        updateValues()
    }

    fun prevMonth() {
        localDate = localDate.minusMonths(1)
        updateValues()
    }

    fun selectDay(day: CalendarDay) {
        selectedDate = localDate.withDayOfMonth(day.number)
    }

    private fun updateValues() {
        year = localDate.year
        month = localDate.monthValue
        lengthOfMonth = localDate.lengthOfMonth()
        firstDayOffset = LocalDate.of(localDate.year, localDate.monthValue, 1).dayOfWeek.ordinal
    }

    companion object {
        val Saver: Saver<CalendarState, *> = listSaver(
            save = {
                listOf(
                    it.localDate,
                    it.selectedDate
                )
            },
            restore = {
                CalendarState(it[0]).apply {
                    selectedDate = it[1]
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