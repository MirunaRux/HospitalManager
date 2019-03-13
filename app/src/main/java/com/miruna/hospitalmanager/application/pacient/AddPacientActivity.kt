package com.miruna.hospitalmanager.application.pacient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_add_pacient.*

class AddPacientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacient)

        btn_submit_pacient.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("PACIENT_ID", et_add_pacient_id.text.toString())
            bundle.putString("PACIENT_NAME", et_add_pacient_name.text.toString())
            bundle.putString("PACIENT_SURNAME", et_add_pacient_surname.text.toString())
            bundle.putString("PACIENT_AGE", et_add_pacient_age.text.toString())
            bundle.putString("PACIENT_CNP", et_add_pacient_cnp.text.toString())
            bundle.putString("PACIENT_DATE_IN", et_add_pacient_dateIn.text.toString())
            bundle.putString("PACIENT_DATE_EX", et_add_pacient_dateEx.text.toString())

            val pacientListFragment = PacientListFragment()
            pacientListFragment.setArguments(bundle)
            finish()
        }
    }
}
