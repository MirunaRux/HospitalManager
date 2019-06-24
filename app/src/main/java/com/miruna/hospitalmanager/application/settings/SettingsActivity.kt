package com.miruna.hospitalmanager.application.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.agenda.AgendaListFragment
import com.miruna.hospitalmanager.application.agenda.Event
import com.miruna.hospitalmanager.application.agenda.EventService
import com.miruna.hospitalmanager.application.notification.NotificationUtils
import com.miruna.hospitalmanager.application.signUp.User
import kotlinx.android.synthetic.main.activity_settings.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {
    private val mNotificationTime =
        Calendar.getInstance().timeInMillis + 10000 //Set after 5 seconds from the current time.
    private var mNotified = false
    private var eventList: List<Event> = arrayListOf()
    lateinit var mPrefs: SharedPreferences
    var toggleChecked: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mPrefs = getPreferences(Context.MODE_PRIVATE)

        toggleChecked = mPrefs.getBoolean("Toggle notifications", false)

        if (toggleChecked) {
            tg_btn_notifications.toggle()
        }

        tg_btn_notifications.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val prefsEditor = mPrefs.edit()
                prefsEditor.putBoolean("Toggle notifications", true)
                prefsEditor.commit()
                Log.i("not", "checked")
                toggleChecked = true
                for (e in eventList) {
                    val gson = Gson()
                    val json = mPrefs.getString("User", "")
                    val savedUser = gson.fromJson<User>(json, User::class.java!!)
                    if (e.doctorUsername!!.equals(savedUser)) {
                        if (!mNotified) {
                            var date = SimpleDateFormat("dd-MM-yyyy").parse(e.startDate).time
                            var timeD = e.startTime?.split(" ")
                            var hm = timeD?.get(0)?.split(":")
                            if (timeD?.get(1).equals("AM")) {
                                date = date + hm?.get(0)?.toLong()?.let { TimeUnit.HOURS.toMillis(it) }!!
                                date = date + hm?.get(1)?.toLong()?.let { TimeUnit.MINUTES.toMillis(it) }!!
                            } else {
                                date = date + hm?.get(0)?.toLong()?.let { TimeUnit.HOURS.toMillis(it) }!!
                                date = date + hm?.get(1)?.toLong()?.let { TimeUnit.MINUTES.toMillis(it) }!!
                                date = date + 43200000
                            }
                            date = date - 60000
                            NotificationUtils().setNotification(date, this@SettingsActivity)
                            mNotified = true
                        }
                    }
                }
            } else {
                val prefsEditor = mPrefs.edit()
                prefsEditor.putBoolean("Toggle notifications", false)
                prefsEditor.commit()

                Log.i("not", "unchecked")
            }
        }


        btn_terms_conditions.setOnClickListener {
            val termsConditionsIntent = Intent(this, TermsConditionsActivity::class.java)
            startActivity(termsConditionsIntent)
        }

    }

    private inner class getAllEventsTask : AsyncTask<Void, Void, List<Event>>() {
        override fun doInBackground(vararg params: Void): List<Event>? {
            try {
                eventList = EventService().findAllEvents()
                return eventList
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(events: List<Event>?) {

        }

    }

}
