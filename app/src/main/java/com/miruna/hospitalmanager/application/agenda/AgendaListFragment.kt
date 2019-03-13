package com.miruna.hospitalmanager.application.agenda

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.fragment_agenda_list.*
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication



private const val ARG_PARAM1 = "param1"


class AgendaListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mOnActivityFragmentCommunication: OnActivityFragmentCommunication

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

        val view = inflater.inflate(R.layout.fragment_agenda_list, container, false)

        return view
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context : Context = view.getContext()

        var events = mutableListOf<Event>()
        for (i in 1..9){
            events.add(
                Event(i, "Eveniment" + i.toString(), "Locatie" + i.toString(), "Pacient" + i.toString(), "Doctor"+i.toString())
            )
        }

        val bundle = Bundle()

        mOnActivityFragmentCommunication.onAddObject("EVENT_LIST_FRAGMENT", bundle)

        val newEvent = bundle.getParcelable<Event>("eveniment nou")

        if(newEvent != null){
            events.add(newEvent)
        }

        recyclerViewAgendaList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EventsAdapter(events)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnActivityFragmentCommunication) {
            this.mOnActivityFragmentCommunication = context
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
        fun newInstance(param1: String) =
            AgendaListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
