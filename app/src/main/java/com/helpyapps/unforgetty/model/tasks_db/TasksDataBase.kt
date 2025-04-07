package com.helpyapps.unforgetty.model.tasks_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TasksDataBase : RoomDatabase() {

    abstract val dao: TasksDao

    companion object {
        fun create(context: Context): TasksDataBase {
            return Room.databaseBuilder(
                context,
                TasksDataBase::class.java,
                "tasks.db"
            ).build()
        }
    }

}