package com.miruna.hospitalmanager.application.pacient

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_pacient.*
import java.util.*

class AddPacientActivity : AppCompatActivity() {

    private fun getDatePickerListener() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacient)

        var calendar : Calendar
        var datePicker : DatePickerDialog

        btn_pick_dateIn.setOnClickListener {
            calendar = Calendar.getInstance()

            var day : Int = calendar.get(Calendar.DAY_OF_MONTH)
            var month : Int = calendar.get(Calendar.MONTH)
            var year : Int = calendar.get(Calendar.YEAR)
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            et_add_pacient_dateIn.setText(datePickerDialog.toString())

        }
        btn_pick_dateEx.setOnClickListener {
            calendar = Calendar.getInstance()

            var day : Int = calendar.get(Calendar.DAY_OF_MONTH)
            var month : Int = calendar.get(Calendar.MONTH)
            var year : Int = calendar.get(Calendar.YEAR)
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            et_add_pacient_dateEx.setText(datePickerDialog.toString())

        }

        btn_submit_pacient.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("PACIENT_ID", et_add_pacient_id.text.toString())
            bundle.putString("PACIENT_NAME", et_add_pacient_name.text.toString())
            bundle.putString("PACIENT_SURNAME", et_add_pacient_surname.text.toString())
            bundle.putString("PACIENT_AGE", et_add_pacient_age.text.toString())
            bundle.putString("PACIENT_CNP", et_add_pacient_cnp.text.toString())
            bundle.putString("PACIENT_DATE_IN", et_add_pacient_dateIn.text.toString())
            bundle.putString("PACIENT_DATE_EX", et_add_pacient_dateEx.text.toString())


            val dashboardIntent : Intent = Intent(this, DashboardActivity()::class.java)

            dashboardIntent.putExtra("BUNDLE_EXTRA_PACIENT", bundle)

            setResult(Constants.RESULT_CODE_ADD_PACIENT, dashboardIntent)

            finish()
        }
    }
}
