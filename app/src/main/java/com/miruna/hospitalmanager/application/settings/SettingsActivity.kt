package com.miruna.hospitalmanager.application.settings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.notification.NotificationUtils
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 10000 //Set after 5 seconds from the current time.
    private var mNotified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tg_btn_notifications.setOnCheckedChangeListener { buttonView, isChecked ->  
            if (isChecked) {
                Log.i("not", "checked")
                if (!mNotified) {
                    NotificationUtils().setNotification(mNotificationTime, this@SettingsActivity)
                }
            } else {
                // pass
                Log.i("not", "unchecked")
            }
        }


        btn_terms_conditions.setOnClickListener {
            val termsConditionsIntent = Intent(this, TermsConditionsActivity::class.java)
            startActivity(termsConditionsIntent)
        }
    }

}
