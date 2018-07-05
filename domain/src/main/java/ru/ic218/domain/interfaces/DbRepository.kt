package ru.ic218.domain.interfaces

import android.arch.lifecycle.LiveData
import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.entity.HistoryEntity
import ru.ic218.domain.entity.TaskEntity

interface DbRepository {

    fun getTasksByStatus(newStatus: StatusTask): LiveData<List<TaskEntity>>
    fun getTasksById(id: Long): TaskEntity
    fun getHistoryByTaskId(id: Long): List<HistoryEntity>
    fun insertOrUpdateTask(model: TaskEntity, historyMessage: String)
    fun changeTaskStatus(idTask: Long, newStatus: StatusTask, oldStatus: StatusTask)
}