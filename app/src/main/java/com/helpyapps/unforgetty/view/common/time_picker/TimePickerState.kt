package com.helpyapps.unforgetty.view.common.time_picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.helpyapps.unforgetty.view.common.infinite_vertical_pager.InfiniteVerticalPagerState

@Composable
fun rememberTimePickerState(initialHour: Int, initialMinute: Int): TimePickerState {
    return rememberSaveable(saver = TimePickerState.Saver) {
        TimePickerState(initialHour, initialMinute)
    }
}

class TimePickerState(
    initialHour: Int,
    initialMinute: Int
) {

    private val hours = List(24) { "0$it".takeLast(2) }
    private val minutes = List(60) { "0$it".takeLast(2) }

    val hourState = InfiniteVerticalPagerState(initialItem = initialHour, itemCount = 24)
    val minuteState = InfiniteVerticalPagerState(initialItem = initialMinute, itemCount = 60)

    val selectedHour get() = hourState.currentItem
    val selectedMinute get() = minuteState.currentItem

    fun getHour(i: Int) = hours[i]
    fun getMinute(i: Int) = minutes[i]

    companion object {
        val Saver: Saver<TimePickerState, *> = listSaver(
            save = {
                listOf(
                    it.selectedHour,
                    it.selectedMinute
                )
            },
            restore = {
                TimePickerState(
                    it[0],
                    it[1]
                )
            }
        )
    }

}