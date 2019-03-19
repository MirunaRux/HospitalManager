package com.miruna.hospitalmanager.application.drug

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_drug.*

class AddDrugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drug)

        btn_submit_drug.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("DRUG_ID", et_add_drug_id.text.toString())
            bundle.putString("DRUG_NAME", et_add_drug_name.text.toString())
            bundle.putString("DRUG_NUMBER", et_add_drug_number.text.toString())

            val dashboardIntent : Intent = Intent(this, DashboardActivity()::class.java)

            dashboardIntent.putExtra("BUNDLE_EXTRA_DRUG", bundle)

            setResult(Constants.RESULT_CODE_ADD_DRUG, dashboardIntent)

            finish()

        }
    }
}
