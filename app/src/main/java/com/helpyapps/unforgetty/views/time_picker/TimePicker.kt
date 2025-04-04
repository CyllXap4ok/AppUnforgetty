package com.helpyapps.unforgetty.views.time_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helpyapps.unforgetty.views.infinite_vertical_pager.InfiniteVerticalPager

@Composable
fun TimePicker(
    timePickerState: TimePickerState,
    dialSize: Int = 3,
    rowHeight: Dp = 50.dp
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy((0.25 * rowHeight.value).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        InfiniteVerticalPager(
            infiniteVerticalPagerState = timePickerState.hourState,
            beyondViewportItemCount = dialSize,
            pageHeight = rowHeight
        ) { page ->
            Text(timePickerState.getHour(page), fontSize = (0.625 * rowHeight.value).sp)
        }

        Text(":", fontSize = (0.625 * rowHeight.value).sp)

        InfiniteVerticalPager(
            infiniteVerticalPagerState = timePickerState.minuteState,
            beyondViewportItemCount = dialSize,
            pageHeight = rowHeight
        ) { page ->
            Text(timePickerState.getMinute(page), fontSize = (0.625 * rowHeight.value).sp)
        }

    }

}