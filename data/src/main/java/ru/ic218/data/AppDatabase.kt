package ru.ic218.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import ru.ic218.data.db.converter.PriorityTaskTypeConverter
import ru.ic218.data.db.converter.StatusTaskTypeConverter
import ru.ic218.data.db.dao.TaskDao
import ru.ic218.data.db.model.HistoryData
import ru.ic218.data.db.model.TaskData

@Database(entities = [
    TaskData::class,
    HistoryData::class],
    version = 1, exportSchema = false)
@TypeConverters(StatusTaskTypeConverter::class, PriorityTaskTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "app.db")
                    .build()
            }
            return instance!!
        }
    }

    abstract fun taskDao(): TaskDao
}