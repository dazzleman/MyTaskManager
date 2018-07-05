package ru.ic218.mytaskmanager.di.module

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.ic218.data.AppDatabase
import ru.ic218.domain.interactors.TaskInteractor
import ru.ic218.domain.interfaces.DbRepository
import ru.ic218.mytaskmanager.feature.edit.viewmodel.EditViewModel
import ru.ic218.mytaskmanager.feature.main.viewmodel.MainViewModel
import ru.ic218.repository.task.DbRepositoryImpl

val myModuleInteractor : Module = applicationContext {
    factory { TaskInteractor(get()) }
}

val myModuleRepository : Module = applicationContext {
    bean { DbRepositoryImpl(get()) as DbRepository }
}

val myModuleViewModel : Module = applicationContext {
    viewModel { MainViewModel(get()) }
    viewModel { EditViewModel(get()) }
}

val myModuleDatabase : Module = applicationContext {
    bean { AppDatabase.get(get()) }
}