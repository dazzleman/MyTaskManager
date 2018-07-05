package ru.ic218.data.db.converter

import android.arch.persistence.room.TypeConverter
import ru.ic218.domain.common.StatusTask

class StatusTaskTypeConverter {

    @TypeConverter
    fun fromStatusTaskType(type: StatusTask): Int {
        return when (type) {
            StatusTask.WAIT -> 0
            StatusTask.PROGRESS -> 1
            StatusTask.DONE -> 2
            StatusTask.CLOSED -> 3
        }
    }

    @TypeConverter
    fun toStatusTaskType(item: Int): StatusTask {
        return when (item) {
            0 -> StatusTask.WAIT
            1 -> StatusTask.PROGRESS
            2 -> StatusTask.DONE
            else -> StatusTask.CLOSED
        }
    }
}