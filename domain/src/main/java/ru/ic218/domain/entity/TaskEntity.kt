package ru.ic218.domain.entity

import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.common.PriorityTask
import java.lang.StringBuilder

data class TaskEntity(
    var id: Long,
    val title: String,
    val description: String,
    val status: StatusTask,
    val priority: PriorityTask,
    val timeCreated: Long
) {

    companion object {
        fun getChangesModelForHistory(old: TaskEntity, new: TaskEntity): String {
            val message = StringBuilder()
            if (old.title != new.title) message.append("Изменено название задачи с ${old.title} на ${new.title}; ")
            if (old.description != new.description) message.append("Изменено описание задачи с ${old.description} на ${new.description}; ")
            if (old.status != new.status) message.append("Изменен статус задачи с ${old.status.name} на ${new.status.name}; ")
            if (old.priority != new.priority) message.append("Изменен приоритет задачи с ${old.priority.name} на ${new.priority.name}; ")
            return message.toString()
        }

        fun getStatusId(status: StatusTask): Int {
            return when (status) {
                StatusTask.WAIT -> 0
                StatusTask.PROGRESS -> 1
                StatusTask.DONE -> 2
                StatusTask.CLOSED -> 3
            }
        }

        fun getStatusById(id: Int): StatusTask {
            return when (id) {
                0 -> StatusTask.WAIT
                1 -> StatusTask.PROGRESS
                2 -> StatusTask.DONE
                else -> StatusTask.CLOSED
            }
        }

        fun getPriorityId(priority: PriorityTask): Int {
            return when (priority) {
                PriorityTask.LOW -> 0
                PriorityTask.MEDIUM -> 1
                PriorityTask.HIGH -> 2
            }
        }

        fun getPriorityById(id: Int): PriorityTask {
            return when (id) {
                0 -> PriorityTask.LOW
                1 -> PriorityTask.MEDIUM
                else -> PriorityTask.HIGH
            }
        }
    }
}