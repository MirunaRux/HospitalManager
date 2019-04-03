package com.miruna.hospitalmanager.application.pacient

import android.content.Context
import android.content.Intent
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


private const val ARG_PARAM1 = "param1"


class PacientListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var onActivityFragmentCommunication: OnActivityFragmentCommunication
    var pacientList: MutableList<Pacient>? = null
    var pacientsAdapter: PacientsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Constants.RESULT_CODE_ADD_PACIENT) {
            val bundle = data?.getBundleExtra("BUNDLE_EXTRA_PACIENT") ?: return
            val pacientId = bundle.getString("PACIENT_ID") ?: ""
            val pacientName = bundle.getString("PACIENT_NAME") ?: ""
            val pacientSurname = bundle.getString("PACIENT_SURNAME") ?: ""
            val pacientAge = bundle.getString("PACIENT_AGE") ?: ""
            val pacientCNP = bundle.getString("PACIENT_CNP") ?: ""
            val pacientDateIn = bundle.getString("PACIENT_DATE_IN") ?: ""
            val pacientDateEx = bundle.getString("PACIENT_DATE_EX") ?: ""
            val newPacient =
                Pacient(pacientId, pacientName, pacientSurname, pacientAge, pacientCNP, pacientDateIn, pacientDateEx)

            pacientList?.add(newPacient)
            pacientsAdapter?.notifyDataSetChanged()
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

        pacientList = PacientService().findAllPacients()

        var pacientListAux = pacientList

        search_pacient_button.setOnClickListener {

            PacientService().findByName(pacientList, et_search_pacient.text.toString())?.let{
                pacientListAux?.addAll(it)
                pacientsAdapter = PacientsAdapter(pacientListAux!!)
                pacientsAdapter?.notifyDataSetChanged()
            }
        }

        cancel_search_pacient_button.setOnClickListener {
            pacientsAdapter = PacientsAdapter(pacientList!!)
            pacientsAdapter?.notifyDataSetChanged()
        }

        recyclerViewPacientList.apply {
            layoutManager = LinearLayoutManager(context)
            pacientsAdapter = PacientsAdapter(pacientList!!)
            pacientsAdapter?.onItemClick = {
                val pacientDetailsIntent = Intent(context, PacientDetailsActivity::class.java)
                SharedPreferenceManager.savePacientId(context, it.id)
                pacientDetailsIntent.putExtra("EXTRA_NAME", it.name)
                pacientDetailsIntent.putExtra("EXTRA_SURNAME", it.surname)
                pacientDetailsIntent.putExtra("EXTRA_AGE", it.age)
                pacientDetailsIntent.putExtra("EXTRA_CNP", it.CNP)
                pacientDetailsIntent.putExtra("EXTRA_DATE_IN", it.dateIn)
                pacientDetailsIntent.putExtra("EXTRA_DATE_EX", it.dateEx)
                startActivity(pacientDetailsIntent)
            }
            this.adapter = pacientsAdapter
        }
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
}
