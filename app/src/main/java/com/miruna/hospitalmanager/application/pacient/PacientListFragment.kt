package com.miruna.hospitalmanager.application.pacient

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.fragment_pacient_list.*


private const val ARG_PARAM1 = "param1"

class PacientListFragment : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_pacient_list, container, false)

        return view
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context : Context = view.getContext()

        var pacients = mutableListOf<Pacient>()
        for (i in 1..9){
            pacients.add(Pacient("Pacient" + i.toString(), "Pacient" + i.toString(), i,
                null, "0"+i.toString()+".0"+i.toString()+".2018", "0"+i.toString()+".0"+i.toString()+".2018",
                "Departament"+i.toString()))
        }
        recyclerViewPacientList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PacientsAdapter(pacients)
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

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
            PacientListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1.toString())
                }
            }
    }
}
