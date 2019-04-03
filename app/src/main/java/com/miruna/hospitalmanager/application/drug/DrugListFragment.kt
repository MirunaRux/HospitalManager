package com.miruna.hospitalmanager.application.drug

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.EditText

import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.fragment_drug_list.*

private const val ARG_PARAM1 = "param1"

class DrugListFragment : Fragment() {
    private var param1 : String? = null
    private var listener : OnFragmentInteractionListener? = null
    var drugList : MutableList<Drug>? = null
    var drugsAdapter : DrugsAdapter? = null


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
            val drugId = bundle.getString("DRUG_ID")?: ""
            val drugName = bundle.getString("DRUG_NAME")?: ""
            val drugNumber = bundle.getString("DRUG_NUMBER")?: ""
            val newDrug = Drug(drugId, drugName, drugNumber.toInt())

            drugList?.add(newDrug)
            drugsAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_drug_list, container, false)

        return view
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context : Context = view.getContext()

        drugList = DrugService().findAllDrugs()

        recyclerViewDrugList.apply {
            layoutManager = LinearLayoutManager(context)
            drugsAdapter = DrugsAdapter(drugList!!)

            this.adapter = drugsAdapter
        }
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
}
