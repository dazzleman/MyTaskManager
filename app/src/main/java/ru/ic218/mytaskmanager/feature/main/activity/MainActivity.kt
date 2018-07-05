package ru.ic218.mytaskmanager.feature.main.activity

import android.arch.lifecycle.Observer
import android.content.ClipDescription
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.DragEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.btnCreateTask
import kotlinx.android.synthetic.main.activity_main.rvDone
import kotlinx.android.synthetic.main.activity_main.rvProgress
import kotlinx.android.synthetic.main.activity_main.rvWait
import kotlinx.android.synthetic.main.toolbar_layout.toolbar
import org.koin.android.architecture.ext.viewModel
import ru.ic218.domain.common.StatusTask
import ru.ic218.domain.entity.TaskEntity
import ru.ic218.mytaskmanager.R
import ru.ic218.mytaskmanager.feature.edit.activity.EditActivity
import ru.ic218.mytaskmanager.feature.main.adapter.CommonListAdapter
import ru.ic218.mytaskmanager.feature.main.viewmodel.MainViewModel

const val INTENT_ID_TASK = "INTENT_ID_TASK"
const val INTENT_STATUS_TASK = "INTENT_STATUS_TASK"

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initListener()
        initRecycleView()
        subscribeViewModel()
    }

    private fun initListener() {
        btnCreateTask.setOnClickListener {
            EditActivity.startActivity(this, -1)
        }
    }

    private var rvWaitAdapter: CommonListAdapter = CommonListAdapter(onClickListener = { EditActivity.startActivity(this, it.id) })

    private var rvProgressAdapter: CommonListAdapter = CommonListAdapter(onClickListener = { EditActivity.startActivity(this, it.id) })

    private var rvDoneAdapter: CommonListAdapter = CommonListAdapter(onClickListener = { EditActivity.startActivity(this, it.id) })

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
    }

    private fun initRecycleView() {
        rvWait.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rvWaitAdapter
            setOnDragListener(MyDragListener(StatusTask.WAIT))
        }
        rvProgress.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rvProgressAdapter
            setOnDragListener(MyDragListener(StatusTask.PROGRESS))
        }
        rvDone.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rvDoneAdapter
            setOnDragListener(MyDragListener(StatusTask.DONE))
        }
    }

    private fun subscribeViewModel() {
        mainViewModel.getTasksByStatus(StatusTask.WAIT).observe(this, Observer {
            it?.let {
                rvWaitAdapter.submitList(it)
            }
        })
        mainViewModel.getTasksByStatus(StatusTask.PROGRESS).observe(this, Observer {
            it?.let {
                rvProgressAdapter.submitList(it)
            }
        })
        mainViewModel.getTasksByStatus(StatusTask.DONE).observe(this, Observer {
            it?.let {
                rvDoneAdapter.submitList(it)
            }
        })
    }

    inner class MyDragListener(private val newStatus: StatusTask) : View.OnDragListener {

        override fun onDrag(v: View?, dragEvent: DragEvent?): Boolean {
            val dragAction = dragEvent?.action
            val dragView = dragEvent?.localState as View
            when (dragAction) {
                DragEvent.ACTION_DRAG_ENDED -> println("ACTION_DRAG_ENDED")
                DragEvent.ACTION_DRAG_ENTERED -> println("ACTION_DRAG_ENTERED")
                DragEvent.ACTION_DRAG_EXITED -> println("ACTION_DRAG_EXITED")
                DragEvent.ACTION_DRAG_LOCATION -> println("ACTION_DRAG_LOCATION")
                DragEvent.ACTION_DRAG_STARTED -> println("ACTION_DRAG_STARTED")
                DragEvent.ACTION_DROP -> {
                    println("ACTION_DROP")
                    val result = dragEvent.result
                    if (result) {
                        dragView.visibility = View.VISIBLE
                    } else {
                        if (dragEvent.clipDescription != null && dragEvent.clipData != null) {
                            if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_INTENT)) {
                                val idTask = dragEvent.clipData?.getItemAt(0)?.intent?.getLongExtra(INTENT_ID_TASK, -1)
                                val oldStatus = dragEvent.clipData?.getItemAt(0)?.intent?.getIntExtra(INTENT_STATUS_TASK, 0)
                                println("Clip data count ${dragEvent.clipData.itemCount}")
                                println("idTask $idTask")
                                idTask?.let { mainViewModel.changeTaskStatus(it, newStatus, TaskEntity.getStatusById(oldStatus!!)) }
                            }
                        }
                    }
                }
            }
            return true
        }
    }
}
