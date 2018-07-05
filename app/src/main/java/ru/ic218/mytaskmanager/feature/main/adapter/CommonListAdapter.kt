package ru.ic218.mytaskmanager.feature.main.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.mytaskmanager.R

class CommonListAdapter(private val onClickListener: (TaskEntity) -> Unit)
    : ListAdapter<TaskEntity, CommonViewHolder>(CommonListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return CommonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}