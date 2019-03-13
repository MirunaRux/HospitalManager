package com.miruna.hospitalmanager.application.pacient

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.EditText
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.dashboard.OnActivityFragmentCommunication
import com.miruna.hospitalmanager.application.signUp.SignUpActivity
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_pacient_list.*


private const val ARG_PARAM1 = "param1"


class PacientListFragment : Fragment(){
    private var param1: String? = null
    private var listener: PacientDetailsFragment.OnFragmentInteractionListener? = null
    var pacients = mutableListOf<Pacient>()
    var displayPacients = mutableListOf<Pacient>()
    lateinit var onActivityFragmentCommunication : OnActivityFragmentCommunication
    lateinit var newPacient : Pacient

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
            pacients.add(Pacient(i, "Pacient" + i.toString(), "Pacient" + i.toString(), i.toString(),
                "2971209152479", "0"+i.toString()+".0"+i.toString()+".2018", "0"+i.toString()+".0"+i.toString()+".2018"))
        }

        /*val newPacient_id = arguments!!.getString("PACIENT_ID")
        val newPacient_name = arguments!!.getString("PACIENT_NAME")
        val newPacient_surname = arguments!!.getString("PACIENT_SURNAME")
        val newPacient_age = arguments!!.getString("PACIENT_AGE")
        val newPacient_cnp = arguments!!.getString("PACIENT_CNP")
        val newPacient_date_in = arguments!!.getString("PACIENT_DATE_IN")
        val newPacient_date_ex = arguments!!.getString("PACIENT_DATE_EX")

        if(newPacient_id != null && newPacient_name != null && newPacient_surname != null && newPacient_age != null && newPacient_cnp != null &&
            newPacient_date_in != null && newPacient_date_ex != null){
            newPacient = Pacient(newPacient_id.toInt(), newPacient_name, newPacient_surname, newPacient_age, newPacient_cnp, newPacient_date_in, newPacient_date_ex)
        }

        if(newPacient != null)
            pacients.add(newPacient)*/

        displayPacients.addAll(pacients)
        recyclerViewPacientList.apply {
            layoutManager = LinearLayoutManager(context)
            val adapter = PacientsAdapter(pacients)
            adapter.onItemClick = {
               // onActivityFragmentCommunication.onAddFragment("DETAILS_FRAGMENT", null , it)
                val pacientDetailsIntent = Intent(context, PacientDetailsActivity::class.java)
                SharedPreferenceManager.savePacientId(context, it.id.toString())
                pacientDetailsIntent.putExtra("EXTRA_NAME", it.name)
                pacientDetailsIntent.putExtra("EXTRA_SURNAME", it.surname)
                pacientDetailsIntent.putExtra("EXTRA_AGE", it.age)
                pacientDetailsIntent.putExtra("EXTRA_CNP", it.CNP)
                pacientDetailsIntent.putExtra("EXTRA_DATE_IN", it.dateIn)
                pacientDetailsIntent.putExtra("EXTRA_DATE_EX", it.dateEx)
                startActivity(pacientDetailsIntent)
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
