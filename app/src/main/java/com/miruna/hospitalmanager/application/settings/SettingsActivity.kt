package com.miruna.hospitalmanager.application.settings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btn_terms_conditions.setOnClickListener {
            val termsConditionsIntent = Intent(this, TermsConditionsActivity::class.java)
            startActivity(termsConditionsIntent)
        }
    }

}
