package com.miruna.hospitalmanager.application.drug

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.EditText

import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.drug_alert_dialog.*
import kotlinx.android.synthetic.main.fragment_drug_list.*

private const val ARG_PARAM1 = "param1"

class DrugListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var drugList: MutableList<Drug>? = null
    var drugsAdapter: DrugsAdapter? = null
    var updatedDrug: Drug = Drug("", "", 0)
    lateinit var newDrug: Drug


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constants.RESULT_CODE_ADD_DRUG) {
            val bundle = data?.getBundleExtra("BUNDLE_EXTRA_DRUG") ?: return
            val drugId = bundle.getString("DRUG_ID") ?: ""
            val drugName = bundle.getString("DRUG_NAME") ?: ""
            val drugNumber = bundle.getString("DRUG_NUMBER") ?: ""
            newDrug = Drug(drugId, drugName, drugNumber.toInt())

            createDrugTask().execute()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_drug_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context = view.getContext()

        getAllDrugsTask().execute()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
        fun newInstance(param1: Int) =
            DrugListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1.toString())
                }
            }
    }

    private inner class createDrugTask : AsyncTask<Void, Void, Drug>() {
        override fun doInBackground(vararg params: Void): Drug? {
            try {
                return DrugService().createDrug(newDrug)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("check", "check")
            }
            return null
        }

        override fun onPostExecute(pacient: Drug?) {
            getAllDrugsTask().execute()
        }
    }

    private inner class updateDrugTask : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            try {
                return DrugService().updateDrug(updatedDrug)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        override fun onPostExecute(result: Boolean?) {
            getAllDrugsTask().execute()
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

            recyclerViewDrugList.apply {
                layoutManager = LinearLayoutManager(context)
                drugsAdapter = DrugsAdapter(drugList!!)

                drugsAdapter?.onItemClick = {
                    var drug = it
                    var dialogView = LayoutInflater.from(context).inflate(R.layout.drug_alert_dialog, null)
                    var dialogBuilder = AlertDialog.Builder(context).setView(dialogView).setTitle("")
                    var alertDialog = dialogBuilder.show()
                    alertDialog.btn_update_drug.setOnClickListener {

                        updatedDrug.id = drug.id
                        updatedDrug.name = drug.name

                        var editText = alertDialog.findViewById<EditText>(R.id.et_update_drug)

                        if(!editText.text.isNullOrEmpty()){
                            updatedDrug.drugNumber = editText.text.toString().toInt()
                        }else{
                            updatedDrug.drugNumber = drug.drugNumber
                        }
                        updateDrugTask().execute()
                        alertDialog.dismiss()
                    }
                }
                this.adapter = drugsAdapter
            }
        }
    }
}

