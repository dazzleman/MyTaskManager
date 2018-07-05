package ru.ic218.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import ru.ic218.data.db.model.HistoryData
import ru.ic218.data.db.model.TaskData
import ru.ic218.domain.common.StatusTask
import java.util.Date

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTask(model: TaskData): Long

    @Insert()
    fun insertHistory(model: HistoryData)

    @Query("select * from tasks where status = :status")
    fun getTasksByStatus(status: StatusTask): LiveData<List<TaskData>>

    @Query("select * from tasks where id = :id")
    fun getTasksById(id: Long): TaskData

    @Query("select * from history where idTask = :id")
    fun getHistoryByTaskId(id: Long): List<HistoryData>

    @Query("update tasks set status = :status where id = :idTask")
    fun changeTaskStatus(idTask: Long, status: StatusTask)

    @Transaction
    fun changeTaskStatusWithHistory(idTask: Long, newStatus: StatusTask, oldStatus: StatusTask) {
        changeTaskStatus(idTask, newStatus)
        insertHistory(HistoryData(
            0,
            idTask,
            "Изменен статус с ${oldStatus.name} на ${newStatus.name}",
            Date().time
        ))
    }

    @Transaction
    fun insertTaskWithHistory(model: TaskData, historyMessage: String) {
        val id = insertOrUpdateTask(model)
        insertHistory(HistoryData(
            0,
            id,
            historyMessage,
            Date().time
        ))
    }
}