package com.helpyapps.unforgetty

import android.app.Application
import android.util.Log
import com.helpyapps.unforgetty.model.tasks_db.TasksDataBase

class App : Application() {
    val tasksDB by lazy {
        TasksDataBase.create(this)
    }
}

fun printLog(context: Any, text: String) {
    Log.d(context.javaClass.simpleName, text)
}