package com.miruna.hospitalmanager.application.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val handler = Handler()
        handler.postDelayed({
            val loginScreenIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginScreenIntent)
            finish()
        }, 1000)
    }
}
