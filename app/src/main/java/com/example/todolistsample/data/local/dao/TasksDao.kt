package com.example.todolistsample.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolistsample.data.local.entity.Task

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE status=\"inprogress\" ORDER BY id ASC ")
    fun getInProgressTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE status=\"done\" ORDER BY id ASC ")
    fun getDoneTasks(): LiveData<List<Task>>
}