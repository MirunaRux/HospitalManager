package com.miruna.hospitalmanager.application.pacient

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_pacient.*
import java.text.SimpleDateFormat
import java.util.*

class AddPacientActivity : AppCompatActivity() {

    private fun getDatePickerListener_et_dateIn() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.time?.let {
                et_add_pacient_dateIn.setText(it.formatToStringByPattern(Constants.DATE_FORMAT_MDY))
            }
        }

    private fun getDatePickerListener_et_dateEx() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.time?.let {
                et_add_pacient_dateEx.setText(it.formatToStringByPattern(Constants.DATE_FORMAT_MDY))
            }
        }

    fun Date.formatToStringByPattern(pattern: String): String {
        val df = SimpleDateFormat(pattern)
        return df.format(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacient)

        var calendar: Calendar

        et_add_pacient_dateIn.setOnClickListener {
            calendar = Calendar.getInstance()

            var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            var month: Int = calendar.get(Calendar.MONTH)
            var year: Int = calendar.get(Calendar.YEAR)
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener_et_dateIn(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        et_add_pacient_dateEx.setOnClickListener {
            calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener_et_dateEx(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        Log.i("gigel","e ok")
        btn_submit_pacient.setOnClickListener {
            Log.i("gigel", "clicked")
            if (isInputValid()) {
                Log.i("gigel", "input valid")
                val bundle = Bundle()
                bundle.putString("PACIENT_ID", et_add_pacient_id.text.toString())
                bundle.putString("PACIENT_NAME", et_add_pacient_name.text.toString())
                bundle.putString("PACIENT_SURNAME", et_add_pacient_surname.text.toString())
                bundle.putString("PACIENT_BIRTHDAY", et_add_pacient_birthday.text.toString())
                bundle.putString("PACIENT_CNP", et_add_pacient_cnp.text.toString())
                bundle.putString("PACIENT_DATE_IN", et_add_pacient_dateIn.text.toString())
                bundle.putString("PACIENT_DATE_EX", et_add_pacient_dateEx.text.toString())


                val dashboardIntent: Intent = Intent(this, DashboardActivity()::class.java)

                dashboardIntent.putExtra("BUNDLE_EXTRA_PACIENT", bundle)

                setResult(Constants.RESULT_CODE_ADD_PACIENT, dashboardIntent)

                finish()
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_add_pacient_id.text.isNullOrEmpty()) {
            til_add_pacient_id.setError("Field required")
            et_add_pacient_id.requestFocus()
            Log.i("gigel","1")
            return false
        }
        if (et_add_pacient_name.text.isNullOrEmpty()) {
            til_add_pacient_name.setError("Field required")
            et_add_pacient_name.requestFocus()
            Log.i("gigel","2")
            return false
        }
        if (et_add_pacient_surname.text.isNullOrEmpty()) {
            til_add_pacient_surname.setError("Field required")
            et_add_pacient_surname.requestFocus()
            Log.i("gigel","3")
            return false
        }
        if (et_add_pacient_birthday.text.isNullOrEmpty()) {
            til_add_pacient_birthday.setError("Field required")
            et_add_pacient_birthday.requestFocus()
            Log.i("gigel","4")
            return false
        }
        if (et_add_pacient_cnp.text.isNullOrEmpty()) {
            til_add_pacient_cnp.setError("Field required")
            et_add_pacient_cnp.requestFocus()
            Log.i("gigel","5")
            return false
        }
        if (et_add_pacient_dateIn.text.isNullOrEmpty()) {
            til_add_pacient_dateIn.setError("Field required")
            et_add_pacient_dateIn.requestFocus()
            Log.i("gigel","6")
            return false
        }
        if (et_add_pacient_dateEx.text.isNullOrEmpty()) {
            til_add_pacient_dateEx.setError("Field required")
            et_add_pacient_dateEx.requestFocus()
            Log.i("gigel","7")
            return false
        }

        return true
    }
}
