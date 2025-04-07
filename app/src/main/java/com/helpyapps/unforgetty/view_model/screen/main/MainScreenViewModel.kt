package com.helpyapps.unforgetty.view_model.screen.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.util.fastFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.helpyapps.unforgetty.App
import com.helpyapps.unforgetty.model.tasks_db.TaskEntity
import com.helpyapps.unforgetty.model.tasks_db.TasksDataBase
import com.helpyapps.unforgetty.view.common.calendar.model.CalendarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class MainScreenViewModel(
    private val dataBase: TasksDataBase,
    savedState: SavedStateHandle
) : ViewModel() {

    val calendarState by savedState.saveable(saver = CalendarState.Saver) { CalendarState() }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    val tasks = mutableStateListOf<TaskEntity>()
    val monthTasks = mutableStateListOf<TaskEntity>()
    val dayTasks: SnapshotStateList<TaskEntity> by savedState.saveable(
        saver = TaskListSaver
    ) {
        mutableStateListOf()
    }

    init { downloadTasks() }

    private fun downloadTasks() = viewModelScope.launch {
        _isLoading.postValue(true)
        tasks.addAll(dataBase.dao.downloadTasks())
        getMonthTasks(calendarState.year, calendarState.month.value)
        getDayTasks(calendarState.selectedDay)
        delay(800)
        _isLoading.postValue(false)
    }

    private fun insertTaskToDB(task: TaskEntity) = viewModelScope.launch {
        dataBase.dao.addTaskToDB(task)
    }

    private fun deleteTaskFromDB(task: TaskEntity) = viewModelScope.launch {
        dataBase.dao.deleteTaskFromDB(task)
    }

    fun addTask(task: TaskEntity) {
        tasks.add(task)
        monthTasks.add(task)
        dayTasks.add(task)
        calendarState.addDayWithTask(task.day)
        insertTaskToDB(task)
    }

    fun deleteTask(task: TaskEntity) {
        tasks.remove(task)
        monthTasks.remove(task)
        dayTasks.remove(task)
        deleteTaskFromDB(task)
    }

    fun getMonthTasks(year: Int, month: Int) {
        monthTasks.clear()
        monthTasks.addAll(tasks.fastFilter { it.year == year && it.month == month })
        calendarState.updateDaysWithTasks(monthTasks.map { it.day }.distinct())
    }

    fun getDayTasks(day: Int) {
        dayTasks.clear()
        dayTasks.addAll(monthTasks.fastFilter { it.day == day })
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]
                val dataBase = (checkNotNull(application) as App).tasksDB
                val savedStateHandle = createSavedStateHandle()
                MainScreenViewModel(dataBase, savedStateHandle)
            }
        }
    }

}

private object TaskListSaver : Saver<SnapshotStateList<TaskEntity>, List<Map<String, Any?>>> {

    override fun SaverScope.save(value: SnapshotStateList<TaskEntity>): List<Map<String, Any?>> {
        return value.map { task ->
            TaskEntityParameters.run {
                mapOf(
                    ID to task.id!!,
                    YEAR to task.year,
                    MONTH to task.month,
                    DAY to task.day,
                    FROM_HOUR to task.fromHour,
                    FROM_MINUTE to task.fromMinute,
                    TO_HOUR to task.toHour,
                    TO_MINUTE to task.toMinute,
                    NOTIFICATION_INTERVAL to task.notificationInterval,
                    NOTIFICATION_TYPE to task.notificationType,
                    TITLE to task.title,
                    TEXT to task.text
                )
            }
        }
    }

    override fun restore(value: List<Map<String, Any?>>): SnapshotStateList<TaskEntity> {
        return value.map { dict ->
            TaskEntityParameters.run {
                TaskEntity(
                    dict[ID] as Int,
                    dict[YEAR] as Int,
                    dict[MONTH] as Int,
                    dict[DAY] as Int,
                    dict[FROM_HOUR] as Int?,
                    dict[FROM_MINUTE] as Int?,
                    dict[TO_HOUR] as Int?,
                    dict[TO_MINUTE] as Int?,
                    dict[NOTIFICATION_INTERVAL] as Int,
                    dict[NOTIFICATION_TYPE] as String,
                    dict[TITLE] as String,
                    dict[TEXT] as String
                )
            }
        }.toMutableStateList()
    }

}

private object TaskEntityParameters {
    const val ID = "id"
    const val YEAR = "year"
    const val MONTH = "month"
    const val DAY = "day"
    const val FROM_HOUR = "fromHour"
    const val FROM_MINUTE = "fromMinute"
    const val TO_HOUR = "toHour"
    const val TO_MINUTE = "toMinute"
    const val NOTIFICATION_INTERVAL = "notificationInterval"
    const val NOTIFICATION_TYPE = "notificationType"
    const val TITLE = "title"
    const val TEXT = "text"
}