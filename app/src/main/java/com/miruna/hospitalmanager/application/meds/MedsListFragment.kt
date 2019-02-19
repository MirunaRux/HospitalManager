package com.miruna.hospitalmanager.application.meds

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miruna.hospitalmanager.R
import java.util.ArrayList

class MedsListFragment : Fragment() {
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null
    private val xadapter: MedsRecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    val ITEMS: MutableList<Drug> = ArrayList<Drug>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medicamente, container, false)

        val context = view.getContext()
        recyclerView = view.findViewById(R.id.list)
        if (mColumnCount <= 1) {
            recyclerView!!.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView!!.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        var adapter = MedsRecyclerViewAdapter(ITEMS, mListener)
        recyclerView!!.adapter = adapter
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(recyclerView!!.context, DividerItemDecoration.VERTICAL)
        )
        return view
    }

    override fun onResume() {
        super.onResume()

        var adapter = MedsRecyclerViewAdapter(ITEMS, mListener)
        recyclerView!!.adapter = adapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Drug, pos: Int)
    }

    companion object {
        private val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): MedsListFragment {
            val fragment = MedsListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}

