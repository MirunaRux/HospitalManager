package com.miruna.hospitalmanager.application.signUp

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.login.LoginActivity
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        btn_save.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            if (isInputValid()) {
                SharedPreferenceManager.saveUsername(this, til_signUp_username.toString())
                loginIntent.putExtra("EXTRA_USERNAME", et_signUp_username.text.toString())
                loginIntent.putExtra("EXTRA_ROLE", spinner_signUp_role.selectedItem.toString())
                Log.i("gigel", "cucu")
                createUserTask().execute()
                startActivity(loginIntent)
                finish()
            }
        }
    }

    private inner class createUserTask : AsyncTask<Void, Void, User>() {
        override fun doInBackground(vararg params: Void): User? {
            try {
                return UserService().createUser(User(
                    et_signUp_username.text.toString(),
                    et_signUp_password.text.toString(),
                    spinner_signUp_role.selectedItem.toString()
                ))
            } catch (e: Exception) {

            }
            return null
        }

        override fun onPostExecute(pacient: User?) {

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val loginIntent: Intent = Intent(this, LoginActivity()::class.java)
        startActivity(loginIntent)
        finish()
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

        if (!et_signUp_password.getText().toString().equals(et_signUp_re_password.getText().toString())) {
            til_signUp_re_password.setError("Password not matching")
            et_signUp_re_password.requestFocus()
            return false
        }

        return true
    }
}
