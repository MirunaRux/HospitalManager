package com.miruna.hospitalmanager.application.agenda

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.event_alert_dialog.*
private const val ARG_PARAM1 = "param1"


class AgendaListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mOnActivityFragmentCommunication: OnActivityFragmentCommunication
    var eventList: MutableList<Event>? = null
    var eventsAdapter:EventsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constants.RESULT_CODE_ADD_EVENT) {
            val bundle = data?.getBundleExtra("BUNDLE_EXTRA_EVENT") ?: return
            val eventId = bundle.getString("EVENT_ID")?: ""
            val eventName = bundle.getString("EVENT_NAME")?: ""
            val eventLocation = bundle.getString("EVENT_LOCATION")?: ""
            val eventPacient = bundle.getString("EVENT_PACIENT")?: ""
            val eventDoctor = bundle.getString("EVENT_DOCTOR")?: ""
            val newEvent = Event(eventId, eventName, eventLocation, eventPacient, eventDoctor)

            eventList?.add(newEvent)
            eventsAdapter?.notifyDataSetChanged()
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

        eventList = EventService().findAllEvents()

        recyclerViewAgendaList.apply {
            layoutManager = LinearLayoutManager(context)
            eventsAdapter = EventsAdapter(eventList!!)

            eventsAdapter?.onItemClick = {
                val event = it
                val dialogView = LayoutInflater.from(context).inflate(R.layout.event_alert_dialog, null)
                val dialogBuilder = AlertDialog.Builder(context).setView(dialogView).setTitle("")
                val  alertDialog = dialogBuilder.show()

                alertDialog.btn_dialog_delete.setOnClickListener {
                    EventService().deleteEventById(event.id)
                    alertDialog.dismiss()
                }
            }
            this.adapter = eventsAdapter
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
