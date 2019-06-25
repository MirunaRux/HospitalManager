package com.miruna.hospitalmanager.application.pacient

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_pacient_list.*
import com.miruna.hospitalmanager.application.utils.Constants
import android.widget.ArrayAdapter
import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.widget.Toast


private const val ARG_PARAM1 = "param1"


class PacientListFragment : Fragment() {

    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var onActivityFragmentCommunication: OnActivityFragmentCommunication
    var pacientList: MutableList<Pacient>? = null
    var pacientsAdapter: PacientsAdapter? = null
    lateinit var newPacient: Pacient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

    }

    override fun onStart() {
        getAllPacientsTask().execute()
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constants.RESULT_CODE_ADD_PACIENT) {
            var lastId = pacientList?.get(pacientList!!.size-1)?.id?.substring(1)
            val bundle = data?.getBundleExtra("BUNDLE_EXTRA_PACIENT") ?: return
            val pacientId = "P" + ((lastId?.toInt() ?: 0) + 1).toString()
            Log.i("goguletu", pacientId)
            val pacientName = bundle.getString("PACIENT_NAME") ?: ""
            val pacientSurname = bundle.getString("PACIENT_SURNAME") ?: ""
            val pacientBirthday = bundle.getString("PACIENT_BIRTHDAY") ?: ""
            val pacientCnp = bundle.getString("PACIENT_CNP") ?: ""
            val pacientDateIn = bundle.getString("PACIENT_DATE_IN") ?: ""
            val pacientDateEx = bundle.getString("PACIENT_DATE_EX") ?: ""
            newPacient =
                    Pacient(
                        pacientId,
                        pacientName,
                        pacientSurname,
                        pacientBirthday,
                        pacientCnp,
                        pacientDateIn,
                        pacientDateEx
                    )

            createPacientTask().execute()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pacient_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context: Context = view.getContext()

        search_pacient_button.setOnClickListener {
            getFilteredPacientsTask().execute()
        }

        cancel_search_pacient_button.setOnClickListener {
            getAllPacientsTask().execute()
        }

        getAllPacientsTask().execute()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnActivityFragmentCommunication) {
            onActivityFragmentCommunication = context
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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

    private inner class createPacientTask : AsyncTask<Void, Void, Pacient>() {
        override fun doInBackground(vararg params: Void): Pacient? {
            try {
                return PacientService().createPacient(newPacient)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("check", "check")
            }
            return null
        }

        override fun onPostExecute(pacient: Pacient?) {
           getAllPacientsTask().execute()
        }
    }

    private inner class getFilteredPacientsTask : AsyncTask<Void, Void, List<Pacient>>() {
        override fun doInBackground(vararg params: Void?): List<Pacient>? {
            try {
                var pacientListAux = PacientService().findAllPacients()
                return PacientService().findByName(pacientListAux, et_search_pacient.text.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(pacients: List<Pacient>?) {
            pacientList = pacients as MutableList<Pacient>?

            recyclerViewPacientList.apply {
                layoutManager = LinearLayoutManager(context)
                pacientsAdapter = PacientsAdapter(pacientList!!)

                var itemDecoration =
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDecoration.setDrawable(resources.getDrawable(R.drawable.list_divider))
                recyclerViewPacientList.addItemDecoration(itemDecoration)
                pacientsAdapter?.onItemClick = {
                    val pacientDetailsIntent = Intent(context, PacientDetailsActivity::class.java)
                    pacientDetailsIntent.putExtra("EXTRA_ID", it.id)
                    pacientDetailsIntent.putExtra("EXTRA_NAME", it.name)
                    pacientDetailsIntent.putExtra("EXTRA_SURNAME", it.surname)
                    pacientDetailsIntent.putExtra("EXTRA_BIRTHDAY", it.birthday)
                    pacientDetailsIntent.putExtra("EXTRA_CNP", it.cnp)
                    pacientDetailsIntent.putExtra("EXTRA_DATE_IN", it.dateIn)
                    pacientDetailsIntent.putExtra("EXTRA_DATE_EX", it.dateEx)
                    startActivity(pacientDetailsIntent)
                }
                this.adapter = pacientsAdapter
            }
        }
    }

    private inner class getAllPacientsTask : AsyncTask<Void, Void, List<Pacient>>() {
        override fun doInBackground(vararg params: Void): List<Pacient>? {
            try {
                return PacientService().findAllPacients()
            } catch (e: Exception) {

            }
            return null
        }

        override fun onPostExecute(pacients: List<Pacient>?) {

            pacientList = pacients as MutableList<Pacient>?

            var itemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)

            itemDecoration.setDrawable(resources.getDrawable(R.drawable.list_divider))

            recyclerViewPacientList.addItemDecoration(itemDecoration)

            recyclerViewPacientList.apply {
                layoutManager = LinearLayoutManager(context)
                pacientsAdapter = PacientsAdapter(pacientList!!)

                var itemDecoration =
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDecoration.setDrawable(resources.getDrawable(R.drawable.list_divider))
                recyclerViewPacientList.addItemDecoration(itemDecoration)

                pacientsAdapter?.onItemClick = {
                    val pacientDetailsIntent = Intent(context, PacientDetailsActivity::class.java)
                    pacientDetailsIntent.putExtra("EXTRA_ID", it.id)
                    pacientDetailsIntent.putExtra("EXTRA_NAME", it.name)
                    pacientDetailsIntent.putExtra("EXTRA_SURNAME", it.surname)
                    pacientDetailsIntent.putExtra("EXTRA_BIRTHDAY", it.birthday)
                    pacientDetailsIntent.putExtra("EXTRA_CNP", it.cnp)
                    pacientDetailsIntent.putExtra("EXTRA_DATE_IN", it.dateIn)
                    pacientDetailsIntent.putExtra("EXTRA_DATE_EX", it.dateEx)
                    Log.i("gigel3", "se seteaza" + it.id)
                    startActivity(pacientDetailsIntent)
                }
                this.adapter = pacientsAdapter
            }
        }

    }
}
