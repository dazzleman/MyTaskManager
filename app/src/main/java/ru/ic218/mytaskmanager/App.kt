package ru.ic218.mytaskmanager

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.ic218.mytaskmanager.di.module.myModuleDatabase
import ru.ic218.mytaskmanager.di.module.myModuleInteractor
import ru.ic218.mytaskmanager.di.module.myModuleRepository
import ru.ic218.mytaskmanager.di.module.myModuleViewModel

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(myModuleInteractor, myModuleViewModel, myModuleDatabase, myModuleRepository))
    }
}