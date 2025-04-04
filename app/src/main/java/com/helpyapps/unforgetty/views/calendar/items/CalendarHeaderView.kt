package com.helpyapps.unforgetty.views.calendar.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helpyapps.unforgetty.R

@Composable
fun CalendarHeaderView(
    modifier: Modifier,
    month: Int,
    year: Int,
    onMonthSwipeClick: (SwipeDirection) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = { onMonthSwipeClick(SwipeDirection.LEFT) }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, "left")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = stringResource(MonthNames.entries[month-1].nameResID).uppercase(), fontSize = 22.sp)
            Text(text = year.toString(), fontSize = 22.sp)
        }

        IconButton(
            onClick = { onMonthSwipeClick(SwipeDirection.RIGHT) }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, "right")
        }

    }

}

enum class MonthNames(val nameResID: Int) {
    JANUARY(R.string.month_1),
    FEBRUARY(R.string.month_2),
    MARCH(R.string.month_3),
    APRIL(R.string.month_4),
    MAY(R.string.month_5),
    JUNE(R.string.month_6),
    JULY(R.string.month_7),
    AUGUST(R.string.month_8),
    SEPTEMBER(R.string.month_9),
    OCTOBER(R.string.month_10),
    NOVEMBER(R.string.month_11),
    DECEMBER(R.string.month_12)
}

enum class SwipeDirection {
    LEFT,
    RIGHT
}