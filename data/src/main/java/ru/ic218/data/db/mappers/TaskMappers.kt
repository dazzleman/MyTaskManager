package ru.ic218.data.db.mappers

import ru.ic218.data.db.model.TaskData
import ru.ic218.domain.entity.TaskEntity

fun mapTaskDataToEntity(data: TaskData): TaskEntity {
    with(data) {
        return TaskEntity(
            id,
            title,
            description,
            status,
            priority,
            timeCreated
        )
    }
}

fun mapTaskEntityToData(data: TaskEntity): TaskData {
    with(data) {
        return TaskData(
            id,
            title,
            description,
            status,
            priority,
            timeCreated
        )
    }
}