package com.miruna.hospitalmanager.application.agenda

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.fragment_agenda_list.*
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.event_alert_dialog.*
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"


class AgendaListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mOnActivityFragmentCommunication: OnActivityFragmentCommunication
    var eventList: MutableList<Event>? = null
    var eventsAdapter: EventsAdapter? = null
    lateinit var idDeletedEvent: String
    lateinit var newEvent: Event
    var doctorUsername: String = ""
    var userRole: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

        val dashboardActivity = activity as DashboardActivity?
        userRole = dashboardActivity?.sendRole() ?: ""
        doctorUsername = dashboardActivity?.sendUsername() ?: ""

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constants.RESULT_CODE_ADD_EVENT) {
            var lastId = eventList?.get(eventList!!.size-1)?.id?.substring(1)
            val bundle = data?.getBundleExtra("BUNDLE_EXTRA_EVENT") ?: return
            val eventId = "E" + ((lastId?.toInt() ?: 0) + 1).toString()
            val eventName = bundle.getString("EVENT_NAME") ?: ""
            val eventLocation = bundle.getString("EVENT_LOCATION") ?: ""
            val eventStartDate = bundle.getString("EVENT_START_DATE") ?: ""
            val eventStartTime = bundle.getString("EVENT_START_TIME") ?: ""
            val eventPacient = bundle.getString("EVENT_PACIENT") ?: ""
            val eventDoctor = bundle.getString("EVENT_DOCTOR") ?: ""

            newEvent =
                Event(eventId, eventName, eventLocation, eventStartDate, eventStartTime, eventPacient, eventDoctor)

            createEventTask().execute()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_agenda_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context = view.getContext()

        search_event_button.setOnClickListener {
            getFilteredEventsTask().execute()
        }

        cancel_search_event_button.setOnClickListener {
            getAllEventsTask().execute()
        }

        if(userRole.equals("Asistent")){
            getAllEventsTask().execute()
        }
        else if(userRole.equals("Medic")){
            getEventsByDoctorUsername().execute()
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

    private inner class createEventTask : AsyncTask<Void, Void, Event>() {
        override fun doInBackground(vararg params: Void): Event? {
            try {
                Log.i("check event", "check")

                return EventService().createEvent(newEvent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(pacient: Event?) {
            getAllEventsTask().execute()
        }
    }

    private inner class deleteEventTask : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean? {
            try {
                return EventService().deleteEventById(idDeletedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        override fun onPostExecute(result: Boolean?) {
            getAllEventsTask().execute()
        }
    }

    private inner class getFilteredEventsTask : AsyncTask<Void, Void, List<Event>>() {
        override fun doInBackground(vararg params: Void?): List<Event>? {
            try {
                var eventListAux = EventService().findAllEvents()
                if(userRole.equals("Asistent")){
                    return EventService().findByDate(eventListAux, et_search_event.text.toString())
                }
                else if(userRole.equals("Medic")){
                    var personalEvents = EventService().findByDoctorUsername(eventListAux, doctorUsername)
                    return EventService().findByDate(personalEvents, et_search_event.text.toString())
                }
            } catch (e: Exception) {

            }
            return null
        }

        override fun onPostExecute(events: List<Event>?) {
            eventList = events as MutableList<Event>?

            recyclerViewAgendaList.apply {
                layoutManager = LinearLayoutManager(context)
                eventsAdapter = EventsAdapter(eventList!!)

                eventsAdapter?.onItemClick = {
                    var event = it
                    var dialogView = LayoutInflater.from(context).inflate(R.layout.event_alert_dialog, null)
                    var dialogBuilder = AlertDialog.Builder(context).setView(dialogView).setTitle("")
                    var alertDialog = dialogBuilder.show()
                    alertDialog.btn_dialog_delete.setOnClickListener {
                        EventService().deleteEventById(event.id)
                        alertDialog.dismiss()
                    }
                }
                this.adapter = eventsAdapter
            }
        }
    }

    private inner class getEventsByDoctorUsername : AsyncTask<Void, Void, List<Event>>() {
        override fun doInBackground(vararg params: Void?): List<Event>? {
           try {
                var eventListAux = EventService().findAllEvents()
                return EventService().findByDoctorUsername(eventListAux, doctorUsername)
            } catch (e: Exception) {

            }
            return null
        }

        override fun onPostExecute(events: List<Event>?) {
            eventList = events as MutableList<Event>?

            recyclerViewAgendaList.apply {
                layoutManager = LinearLayoutManager(context)
                eventsAdapter = EventsAdapter(eventList!!)

                eventsAdapter?.onItemClick = {
                    var event = it
                    var dialogView = LayoutInflater.from(context).inflate(R.layout.event_alert_dialog, null)
                    var dialogBuilder = AlertDialog.Builder(context).setView(dialogView).setTitle("")
                    var alertDialog = dialogBuilder.show()
                    alertDialog.btn_dialog_delete.setOnClickListener {
                        EventService().deleteEventById(event.id)
                        alertDialog.dismiss()
                    }
                }
                this.adapter = eventsAdapter
            }
        }
    }


    private inner class getAllEventsTask : AsyncTask<Void, Void, List<Event>>() {
        override fun doInBackground(vararg params: Void): List<Event>? {
            try {
                return EventService().findAllEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(events: List<Event>?) {

            eventList = events as MutableList<Event>?

            recyclerViewAgendaList.apply {
                layoutManager = LinearLayoutManager(context)
                eventsAdapter = EventsAdapter(eventList!!)

                eventsAdapter?.onItemClick = {
                    var event = it
                    var dialogView = LayoutInflater.from(context).inflate(R.layout.event_alert_dialog, null)
                    var dialogBuilder = AlertDialog.Builder(context).setView(dialogView).setTitle("")
                    var alertDialog = dialogBuilder.show()
                    alertDialog.btn_dialog_delete.setOnClickListener {
                        idDeletedEvent = event.id
                        deleteEventTask().execute()
                        alertDialog.dismiss()
                    }
                }
                this.adapter = eventsAdapter
            }
        }

    }
}
