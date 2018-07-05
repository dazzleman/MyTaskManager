package ru.ic218.data.db.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.common.PriorityTask

@Entity(tableName = "tasks")
data class TaskData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String,
    val status: StatusTask,
    val priority: PriorityTask,
    val timeCreated: Long
)