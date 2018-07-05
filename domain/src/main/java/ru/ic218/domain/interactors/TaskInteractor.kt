package ru.ic218.domain.interactors

import android.arch.lifecycle.LiveData
import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.entity.HistoryEntity
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.domain.interfaces.DbRepository

class TaskInteractor (private val dbRepository: DbRepository) {

    fun getTasksByStatus(status: StatusTask): LiveData<List<TaskEntity>>{
        return dbRepository.getTasksByStatus(status)
    }

    fun getTasksById(id: Long): TaskEntity {
        return dbRepository.getTasksById(id)
    }

    fun getHistoryByTaskId(id: Long): List<HistoryEntity> {
        return dbRepository.getHistoryByTaskId(id)
    }

    fun insertOrUpdateTask(model: TaskEntity, historyMessage: String){
        dbRepository.insertOrUpdateTask(model, historyMessage)
    }

    fun changeTaskStatus(idTask: Long, newStatus: StatusTask, oldStatus: StatusTask){
        dbRepository.changeTaskStatus(idTask, newStatus, oldStatus)
    }
}