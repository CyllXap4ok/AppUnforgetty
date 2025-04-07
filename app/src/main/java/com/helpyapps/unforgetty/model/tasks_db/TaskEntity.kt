package com.helpyapps.unforgetty.model.tasks_db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tasks_data_base")
data class TaskEntity(
    @PrimaryKey(true)
    val id: Int? = null,
    val year: Int,
    val month: Int,
    val day: Int,
    val fromHour: Int?,
    val fromMinute: Int?,
    val toHour: Int?,
    val toMinute: Int?,
    val notificationInterval: Int,
    val notificationType: String,
    val title: String,
    val text: String
)