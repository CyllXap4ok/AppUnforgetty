@file:OptIn(ExperimentalMaterial3Api::class)

package com.helpyapps.unforgetty.view.screen.task.items

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastRoundToInt
import androidx.compose.ui.window.Dialog
import com.helpyapps.unforgetty.R
import com.helpyapps.unforgetty.model.notification.NotificationType

@Composable
fun NotificationPickerDialog(
    show: Boolean = false,
    initialNotificationType: NotificationType? = null,
    fromHour: Int?,
    fromMinute: Int?,
    toHour: Int?,
    toMinute: Int?,
    onDismiss: () -> Unit = {},
    onConfirm: (notificationType: NotificationType, notificationInterval: Int) -> Unit = { _, _ -> },
) {

    var notificationType by remember { mutableStateOf(initialNotificationType?:NotificationType.Alarm) }

    if (show) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .animateContentSize(
                        tween(100)
                    ),
                shape = RoundedCornerShape(10)
            ) {
                Column(
                    modifier = Modifier.padding(top = 5.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("Тип уведомления:")

                        NotificationType.entries.forEach {

                            val focusRequester = remember { FocusRequester() }
                            NotificationIcon(
                                iconResourceID = it.iconResourceID,
                                focusRequester = focusRequester,
                                onClick = { notificationType = it }
                            )
                            SideEffect {
                                if (notificationType == it) focusRequester.requestFocus()
                            }

                        }

                    }

                    if (toHour != null) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {

                            var hourSliderValue by remember { mutableFloatStateOf(0f) }
                            var minuteSliderValue by remember { mutableFloatStateOf(5f) }

                            Text("Повторять раз в: " + if (hourSliderValue > 0) {
                                pluralStringResource(
                                    R.plurals.hour_plurals,
                                    hourSliderValue.fastRoundToInt(),
                                    hourSliderValue.fastRoundToInt()
                                ) + " "
                            } else { "" }
                                    +
                                    if (minuteSliderValue > 0) {
                                        pluralStringResource(
                                            R.plurals.minute_plurals,
                                            minuteSliderValue.fastRoundToInt(),
                                            minuteSliderValue.fastRoundToInt()
                                        )
                                    } else "")

                            if (toHour - fromHour!! > 1) {
                                Slider(
                                    value = hourSliderValue,
                                    onValueChange = { hourSliderValue = it },
                                    steps = toHour - fromHour - 1,
                                    valueRange = 0f..(toHour - fromHour - 1).toFloat(),
                                    thumb = {
                                        Box(
                                            Modifier
                                                .size(20.dp)
                                                .background(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = CutCornerShape(10)
                                                )
                                        )
                                    },
                                    track = {
                                        SliderDefaults.Track(sliderState = it, thumbTrackGapSize = 0.dp)
                                    }
                                )
                            }

                            Slider(
                                value = minuteSliderValue,
                                onValueChange = { minuteSliderValue = it },
                                steps = 10,
                                valueRange = 0f..55f,
                                thumb = {
                                    Box(
                                        Modifier
                                            .size(20.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = CutCornerShape(10)
                                            )
                                    )
                                },
                                track = {
                                    SliderDefaults.Track(sliderState = it, thumbTrackGapSize = 0.dp)
                                }
                            )

                        }

                    }

                    Column {
                        HorizontalDivider()
                        Row(
                            modifier = Modifier.height(40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ConfirmationButton(
                                modifier = Modifier.weight(1f),
                                text = "Отменить"
                            ) { onDismiss() }
                            VerticalDivider()
                            ConfirmationButton(modifier = Modifier.weight(1f), text = "Применить") {
                                onConfirm(notificationType, 10)
                            }
                        }
                    }

                }
            }
        }
    }

}