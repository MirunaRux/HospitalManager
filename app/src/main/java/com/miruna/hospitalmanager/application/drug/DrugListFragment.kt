package com.miruna.hospitalmanager.application.drug

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.EditText

import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.fragment_drug_list.*

private const val ARG_PARAM1 = "param1"

class DrugListFragment : Fragment() {
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var drugs = mutableListOf<Drug>()
    var displayDrugs = mutableListOf<Drug>()

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

        for (i in 1..9){
            drugs.add(
                Drug(i, "Drug"+i.toString(), i*100)
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
                        displayDrugs.clear()
                        val search = newText.toLowerCase()
                        drugs.forEach {
                            if(it.name.toLowerCase().contains(search))
                                displayDrugs.add(it)
                        }

                        recyclerViewDrugList.adapter?.notifyDataSetChanged()
                    }else{
                        displayDrugs.clear()
                        displayDrugs.addAll(drugs)
                        recyclerViewDrugList.adapter?.notifyDataSetChanged()
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
            DrugListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1.toString())
                }
            }
    }
}
