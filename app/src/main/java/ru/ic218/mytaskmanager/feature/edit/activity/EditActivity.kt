package ru.ic218.mytaskmanager.feature.edit.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar
import org.koin.android.architecture.ext.viewModel
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.mytaskmanager.R
import ru.ic218.mytaskmanager.feature.edit.viewmodel.EditViewModel
import java.lang.StringBuilder
import java.util.Date

class EditActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_TASK_ID = "task_id"

        fun startActivity(context: Activity, idTask: Long) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(INTENT_TASK_ID, idTask)
            context.startActivity(intent)
        }
    }

    private val editViewModel: EditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val taskID = intent.getLongExtra(INTENT_TASK_ID, -1)
        editViewModel.setTaskId(taskID)

        lifecycle.addObserver(editViewModel)

        initToolbar(taskID)
        initListener()
        subscribeViewModel()
    }

    private fun initToolbar(id: Long) {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (id == -1L) title = getString(R.string.activity_edit_new_task_title)
        else title = getString(R.string.activity_edit_edit_task_title)
    }

    private fun initListener() {
        btnCompleteTask.setOnClickListener {
            val model = TaskEntity(
                0,
                editTitle.text.toString(),
                editDescription.text.toString(),
                TaskEntity.getStatusById(spinStatus.selectedItemPosition),
                TaskEntity.getPriorityById(spinPriority.selectedItemPosition),
                Date().time
            )
            editViewModel.insertOrUpdateTask(model)
            finish()
        }
    }

    private fun subscribeViewModel() {
        editViewModel.taskModelFromDb.observe(this, Observer {
            it?.let {
                editTitle.setText(it.title)
                editDescription.setText(it.description)
                spinStatus.setSelection(TaskEntity.getStatusId(it.status))
                spinPriority.setSelection(TaskEntity.getPriorityId(it.priority))
            }
        })
        editViewModel.historyModelFromDb.observe(this, Observer {
            it?.let {
                val message = StringBuilder()
                it.map {
                    message.append(getString (R.string.activity_edit_history_message, Date(it.timeCreated), it.message))
                }
                textHistory.text = message
            }
        })
    }
}
