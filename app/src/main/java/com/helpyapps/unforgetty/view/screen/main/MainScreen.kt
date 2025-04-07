package com.helpyapps.unforgetty.view.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.helpyapps.unforgetty.R
import com.helpyapps.unforgetty.model.tasks_db.TaskEntity
import com.helpyapps.unforgetty.model.notification.NotificationType
import com.helpyapps.unforgetty.view.screen.main.items.TaskListItem
import com.helpyapps.unforgetty.view_model.screen.main.MainScreenViewModel
import com.helpyapps.unforgetty.view.common.buttons.PlusButton
import com.helpyapps.unforgetty.view.common.calendar.CalendarView
import com.helpyapps.unforgetty.view.common.calendar.items.HorizontalGradientDivider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    vm: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    navigateToTaskScreen: (TaskEntity) -> Unit
) {
    
    val isLoading = vm.isLoading.observeAsState()

    val calendarState = vm.calendarState

    val dayTasks = vm.dayTasks

    if (isLoading.value == true) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CalendarView(
                calendarState = calendarState,
                onMonthChanged = {
                    vm.getMonthTasks(calendarState.year, calendarState.month.value)
                },
                onDayClick = {
                    vm.getDayTasks(calendarState.selectedDay)
                }
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                if (dayTasks.isEmpty()) {

                    Text(
                        text = stringResource(R.string.emptyTasks),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )

                } else {

                    LazyColumn(
                        modifier = Modifier.align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(dayTasks) { task ->
                            TaskListItem(modifier = Modifier.fillMaxWidth(), task) {
                                navigateToTaskScreen(task)
                            }
                            HorizontalGradientDivider(thickness = 1.dp)
                        }
                    }

                }

                PlusButton(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    val taskScreenData = TaskEntity(
                        null,
                        year = calendarState.year,
                        month = calendarState.month.value,
                        day = calendarState.selectedDay,
                        fromHour = null,
                        fromMinute = null,
                        toHour = null,
                        toMinute = null,
                        notificationInterval = 0,
                        notificationType = NotificationType.Alarm.name,
                        title = "Тест ${(1..100).random()}",
                        text = ""
                    )
                    navigateToTaskScreen(taskScreenData)
                }

            }

        }

    }

}