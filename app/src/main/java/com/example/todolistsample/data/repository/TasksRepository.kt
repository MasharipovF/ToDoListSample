package com.example.todolistsample.data.repository

import androidx.lifecycle.LiveData
import com.example.todolistsample.data.local.dao.TasksDao
import com.example.todolistsample.data.local.entity.Task

class TasksRepository(private val tasksDao: TasksDao) {
    val getAllTasks: LiveData<List<Task>> = tasksDao.getAllTasks()
    val getInProgressTasks: LiveData<List<Task>> = tasksDao.getInProgressTasks()
    val getDoneTasks: LiveData<List<Task>> = tasksDao.getDoneTasks()

    suspend fun addTask(task: Task) {
        tasksDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        tasksDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        tasksDao.deleteTask(task)
    }
}