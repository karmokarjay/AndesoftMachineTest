package com.demo.andesoftmachinetest.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.widget.EditText
import android.widget.Toast
import com.demo.andesoftmachinetest.R
import java.util.*

/**
 * This class shows the demo of Kotlin Extension functions.
 */

fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun EditText.onDatePickerClick(context: Context) {
    val calendar = Calendar.getInstance()
    val calenderYear = calendar.get(Calendar.YEAR)
    val calenderMonth = calendar.get(Calendar.MONTH)
    val calenderDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(
            context,
            R.style.DatePickerDialog,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var monthInDateFormat: String
                var dayInDateFormat: String
                val mMonth = month + 1

                monthInDateFormat = mMonth.toString()
                if (mMonth < 10) {
                    monthInDateFormat = "0$mMonth"
                }

                dayInDateFormat = dayOfMonth.toString()
                if (dayOfMonth < 10) {
                    dayInDateFormat = "0$dayOfMonth"
                }
                this.setText("$dayInDateFormat/$monthInDateFormat/$year")
            },
            calenderYear,
            calenderMonth,
            calenderDay
        )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
    datePickerDialog.show()
    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
}

fun Context.openSettingsForAcceptingPermissions() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri =
        Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    this.startActivity(intent)
}