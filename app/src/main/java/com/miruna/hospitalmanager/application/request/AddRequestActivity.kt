package com.miruna.hospitalmanager.application.request

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.activity_add_request.*

class AddRequestActivity : AppCompatActivity() {
    lateinit var newRequest: Request
    var requestList: MutableList<Request>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)

        getAllRequestsTask().execute()

        btn_submit_request.setOnClickListener {
            if (isInputValid()) {
                val toast: Toast = Toast.makeText(this, "Request successfully registered !", Toast.LENGTH_LONG)
                toast.show()

                val bundle = Bundle()
                bundle.putString("REQUEST_DRUG_NAME", et_addRequest_drug_name.text.toString())
                bundle.putString("REQUEST_CANTITY", et_addRequest_cantity.text.toString())

                val dashboardIntent = Intent(this, DashboardActivity()::class.java)

                dashboardIntent.putExtra("BUNDLE_EXTRA_REQUEST", bundle)

                setResult(Constants.RESULT_CODE_ADD_DRUG, dashboardIntent)

                var lastId = requestList?.get(requestList!!.size-1)?.id?.substring(1)
                val requestId = "R" + ((lastId?.toInt() ?: 0) + 1).toString()
                newRequest = Request(requestId, et_addRequest_drug_name.text.toString(), et_addRequest_cantity.text.toString().toInt())

                createRequestTask().execute()

                finish()
            } else {
                val toast: Toast = Toast.makeText(this, "Bad request !", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_addRequest_drug_name.text.isNullOrEmpty()) {
            til_addRequest_drug_name.setError("Field required")
            et_addRequest_drug_name.requestFocus()
            return false
        }
        if (et_addRequest_cantity.text.isNullOrEmpty()) {
            til_addRequest_cantity.setError("Field required")
            et_addRequest_cantity.requestFocus()
            return false
        }

        return true
    }
    private inner class createRequestTask : AsyncTask<Void, Void, Request>() {
        override fun doInBackground(vararg params: Void): Request? {
            try {
                return RequestService().createRequest(newRequest)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("check", "check")
            }
            return null
        }
    }

    private inner class getAllRequestsTask : AsyncTask<Void, Void, List<Request>>() {
        override fun doInBackground(vararg params: Void): List<Request>? {
            try {
                Log.i("requestGogu", "a ajuns unde trebuie")
                return RequestService().findAllRequests()
            } catch (e: Exception) {

            }
            return null
        }

        override fun onPostExecute(requests: List<Request>?) {
            requestList = requests as MutableList<Request>?
        }
    }
}
