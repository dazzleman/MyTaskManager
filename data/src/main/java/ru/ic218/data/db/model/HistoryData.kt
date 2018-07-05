package ru.ic218.data.db.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "history")
data class HistoryData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val idTask: Long,
    val message: String,
    val timeCreated: Long
)