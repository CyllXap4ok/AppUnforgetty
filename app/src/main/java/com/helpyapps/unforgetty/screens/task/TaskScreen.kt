package com.helpyapps.unforgetty.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helpyapps.unforgetty.R
import com.helpyapps.unforgetty.data.tasks_data_base.TaskEntity
import com.helpyapps.unforgetty.notification.NotificationType
import com.helpyapps.unforgetty.screens.task.items.input.PickInput
import com.helpyapps.unforgetty.screens.task.items.input.TextInput
import com.helpyapps.unforgetty.screens.task.items.picker_dialog.NotificationPickerDialog
import com.helpyapps.unforgetty.screens.task.items.picker_dialog.TimePickerDialog

@Composable
fun TaskScreen(
    taskData: TaskEntity
) {
    Surface {

        var selectedFromHour by remember { mutableStateOf(taskData.fromHour) }
        var selectedFromMinute by remember { mutableStateOf(taskData.fromMinute) }
        var selectedToHour by remember { mutableStateOf(taskData.toHour) }
        var selectedToMinute by remember { mutableStateOf(taskData.toMinute) }

        var showTimePickerDialog by remember { mutableStateOf(false) }
        TimePickerDialog(
            show = showTimePickerDialog,
            fromHour = selectedFromHour,
            fromMinute = selectedFromMinute,
            toHour = selectedToHour,
            toMinute = selectedToMinute,
            onDismiss = { showTimePickerDialog = false },
            onConfirm = { fromHour, fromMinute, toHour, toMinute ->
                showTimePickerDialog = false
                selectedFromHour = fromHour
                selectedFromMinute = fromMinute
                selectedToHour = toHour
                selectedToMinute = toMinute
            }
        )

        var showNotificationPickerDialog by remember { mutableStateOf(false) }
        var notificationType by rememberSaveable { mutableStateOf<NotificationType?>(null) }
        var notificationText by rememberSaveable { mutableStateOf("") }
        NotificationPickerDialog(
            show = showNotificationPickerDialog,
            initialNotificationType = notificationType,
            fromHour = selectedFromHour,
            fromMinute = selectedFromMinute,
            toHour = selectedToHour,
            toMinute = selectedToMinute,
            onDismiss = { showNotificationPickerDialog = false },
            onConfirm = { notifType, notifInterval ->
                notificationType = notifType
                notificationText = notifInterval.toString() // TODO
                showNotificationPickerDialog = false
            }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text("0${taskData.day}.".takeLast(3) + "0${taskData.month}".takeLast(2) + ".${taskData.year}", fontSize = 18.sp)

            val focusManager = LocalFocusManager.current
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = taskData.title,
                placeholderText = "ЗАГОЛОВОК",
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.title_icon),
                    contentDescription = ""
                ) },
                trailingIcon = { Icon(
                    painter = painterResource(R.drawable.sharp_asterisk_24),
                    modifier = Modifier.scale(0.6f),
                    contentDescription = ""
                ) },
                singleLine = true,
                onHideClick = { focusManager.clearFocus() },
                onDoneClick = { focusManager.clearFocus() }
            )

            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = taskData.text,
                placeholderText = "КОММЕНТАРИЙ",
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.comment_icon),
                    contentDescription = ""
                ) },
                minLines = 1,
                expandable = true,
                onHideClick = { focusManager.clearFocus() },
                onDoneClick = { focusManager.clearFocus() }
            )

            PickInput(
                text = createTimeIntervalText(selectedFromHour, selectedFromMinute, selectedToHour, selectedToMinute),
                hintText = "ВЫБРАТЬ ВРЕМЯ",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.time_icon),
                    contentDescription = ""
                ) },
                trailingIcon = { Icon(
                    painter = painterResource(R.drawable.sharp_asterisk_24),
                    modifier = Modifier.scale(0.6f),
                    contentDescription = ""
                ) },
                onPickClick = {
                    focusManager.clearFocus()
                    showTimePickerDialog = true
                }
            )

            PickInput(
                text = notificationText,
                hintText = "НАСТРОИТЬ УВЕДОМЛЕНИЯ",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(
                    painter = painterResource(when(notificationType) {
                        NotificationType.Alarm -> R.drawable.alarm_icon
                        NotificationType.Vibration -> R.drawable.vibration_icon
                        NotificationType.Silent -> R.drawable.silence_icon
                        else -> R.drawable.notification_icon
                    }),
                    contentDescription = ""
                ) },
                trailingIcon = { Icon(
                    painter = painterResource(R.drawable.sharp_asterisk_24),
                    modifier = Modifier.scale(0.6f),
                    contentDescription = ""
                ) },
                onPickClick = {
                    focusManager.clearFocus()
                    showNotificationPickerDialog = true
                }
            )

        }

    }

}

private fun createTimeIntervalText(fromHour: Int?, fromMinute: Int?, toHour: Int?, toMinute: Int?): String {
    val fromTime = if (fromHour != null && fromMinute != null) {
        createTimeText(fromHour, fromMinute)
    } else ""

    val toTime = if (toHour != null && toMinute != null) {
        " - " + createTimeText(toHour, toMinute)
    } else ""

    return fromTime + toTime
}

private fun createTimeText(hour: Int, minute: Int): String {
    val hourText = if (hour < 10) "0$hour" else "$hour"
    val minuteText = if (minute < 10) ":0$minute" else ":$minute"

    return hourText + minuteText
}