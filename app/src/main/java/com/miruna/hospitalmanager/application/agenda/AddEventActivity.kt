package com.miruna.hospitalmanager.application.agenda

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_event.*
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity : AppCompatActivity() {
    var eventList: MutableList<Event>? = null
    lateinit var newEvent: Event

    private fun getDatePickerListener_et_start_date() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.time?.let {
                et_add_event_start_date.setText(it.formatToStringByPattern(Constants.DATE_FORMAT_MDY))
            }
        }

    private fun getAMPM(hour:Int):String{
        return if(hour>11)" PM" else " AM"
    }

    private fun getHourAMPM(hour:Int):Int{
        // Return the hour value for AM PM time format
        var modifiedHour = if (hour>11)hour-12 else hour
        if (modifiedHour == 0){modifiedHour = 12}
        return modifiedHour
    }

    private fun getTimePickerListener_et_start_time() =
        TimePickerDialog.OnTimeSetListener { view,hourOfDay,minute->
            et_add_event_start_time.setText(getHourAMPM(hourOfDay).toString() + ":" + minute.toString()+ getAMPM(hourOfDay))
        }


    fun Date.formatToStringByPattern(pattern: String): String {
        val df = SimpleDateFormat(pattern)
        return df.format(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        getAllEventsTask().execute()

        var calendar: Calendar

        et_add_event_start_date.setOnClickListener {
            calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener_et_start_date(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        et_add_event_start_time.setOnClickListener {
            calendar = Calendar.getInstance()

            val timePickerDialog = TimePickerDialog(
                this,
                getTimePickerListener_et_start_time(),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }

        btn_submit_event.setOnClickListener {
            if (isInputValid()) {
                val bundle = Bundle()
                bundle.putString("EVENT_NAME", et_add_event_name.text.toString())
                bundle.putString("EVENT_LOCATION", et_add_event_location.text.toString())
                bundle.putString("EVENT_START_DATE", et_add_event_start_date.text.toString())
                bundle.putString("EVENT_START_TIME", et_add_event_start_time.text.toString())
                bundle.putString("EVENT_PACIENT", et_add_event_pacient.text.toString())
                bundle.putString("EVENT_DOCTOR", et_add_event_doctor.text.toString())

                val dashboardIntent: Intent = Intent(this, DashboardActivity()::class.java)

                dashboardIntent.putExtra("BUNDLE_EXTRA_EVENT", bundle)

                setResult(Constants.RESULT_CODE_ADD_EVENT, dashboardIntent)

                var lastId = eventList?.get(eventList!!.size-1)?.id?.substring(1)
                val eventId = "E" + ((lastId?.toInt() ?: 0) + 1).toString()
                newEvent = Event(eventId, et_add_event_name.text.toString(), et_add_event_location.text.toString(),
                    et_add_event_start_date.text.toString(), et_add_event_start_time.text.toString(), et_add_event_pacient.text.toString(),
                    et_add_event_doctor.text.toString())

                createEventTask().execute()

                finish()
            }
        }
    }

    fun isInputValid(): Boolean {

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
        if (et_add_event_start_date.text.isNullOrEmpty()) {
            til_add_event_start_date.setError("Field required")
            et_add_event_start_date.requestFocus()
            return false
        }
        if (et_add_event_start_time.text.isNullOrEmpty()) {
            til_add_event_start_time.setError("Field required")
            et_add_event_start_time.requestFocus()
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

    private inner class createEventTask : AsyncTask<Void, Void, Event>() {
        override fun doInBackground(vararg params: Void): Event? {
            try {
                Log.i("check event", "check")

                return EventService().createEvent(newEvent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(pacient: Event?) {
            getAllEventsTask().execute()
        }
    }

    private inner class getAllEventsTask : AsyncTask<Void, Void, List<Event>>() {
        override fun doInBackground(vararg params: Void): List<Event>? {
            try {
                return EventService().findAllEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(events: List<Event>?) {

            eventList = events as MutableList<Event>?
        }
    }

}
