package com.helpyapps.unforgetty.view.common.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.helpyapps.unforgetty.view.common.calendar.items.CalendarCell
import com.helpyapps.unforgetty.view.common.calendar.items.CalendarHeaderView
import com.helpyapps.unforgetty.view.common.calendar.items.HorizontalGradientDivider
import com.helpyapps.unforgetty.view.common.calendar.items.SwipeDirection
import com.helpyapps.unforgetty.view.common.calendar.items.WeekDaysView

@Composable
fun CalendarView(
    calendarState: CalendarState,
    onMonthChanged: () -> Unit = {},
    onDayClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    CalendarHeaderView(
        modifier = Modifier.fillMaxWidth(),
        month = calendarState.monthName,
        year = calendarState.year,
        onMonthSwipeClick = { swipeDirection ->
            when(swipeDirection) {
                SwipeDirection.LEFT -> calendarState.prevMonth()
                SwipeDirection.RIGHT -> calendarState.nextMonth()
            }
            onMonthChanged()
            focusManager.clearFocus()
        }
    )

    WeekDaysView(Modifier.fillMaxWidth())

    HorizontalGradientDivider(thickness = 1.dp)

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterVertically),
        horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally),
        columns = GridCells.Fixed(7)
    ) {

        items(calendarState.viewDays) { day ->

            day?.let {
                val focusRequester = remember { FocusRequester() }

                CalendarCell(
                    day = day,
                    focusRequester = focusRequester
                ) {
                    calendarState.selectDay(day.number)
                    onDayClick()
                }

                SideEffect {
                    if (day.isSelected) focusRequester.requestFocus()
                }
            } ?: Spacer(Modifier)

        }

    }

}