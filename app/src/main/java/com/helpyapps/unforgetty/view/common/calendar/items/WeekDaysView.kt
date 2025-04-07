package com.helpyapps.unforgetty.view.common.calendar.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.helpyapps.unforgetty.R

@Composable
fun WeekDaysView(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        DaysNames.entries.forEach { day ->
            Text(
                text = stringResource(day.nameResID).uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

enum class DaysNames(val nameResID: Int) {
    MONDAY(R.string.day_1),
    TUESDAY(R.string.day_2),
    WEDNESDAY(R.string.day_3),
    THURSDAY(R.string.day_4),
    FRIDAY(R.string.day_5),
    SATURDAY(R.string.day_6),
    SUNDAY(R.string.day_7);
}