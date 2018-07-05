package ru.ic218.domain.entity


data class HistoryEntity(
    val id: Long = 0,
    val idTask: Long = 0,
    val message: String = "Нет истории",
    val timeCreated: Long = 0
)