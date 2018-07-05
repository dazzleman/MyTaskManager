package ru.ic218.data.db.mappers

import ru.ic218.data.db.model.HistoryData
import ru.ic218.domain.entity.HistoryEntity

fun mapHistoryDataToEntity(data: HistoryData): HistoryEntity {
    with(data) {
        return HistoryEntity(
            id,
            idTask,
            message,
            timeCreated
        )
    }
}