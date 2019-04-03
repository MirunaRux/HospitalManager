package com.miruna.hospitalmanager.application.agenda

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_event.*

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        btn_submit_event.setOnClickListener {
            if(isInputValid()) {
                val bundle = Bundle()
                bundle.putString("EVENT_ID", et_add_event_id.text.toString())
                bundle.putString("EVENT_NAME", et_add_event_name.text.toString())
                bundle.putString("EVENT_LOCATION", et_add_event_location.text.toString())
                bundle.putString("EVENT_PACIENT", et_add_event_pacient.text.toString())
                bundle.putString("EVENT_DOCTOR", et_add_event_doctor.text.toString())

                val dashboardIntent: Intent = Intent(this, DashboardActivity()::class.java)

                dashboardIntent.putExtra("BUNDLE_EXTRA_EVENT", bundle)

                setResult(Constants.RESULT_CODE_ADD_EVENT, dashboardIntent)

                finish()
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_add_event_id.text.isNullOrEmpty()) {
            til_add_event_id.setError("Field required")
            et_add_event_id.requestFocus()
            return false
        }
        if (et_add_event_name.text.isNullOrEmpty()) {
            til_add_event_name.setError("Field required")
            et_add_event_name.requestFocus()
            return false
        }
        if (et_add_event_location.text.isNullOrEmpty()) {
            til_add_event_location.setError("Field required")
            et_add_event_location.requestFocus()
            return false
        }
        if (et_add_event_pacient.text.isNullOrEmpty()) {
            til_add_event_pacient.setError("Field required")
            et_add_event_pacient.requestFocus()
            return false
        }
        if (et_add_event_doctor.text.isNullOrEmpty()) {
            til_add_event_doctor.setError("Field required")
            et_add_event_doctor.requestFocus()
            return false
        }

        return true
    }
}
