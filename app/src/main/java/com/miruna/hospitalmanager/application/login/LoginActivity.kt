package com.miruna.hospitalmanager.application.login

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.signUp.SignUpActivity
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Spinner
import android.widget.Toast
import com.miruna.hospitalmanager.application.signUp.User
import com.miruna.hospitalmanager.application.signUp.UserService
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.miruna.hospitalmanager.R


class LoginActivity : AppCompatActivity() {

    lateinit var currentUser: User
    lateinit var loginIntent: Intent
    lateinit var extraUsername: String
    lateinit var extraRole: String
    lateinit var mPrefs: SharedPreferences
    var rememberMeChecked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mPrefs = getPreferences(Context.MODE_PRIVATE)

        loginIntent = getIntent()

        extraUsername = loginIntent.getStringExtra("EXTRA_USERNAME") ?: ""
        extraRole = loginIntent.getStringExtra("EXTRA_ROLE") ?: "Role"

        et_login_username.setText(extraUsername)

        if (!extraRole.equals("Role")) {
            spinner_login_role.setSelection(getIndex(spinner_login_role, extraRole))
        }

        checkBox_constraint_remember.setOnClickListener{
            rememberMeChecked = true
        }

        val gson = Gson()
        val json = mPrefs.getString("User", "")
        val savedUser = gson.fromJson<User>(json, User::class.java!!)

        if(savedUser != null)
        {
            et_login_username.setText(savedUser.username)
            et_login_password.setText(savedUser.password)
            spinner_login_role.setSelection(getIndex(spinner_login_role, savedUser.role))
            checkBox_constraint_remember.isChecked = true
        }

        btn_login.setOnClickListener {
            if (isInputValid()) {
                currentUser = User(
                    et_login_username.text.toString(), et_login_password.text.toString(),
                    spinner_login_role.selectedItem.toString()
                )
                isUserExistTask().execute()
            }
        }

        btn_signUp.setOnClickListener {
            val signUpScreenIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpScreenIntent)
            finish()
        }

    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0..spinner.count) {
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

    fun goToDashboard() {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        SharedPreferenceManager.saveUsername(this, et_login_username.text.toString())
        dashboardIntent.putExtra("EXTRA_USERNAME", et_login_username.text.toString())
        dashboardIntent.putExtra("EXTRA_ROLE", extraRole)
        startActivity(dashboardIntent)
        finish()

    }

    private inner class isUserExistTask : AsyncTask<Void, Void, Void>() {
        var showToast : Boolean = false
        override fun doInBackground(vararg params: Void): Void? {
            try {
                var exist: Boolean = UserService().isUserExist(currentUser)
                if (!exist) {
                    showToast = true
                } else {
                    if(checkBox_constraint_remember.isChecked){
                        val prefsEditor = mPrefs.edit()
                        val gson = Gson()
                        val json = gson.toJson(currentUser)
                        prefsEditor.putString("User", json)
                        prefsEditor.commit()
                    }
                    goToDashboard()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(pacient: Void?) {
            if (showToast) {
                val toast = Toast.makeText(applicationContext, "User not registered !", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
}