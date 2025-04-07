package com.helpyapps.unforgetty.view.common.calendar.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helpyapps.unforgetty.view.common.calendar.model.CalendarDay

@Composable
fun CalendarCell(
    day: CalendarDay,
    focusRequester: FocusRequester,
    onClick: () -> Unit
) {

    val defaultCellColor = MaterialTheme.colorScheme.background
    val selectedCellColor = MaterialTheme.colorScheme.onBackground

    var cellColor by remember { mutableStateOf(Color.Unspecified) }
    var textColor by remember { mutableStateOf(Color.Unspecified) }
    var enabled by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .size(45.dp, 45.dp)
            .background(color = cellColor, shape = RoundedCornerShape(10))
            .border(
                width = 2.dp,
                color = if (day.isCurrent) selectedCellColor else Color.Transparent,
                shape = RoundedCornerShape(10)
            )
            .focusRequester(focusRequester)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = enabled
            ) {
                focusRequester.requestFocus()
                onClick()
            }
            .onFocusChanged {
                if (it.isFocused) {
                    cellColor = selectedCellColor
                    textColor = defaultCellColor
                    enabled = false
                }  else {
                    cellColor = defaultCellColor
                    textColor = selectedCellColor
                    enabled = true
                }
            }
            .focusable(),
        contentAlignment = Alignment.TopEnd
    ) {
        if (day.haveTasks) {
            Box(
                modifier = Modifier.size(10.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Box(
                    modifier = Modifier.background(color = textColor, shape = CircleShape).size(5.dp)
                )
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .matchParentSize(),
            text = day.number.toString(),
            color = textColor,
            fontSize = 16.sp
        )
    }

}