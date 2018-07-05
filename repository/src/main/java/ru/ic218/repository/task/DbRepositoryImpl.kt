package ru.ic218.repository.task

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import ru.ic218.data.AppDatabase
import ru.ic218.data.db.mappers.mapHistoryDataToEntity
import ru.ic218.data.db.mappers.mapTaskDataToEntity
import ru.ic218.data.db.mappers.mapTaskEntityToData
import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.entity.HistoryEntity
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.domain.interfaces.DbRepository

class DbRepositoryImpl(private val db: AppDatabase) : DbRepository {

    override fun getTasksByStatus(status: StatusTask): LiveData<List<TaskEntity>> {
        return Transformations.map(db.taskDao().getTasksByStatus(status), Function {
            it.map { mapTaskDataToEntity(it) }
        })
    }

    override fun getTasksById(id: Long): TaskEntity {
        val data = db.taskDao().getTasksById(id)
        return mapTaskDataToEntity(data)
    }

    override fun getHistoryByTaskId(id: Long): List<HistoryEntity> {
        val data = db.taskDao().getHistoryByTaskId(id)
        return if (data.isEmpty()) listOf(HistoryEntity()) else data.map { mapHistoryDataToEntity(it) }
    }

    override fun insertOrUpdateTask(model: TaskEntity, historyMessage: String) {
        val data = mapTaskEntityToData(model)
        db.taskDao().insertTaskWithHistory(data, historyMessage)
    }

    override fun changeTaskStatus(idTask: Long, newStatus: StatusTask, oldStatus: StatusTask) {
        db.taskDao().changeTaskStatusWithHistory(idTask, newStatus, oldStatus)
    }
}