package com.miruna.hospitalmanager.application.settings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_terms_conditions.*

class TermsConditionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)
        btn_accept_terms.setOnClickListener {
            var toast = Toast.makeText(this, "Terms and consitions are accepted!", Toast.LENGTH_SHORT)
            toast.show()
        }

        btn_send_email_terms.setOnClickListener {
            var mailIntent = Intent(Intent.ACTION_SEND)
            mailIntent.putExtra(Intent.EXTRA_EMAIL, et_email.text.toString())
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Terms and Conditions")
            mailIntent.putExtra(Intent.EXTRA_TEXT, tv_terms_cond_content.text.toString())
            mailIntent.type = "message/rfc822"

            startActivity(Intent.createChooser(mailIntent, "Coose app to send email"))
        }
    }
}
