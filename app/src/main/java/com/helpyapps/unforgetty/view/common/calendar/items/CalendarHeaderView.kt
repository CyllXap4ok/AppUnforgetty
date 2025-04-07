package com.helpyapps.unforgetty.view.common.calendar.items

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarHeaderView(
    modifier: Modifier,
    month: String,
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
            Text(text = month, fontSize = 22.sp)
            Text(text = year.toString(), fontSize = 22.sp)
        }

        IconButton(
            onClick = { onMonthSwipeClick(SwipeDirection.RIGHT) }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, "right")
        }

    }

}

enum class SwipeDirection {
    LEFT,
    RIGHT
}