package com.example.todolistsample.presenter.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todolistsample.R
import com.example.todolistsample.domain.viewmodels.TaskViewModel
import com.example.todolistsample.databinding.ActivityMainBinding
import com.example.todolistsample.data.local.entity.Task
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mTaskViewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.allTasksFragment,
                R.id.finishedTasksFragment,
                R.id.inProgressTasksFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


    }

    fun insertDataToDb(title: String, date: String): Boolean {
        val status = getString(R.string.progress)
        return if (inputCheck(title, date)) {
            val task = Task(0, title, date, status)
            mTaskViewModel.addTask(task)
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(this, "Fields not filled", Toast.LENGTH_SHORT).show()
            false
        }

    }

    private fun inputCheck(title: String, date: String): Boolean {
        return !(TextUtils.isEmpty(date) || TextUtils.isEmpty(title))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_btn -> {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_new_task)

                val btnAdd = dialog.findViewById<Button>(R.id.btnAdd)
                val tvDate = dialog.findViewById<TextView>(R.id.tvDate)
                val tvTitle = dialog.findViewById<EditText>(R.id.tvTitle)

                btnAdd.setOnClickListener {
                    val flag = insertDataToDb(
                        tvTitle.text.toString(),
                        tvDate.text.toString()
                    )
                    if (flag) dialog.dismiss()
                }

                tvDate.setOnClickListener {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)

                    val dpd = DatePickerDialog(
                        this,
                        { _, year, monthOfYear, dayOfMonth ->
                            tvDate.text = "$dayOfMonth.${monthOfYear + 1}.$year"
                        },
                        year,
                        month,
                        day
                    )
                    dpd.show()
                }
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}