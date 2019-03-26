package com.miruna.hospitalmanager.application.request

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication

private const val ARG_PARAM1 = "param1"


class AddRequestFragment : Fragment() {
    private var param1: String? = null
    lateinit var onActivityFragmentCommunication: OnActivityFragmentCommunication
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
        return inflater.inflate(R.layout.fragment_add_request, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnActivityFragmentCommunication) {
            onActivityFragmentCommunication = context
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
            AddRequestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
