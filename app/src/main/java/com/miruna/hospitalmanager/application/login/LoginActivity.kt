package com.miruna.hospitalmanager.application.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.signUp.SignUpActivity
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Spinner





class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginIntent :Intent = getIntent()
        val extraUsername = loginIntent.getStringExtra("EXTRA_USERNAME")
        val extraRole = loginIntent.getStringExtra("EXTRA_ROLE")
        et_login_username.setText(extraUsername)

        if(extraRole!= null){
            spinner_login_role.setSelection(getIndex(spinner_login_role, extraRole))
        }

        btn_login.setOnClickListener{
            val dashboardIntent = Intent(this, DashboardActivity::class.java)
            if (isInputValid()) {
                SharedPreferenceManager.saveUsername(this, et_login_username.text.toString())
                dashboardIntent.putExtra("EXTRA_USERNAME", et_login_username.text.toString())
                dashboardIntent.putExtra("EXTRA_ROLE", extraRole)
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

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 .. spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
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
