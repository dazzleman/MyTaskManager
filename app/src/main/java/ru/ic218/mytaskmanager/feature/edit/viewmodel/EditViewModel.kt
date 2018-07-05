package ru.ic218.mytaskmanager.feature.edit.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import ru.ic218.domain.entity.HistoryEntity
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.domain.interactors.TaskInteractor
import java.util.concurrent.Executors

class EditViewModel(private val taskInteractor: TaskInteractor) : ViewModel(), LifecycleObserver {

    val taskModelFromDb = MutableLiveData<TaskEntity>()
    val historyModelFromDb = MutableLiveData<List<HistoryEntity>>()

    private val executors = Executors.newSingleThreadExecutor()

    private var taskId: Long = -1
    fun setTaskId(id: Long) {
        taskId = id
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        if (taskId != -1L) executors.submit {
            val task = taskInteractor.getTasksById(taskId)
            val history = taskInteractor.getHistoryByTaskId(taskId)
            taskModelFromDb.postValue(task)
            historyModelFromDb.postValue(history)
        }
    }

    fun insertOrUpdateTask(model: TaskEntity) {
        executors.submit {
            var historyMessage = "Создана запись"
            if (taskModelFromDb.value != null) {
                model.apply {
                    id = taskModelFromDb.value?.id!!
                }
                historyMessage = TaskEntity.getChangesModelForHistory(taskModelFromDb.value!!, model)
                if (historyMessage.isEmpty()) return@submit
            }
            taskInteractor.insertOrUpdateTask(model, historyMessage)
        }
    }

    override fun onCleared() {
        executors.shutdownNow()
        super.onCleared()
    }
}