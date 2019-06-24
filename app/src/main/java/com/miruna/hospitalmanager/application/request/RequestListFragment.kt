package com.miruna.hospitalmanager.application.request

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication
import com.miruna.hospitalmanager.application.utils.Constants
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_pacient_list.*
import kotlinx.android.synthetic.main.fragment_request_list.*

private const val ARG_PARAM1 = "param1"

class RequestListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mOnActivityFragmentCommunication: OnActivityFragmentCommunication
    var requestList: MutableList<Request>? = null
    var requestsAdapter: RequestsAdapter? = null
    lateinit var newRequest: Request
    lateinit var idDeletedRequest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context = view.getContext()

        getAllRequestsTask().execute()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constants.RESULT_CODE_ADD_EVENT) {
            var lastId = requestList?.get(requestList!!.size-1)?.id?.substring(1)
            val bundle = data?.getBundleExtra("BUNDLE_EXTRA_REQUEST") ?: return
            val requestId = "R" + ((lastId?.toInt() ?: 0) + 1).toString()
            val requestDrugName = bundle.getString("REQUEST_DRUG_NAME") ?: ""
            val requestCantity = bundle.getString("REQUEST_CANTITY") ?: ""

            newRequest =
                Request(requestId, requestDrugName, requestCantity.toInt())

            createRequestTask().execute()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnActivityFragmentCommunication) {
            this.mOnActivityFragmentCommunication = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            RequestListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
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

        override fun onPostExecute(pacient: Request?) {
            getAllRequestsTask().execute()
        }
    }

    private inner class deleteRequestTask : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean? {
            try {
                return RequestService().deleteRequestById(idDeletedRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        override fun onPostExecute(result: Boolean?) {
            getAllRequestsTask().execute()
        }
    }

    private inner class getAllRequestsTask : AsyncTask<Void, Void, List<Request>>() {
        override fun doInBackground(vararg params: Void): List<Request>? {
            try {
                return RequestService().findAllRequests()
            } catch (e: Exception) {

            }
            return null
        }

        override fun onPostExecute(requests: List<Request>?) {
            requestList = requests as MutableList<Request>

            recyclerViewRequestList.apply {
                layoutManager = LinearLayoutManager(context)
                requestsAdapter = RequestsAdapter(requestList!!)

                requestsAdapter?.onItemClick = {
                    idDeletedRequest = it.id
                    deleteRequestTask().execute()
                }
                this.adapter = requestsAdapter
            }
        }
    }
}
