package ru.ic218.data.db.converter

import android.arch.persistence.room.TypeConverter
import ru.ic218.domain.common.PriorityTask

class PriorityTaskTypeConverter {

    @TypeConverter
    fun fromPriorityTaskType(type: PriorityTask): Int {
        return when (type) {
            PriorityTask.LOW -> 0
            PriorityTask.MEDIUM -> 1
            PriorityTask.HIGH -> 2
        }
    }

    @TypeConverter
    fun toPriorityTaskType(item: Int): PriorityTask {
        return when (item) {
            0 -> PriorityTask.LOW
            1 -> PriorityTask.MEDIUM
            else -> PriorityTask.HIGH
        }
    }
}