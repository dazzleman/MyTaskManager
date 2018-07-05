package ru.ic218.mytaskmanager.feature.main.adapter

import android.support.v7.util.DiffUtil
import ru.ic218.domain.entity.TaskEntity

class CommonListDiffCallback : DiffUtil.ItemCallback<TaskEntity>(){

    override fun areItemsTheSame(oldItem: TaskEntity?, newItem: TaskEntity?): Boolean {
        return oldItem?.id == newItem?.id
            && oldItem?.title == newItem?.title
            && oldItem?.description == newItem?.description
            && oldItem?.status == newItem?.status
            && oldItem?.priority == newItem?.priority
    }

    override fun areContentsTheSame(oldItem: TaskEntity?, newItem: TaskEntity?): Boolean {
        return oldItem == newItem
    }
}