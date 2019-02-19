package com.miruna.hospitalmanager.application.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.signUp.SignUpActivity
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            val dashboardIntent = Intent(this, DashboardActivity::class.java)
            if (isInputValid()) {
                SharedPreferenceManager.saveUsername(this, til_login_username.toString())
                dashboardIntent.putExtra("EXTRA_USERNAME", til_login_username.toString())
                startActivity(dashboardIntent)
                finish()
            }
        }

        btn_signUp.setOnClickListener {
            val signUpScreenIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpScreenIntent)
            finish()
        }

    }

    fun isInputValid(): Boolean {

        if (et_login_username.text.isNullOrEmpty()) {
            til_login_username.setError("Field required")
            et_login_username.requestFocus()
            return false
        }

        if (et_login_password.text.isNullOrEmpty()) {
            til_login_password.setError("Field required")
            et_login_password.requestFocus()
            return false
        }

        return true
    }
}
