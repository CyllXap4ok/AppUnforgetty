package com.helpyapps.unforgetty.data.tasks_data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskToDB(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTaskFromDB(taskEntity: TaskEntity)

    @Query("SELECT * FROM tasks_data_base ORDER BY year ASC, month ASC, day ASC")
    suspend fun downloadTasks(): List<TaskEntity>

}