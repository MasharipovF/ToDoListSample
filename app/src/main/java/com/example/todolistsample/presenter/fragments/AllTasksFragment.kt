package com.example.todolistsample.presenter.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistsample.R
import com.example.todolistsample.domain.viewmodels.TaskViewModel
import com.example.todolistsample.presenter.adapters.TasksAdapter
import com.example.todolistsample.databinding.FragmentAllTasksBinding
import com.example.todolistsample.data.local.entity.Task

class AllTasksFragment : Fragment() {
    private lateinit var binding: FragmentAllTasksBinding
    lateinit var adapter: TasksAdapter
    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAllTasksBinding.bind(view)

        binding.rvAllTasks.layoutManager = LinearLayoutManager(requireContext())
        adapter = TasksAdapter(object : TasksAdapter.OnTasksAdapterItemClickListener {
            override fun onUpdateClick(task: Task) {
                mTaskViewModel.updateTask(task)
                Toast.makeText(requireContext(), "Updated succesfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onDeleteClick(task: Task) {
                deleteUser(task)
            }
        }, requireContext())
        binding.rvAllTasks.adapter = adapter

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        mTaskViewModel.getAllTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.list = task as MutableList<Task>
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun deleteUser(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(task)
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        builder.setTitle("Delete this task?")
        builder.setMessage("Are you sure to delete this task?")
        builder.create().show()

    }
}