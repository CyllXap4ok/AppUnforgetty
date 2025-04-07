package com.helpyapps.unforgetty

import android.app.Application
import com.helpyapps.unforgetty.model.tasks_db.TasksDataBase

class App : Application() {
    val tasksDB by lazy {
        TasksDataBase.create(this)
    }
}