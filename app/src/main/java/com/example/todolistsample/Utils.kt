package com.example.todolistsample

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

object Utils {

    fun datePickerDialog(context: Context): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var resultString: String = ""

        val dpd = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                resultString = "$dayOfMonth.${monthOfYear + 1}.$year"
            },
            year,
            month,
            day
        )

        dpd.show()


        return resultString
    }
}