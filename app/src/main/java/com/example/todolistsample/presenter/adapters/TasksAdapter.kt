package com.example.todolistsample.presenter.adapters

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistsample.R
import com.example.todolistsample.domain.viewmodels.TaskViewModel
import com.example.todolistsample.Utils
import com.example.todolistsample.databinding.ItemTasksBinding
import com.example.todolistsample.data.local.entity.Task
import java.util.*

class TasksAdapter(val listener: OnTasksAdapterItemClickListener, private val mContext: Context) :
    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    private lateinit var mViewModel: TaskViewModel

    var list: MutableList<Task> = arrayListOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tasks, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        with(holder) {
            mBinding.tvTitle.setText(list[position].title)
            mBinding.tvDate.text = list[position].date
            mBinding.btnDelete.isEnabled = false
            mBinding.btnStatus.isEnabled = false
            if (list[position].status == "done") {
                mBinding.btnStatus.setImageResource(R.drawable.ic_baseline_done)
            } else mBinding.btnStatus.setImageResource(R.drawable.ic_baseline_inprogress)
        }
    }

    override fun getItemCount(): Int = list.size


    inner class TasksViewHolder(itemview: View) :
        RecyclerView.ViewHolder(itemview) {
        val mBinding = ItemTasksBinding.bind(itemView)

        init {
            mBinding.btnEdit.setOnClickListener {
                if (mBinding.tvTitle.isEnabled) {
                    mBinding.tvTitle.isEnabled = false
                    mBinding.tvDate.isEnabled = false
                    mBinding.btnStatus.isEnabled = false
                    mBinding.btnDelete.isEnabled = false

                    val id = list[adapterPosition].id
                    val title = mBinding.tvTitle.text.toString()
                    val date = mBinding.tvDate.text.toString()
                    val status: String = mBinding.btnStatus.tag.toString()

                    listener.onUpdateClick(Task(id, title, date, status))

                } else {
                    mBinding.tvTitle.isEnabled = true
                    mBinding.tvDate.isEnabled = true
                    mBinding.btnStatus.isEnabled = true
                    mBinding.btnDelete.isEnabled = true

                }
            }

            mBinding.tvDate.setOnClickListener {
                mBinding.tvDate.text = Utils.datePickerDialog(mContext)
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(
                    mContext,
                    { _, year, monthOfYear, dayOfMonth ->
                        mBinding.tvDate.text = "$dayOfMonth.${monthOfYear + 1}.$year"
                    },
                    year,
                    month,
                    day
                )
                dpd.show()
            }

            mBinding.btnStatus.setOnClickListener {
                if (mBinding.btnStatus.tag == mContext.getString(R.string.progress)) {
                    mBinding.btnStatus.setImageResource(R.drawable.ic_baseline_done)
                    mBinding.btnStatus.tag = mContext.getString(R.string.done)
                } else {
                    mBinding.btnStatus.setImageResource(R.drawable.ic_baseline_inprogress)
                    mBinding.btnStatus.tag = mContext.getString(R.string.progress)
                }
            }

            mBinding.btnDelete.setOnClickListener {
                listener.onDeleteClick(list[adapterPosition])

                mBinding.tvTitle.isEnabled = false
                mBinding.tvDate.isEnabled = false
                mBinding.btnStatus.isEnabled = false
                mBinding.btnDelete.isEnabled = false
            }
        }

    }

    interface OnTasksAdapterItemClickListener {
        fun onUpdateClick(task: Task)
        fun onDeleteClick(task: Task)
    }
}