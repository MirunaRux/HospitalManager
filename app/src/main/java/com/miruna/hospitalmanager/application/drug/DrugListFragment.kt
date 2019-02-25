package com.miruna.hospitalmanager.application.drug

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.fragment_drug_list.*
import kotlinx.android.synthetic.main.fragment_pacient_list.*

private const val ARG_PARAM1 = "param1"

class DrugListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null

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

        val view = inflater.inflate(R.layout.fragment_drug_list, container, false)

        return view
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context : Context = view.getContext()

        var drugs = mutableListOf<Drug>()
        for (i in 1..9){
            drugs.add(
                Drug("Drug"+i.toString(), i*100)
            )
        }
        recyclerViewDrugList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DrugsAdapter(drugs)
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