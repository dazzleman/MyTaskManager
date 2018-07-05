package ru.ic218.mytaskmanager.feature.main.adapter

import android.content.ClipData
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_rv_item.view.cardDescription
import kotlinx.android.synthetic.main.view_rv_item.view.cardPriority
import kotlinx.android.synthetic.main.view_rv_item.view.cardTitle
import ru.ic218.domain.common.PriorityTask
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.mytaskmanager.R
import ru.ic218.mytaskmanager.feature.main.activity.INTENT_ID_TASK
import ru.ic218.mytaskmanager.feature.main.activity.INTENT_STATUS_TASK

class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: TaskEntity, clickListener: (TaskEntity) -> Unit) {
        itemView.cardTitle.text = item.title
        itemView.cardDescription.text = item.description
        when (item.priority) {
            PriorityTask.LOW -> itemView.cardPriority.setImageResource(R.color.taskColorLowPriority)
            PriorityTask.MEDIUM  -> itemView.cardPriority.setImageResource(R.color.taskColorMediumPriority)
            PriorityTask.HIGH  -> itemView.cardPriority.setImageResource(R.color.taskColorHighPriority)
        }

        itemView.visibility = View.VISIBLE

        itemView.setOnClickListener { clickListener.invoke(item) }
        itemView.setOnLongClickListener {
            val intent = Intent()
            intent.putExtra(INTENT_ID_TASK, item.id)
            intent.putExtra(INTENT_STATUS_TASK, TaskEntity.getStatusId(item.status))
            val clipData = ClipData.newIntent("id", intent)
            val dsb = View.DragShadowBuilder(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) it.startDragAndDrop(clipData, dsb, it, 0)
            else it.startDrag(clipData, dsb, it, 0)

            it.visibility = View.INVISIBLE
true
        }
    }
}