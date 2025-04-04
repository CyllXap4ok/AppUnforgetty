package com.helpyapps.unforgetty.screens.main.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helpyapps.unforgetty.R
import com.helpyapps.unforgetty.data.tasks_data_base.TaskEntity
import com.helpyapps.unforgetty.notification.NotificationType

@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    task: TaskEntity,
    onClick: () -> Unit = {}
) {

    val iconID = when(task.notificationType) {
        NotificationType.Alarm.name -> R.drawable.alarm_icon
        NotificationType.Vibration.name -> R.drawable.vibration_icon
        else -> R.drawable.silence_icon
    }

    Button(
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        onClick = {
            onClick()
        }
    ) {

        Column(modifier = modifier) {
            Text(
                text = task.title,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("${task.fromHour}:${task.fromMinute} - ${task.toHour}:${task.toMinute}")
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(iconID),
                    contentDescription = "NotificationIcon"
                )
            }
        }

    }

}