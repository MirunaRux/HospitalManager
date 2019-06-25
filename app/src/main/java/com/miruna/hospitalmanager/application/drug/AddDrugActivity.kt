package com.miruna.hospitalmanager.application.drug

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_drug.*

class AddDrugActivity : AppCompatActivity() {

    var drugList: MutableList<Drug>? = null
    lateinit var newDrug: Drug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drug)


        getAllDrugsTask().execute()

        btn_submit_drug.setOnClickListener {
            if(isInputValid()){
                val bundle = Bundle()
                bundle.putString("DRUG_NAME", et_add_drug_name.text.toString())
                bundle.putString("DRUG_NUMBER", et_add_drug_number.text.toString())
                Log.i("drugGigel", drugList?.size.toString())

                val dashboardIntent : Intent = Intent(this, DashboardActivity()::class.java)

                dashboardIntent.putExtra("BUNDLE_EXTRA_DRUG", bundle)

                setResult(Constants.RESULT_CODE_ADD_DRUG, dashboardIntent)
                var lastId = drugList?.get(drugList!!.size-1)?.id?.substring(1)
                val drugId = "D" + ((lastId?.toInt() ?: 0) + 1).toString()
                newDrug = Drug(drugId, et_add_drug_name.text.toString(), et_add_drug_number.text.toString().toInt())
                Log.i("drugGigel", drugId)
                Log.i("drugGigel", et_add_drug_name.text.toString())
                Log.i("drugGigel", et_add_drug_number.text.toString())
                createDrugTask().execute()

                finish()
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_add_drug_name.text.isNullOrEmpty()) {
            til_add_drug_name.setError("Field required")
            et_add_drug_name.requestFocus()
            return false
        }
        if (et_add_drug_number.text.isNullOrEmpty()) {
            til_add_drug_number.setError("Field required")
            et_add_drug_number.requestFocus()
            return false
        }

        return true
    }

    private inner class createDrugTask : AsyncTask<Void, Void, Drug>() {
        override fun doInBackground(vararg params: Void): Drug? {
            try {
                Log.i("drugGigel", "a ajuns la create")
                return DrugService().createDrug(newDrug)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("check", "check")
            }
            return null
        }

    }

    private inner class getAllDrugsTask : AsyncTask<Void, Void, List<Drug>>() {
        override fun doInBackground(vararg params: Void): List<Drug>? {
            try {
                return DrugService().findAllDrugs()
            } catch (e: Exception) {

            }

            return null
        }

        override fun onPostExecute(drugs: List<Drug>?) {

            drugList = drugs as MutableList<Drug>
        }
    }
}
