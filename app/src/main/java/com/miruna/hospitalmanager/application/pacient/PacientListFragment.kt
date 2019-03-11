package com.miruna.hospitalmanager.application.pacient

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.EditText
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.agenda.AgendaListFragment
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.fragment_pacient_list.*


private const val ARG_PARAM1 = "param1"


class PacientListFragment : Fragment(){
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var pacients = mutableListOf<Pacient>()
    var displayPacients = mutableListOf<Pacient>()
    lateinit var onActivityFragmentCommunication : OnActivityFragmentCommunication

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

        for (i in 1..9){
            pacients.add(Pacient(i, "Pacient" + i.toString(), "Pacient" + i.toString(), i,
                null, "0"+i.toString()+".0"+i.toString()+".2018", "0"+i.toString()+".0"+i.toString()+".2018"))
        }

        displayPacients.addAll(pacients)
        recyclerViewPacientList.apply {
            layoutManager = LinearLayoutManager(context)
            val adapter = PacientsAdapter(pacients)
            adapter.onItemClick = {
                onActivityFragmentCommunication.onAddFragment("DETAILS_FRAGMENT", null , it)
            }
            this.adapter = adapter
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnActivityFragmentCommunication){
            onActivityFragmentCommunication = context
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            editText.hint = "Search here..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query : String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText : String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        displayPacients.clear()
                        val search = newText.toLowerCase()
                        pacients.forEach {
                            if(it.name.toLowerCase().contains(search))
                                displayPacients.add(it)
                        }

                        recyclerViewPacientList.adapter?.notifyDataSetChanged()
                    }else{
                        displayPacients.clear()
                        displayPacients.addAll(pacients)
                        recyclerViewPacientList.adapter?.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }

        super.onCreateOptionsMenu(menu, inflater)
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
