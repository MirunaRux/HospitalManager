package com.miruna.hospitalmanager.application.meds

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R

class MedsRecyclerViewAdapter(
    private var drugList: List<Drug>?,
    private val mListener: MedsListFragment.OnListFragmentInteractionListener?
) :
    RecyclerView.Adapter<MedsRecyclerViewAdapter.ViewHolder>() {

    fun setData(drugList: List<Drug>) {
        this.drugList = drugList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_medicamente_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(drugList!![position])
    }

    override fun getItemCount(): Int {
        return drugList!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvName: TextView
        val tvNumber: TextView
        lateinit var currentDrug: Drug

        init {
            tvName = mView.findViewById(R.id.tv_item_drug_name) as TextView
            tvNumber = mView.findViewById(R.id.tv_item_drug_number) as TextView

            mView.setOnClickListener {
                if (null != mListener) {
                    mListener!!.onListFragmentInteraction(drugList!![adapterPosition], adapterPosition)
                }
            }
        }

        fun onBind(drug: Drug) {
            currentDrug = drug
            tvName.setText(currentDrug.name)
            tvNumber.setText(currentDrug.number)
        }

        override fun toString(): String {
            return super.toString() + " '" + tvNumber.text + "'"
        }
    }
}
