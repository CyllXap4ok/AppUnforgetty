package com.helpyapps.unforgetty.view_model.screen.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.util.fastFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.helpyapps.unforgetty.App
import com.helpyapps.unforgetty.model.tasks_db.TaskEntity
import com.helpyapps.unforgetty.model.tasks_db.TasksDataBase
import com.helpyapps.unforgetty.view.common.calendar.CalendarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val dataBase: TasksDataBase
) : ViewModel() {

    val calendarState = MutableLiveData(CalendarState())

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _tasks = mutableStateListOf<TaskEntity>()
    val tasks = _tasks

    private val _monthTasks = mutableStateListOf<TaskEntity>()
    val monthTasks = _monthTasks

    private val _dayTasks = mutableStateListOf<TaskEntity>()
    val dayTasks = this._dayTasks

    init { downloadTasks() }

    private fun downloadTasks() = viewModelScope.launch {
        _tasks.addAll(dataBase.dao.downloadTasks())
        getMonthTasks(calendarState.value!!.year, calendarState.value!!.month.value)
        getDayTasks(calendarState.value!!.selectedDay)
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
        _tasks.add(task)
        _monthTasks.add(task)
        this._dayTasks.add(task)
        calendarState.value!!.addDayWithTask(task.day)
        insertTaskToDB(task)
    }

    fun deleteTask(task: TaskEntity) {
        _tasks.remove(task)
        _monthTasks.remove(task)
        this._dayTasks.remove(task)
        deleteTaskFromDB(task)
    }

    fun getMonthTasks(year: Int, month: Int) {
        _monthTasks.clear()
        _monthTasks.addAll(_tasks.fastFilter { it.year == year && it.month == month })
        calendarState.value!!.updateDaysWithTasks(_monthTasks.map { it.day }.distinct())
    }

    fun getDayTasks(day: Int) {
        this._dayTasks.clear()
        this._dayTasks.addAll(_monthTasks.fastFilter { it.day == day })
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]
                val dataBase = (checkNotNull(application) as App).tasksDB
                val savedStateHandle = createSavedStateHandle()
                MainScreenViewModel(dataBase)
            }
        }
    }

}