package ru.ic218.mytaskmanager.feature.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.domain.interactors.TaskInteractor
import java.util.concurrent.Executors

class MainViewModel(private val taskInteractor: TaskInteractor) : ViewModel() {

    private val executors = Executors.newSingleThreadExecutor()

    fun getTasksByStatus(newStatus: StatusTask): LiveData<List<TaskEntity>> {
        return taskInteractor.getTasksByStatus(newStatus)
    }

    fun changeTaskStatus(idTask: Long, newStatus: StatusTask, oldStatus: StatusTask){
        executors.submit {
            taskInteractor.changeTaskStatus(idTask, newStatus, oldStatus)
        }
    }

    override fun onCleared() {
        executors.shutdownNow()
        super.onCleared()
    }
}