package com.helpyapps.unforgetty.view.screen.task.items

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.helpyapps.unforgetty.view.common.time_picker.TimePicker
import com.helpyapps.unforgetty.view.common.time_picker.TimePickerState

@Composable
fun TimePickerDialog(
    show: Boolean = false,
    fromHour: Int?,
    fromMinute: Int?,
    toHour: Int?,
    toMinute: Int?,
    onDismiss: () -> Unit = {},
    onConfirm: (fromHour: Int?, fromMinute: Int?, toHour: Int?, toMinute: Int?) -> Unit = { _, _, _, _ -> },
) {

    val fromTimePickerState = TimePickerState(fromHour?:12, fromMinute?:0)
    val toTimePickerState = TimePickerState(toHour?:(fromHour?:13), toMinute?:0)

    if (show) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(10)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    var intervalOn by remember { mutableStateOf(toHour?.let{ true }?: false) }

                    Row(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { intervalOn = !intervalOn },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = intervalOn, onCheckedChange = null)
                        Text("Интервал времени")
                    }

                    Row(
                        modifier = Modifier.animateContentSize(
                            spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TimePicker(
                            timePickerState = fromTimePickerState,
                            dialSize = 3,
                            rowHeight = 50.dp
                        )

                        if (intervalOn) {
                            Text("—")
                            TimePicker(
                                timePickerState = toTimePickerState,
                                dialSize = 3,
                                rowHeight = 50.dp
                            )
                        }
                    }

                    Column {
                        HorizontalDivider()
                        Row(
                            modifier = Modifier.height(40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ConfirmationButton(modifier = Modifier.weight(1f), text = "Отменить") { onDismiss() }
                            VerticalDivider()
                            ConfirmationButton(modifier = Modifier.weight(1f), text = "Применить") { onConfirm(
                                fromTimePickerState.selectedHour,
                                fromTimePickerState.selectedMinute,
                                if (intervalOn) { toTimePickerState.selectedHour } else null,
                                if (intervalOn) { toTimePickerState.selectedMinute } else null
                            ) }
                        }
                    }
                }
            }
        }
    }

}