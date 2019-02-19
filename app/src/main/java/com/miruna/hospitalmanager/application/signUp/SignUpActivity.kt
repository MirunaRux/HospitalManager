package com.miruna.hospitalmanager.application.signUp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_save.setOnClickListener {
            val dashboardIntent = Intent(this, DashboardActivity::class.java)
            if (isInputValid()) {
                SharedPreferenceManager.saveUsername(this, til_signUp_username.toString())
                dashboardIntent.putExtra("EXTRA_USERNAME", til_signUp_username.toString())
                setResult(Activity.RESULT_OK, dashboardIntent)
                finish()
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_signUp_username.text.isNullOrEmpty()) {
            til_signUp_username.setError("Field required")
            et_signUp_username.requestFocus()
            return false
        }
        if (et_signUp_name.text.isNullOrEmpty()) {
            til_signUp_name.setError("Field required")
            et_signUp_username.requestFocus()
            return false
        }
        if (et_signUp_surname.text.isNullOrEmpty()) {
            til_signUp_surname.setError("Field required")
            et_signUp_surname.requestFocus()
            return false
        }

        if (til_signUp_password.toString().trim({ it <= ' ' }) != til_signUp_re_password.toString().trim({ it <= ' ' })) {
            til_signUp_re_password.setError("Password not matching")
            et_signUp_re_password.requestFocus()
            return false
        }

        return true
    }
}
