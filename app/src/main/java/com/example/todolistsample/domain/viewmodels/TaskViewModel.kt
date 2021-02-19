package com.example.todolistsample.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistsample.data.local.TasksDatabase
import com.example.todolistsample.data.repository.TasksRepository
import com.example.todolistsample.data.local.entity.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val getAllTasks: LiveData<List<Task>>
    val getInProgressTasks: LiveData<List<Task>>
    val getDoneTasks: LiveData<List<Task>>
    private val repository: TasksRepository

    init {
        val taskDao = TasksDatabase.getDatabase(application).taskDao()
        repository = TasksRepository(taskDao)
        getAllTasks = repository.getAllTasks
        getInProgressTasks = repository.getInProgressTasks
        getDoneTasks = repository.getDoneTasks
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

}