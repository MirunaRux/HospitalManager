package com.miruna.hospitalmanager.application.drug

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.drug_row.view.*

class DrugsAdapter (private val drugs: MutableList<Drug>) : RecyclerView.Adapter<DrugsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.drug_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = drugs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.drug_name.text = drugs[position].name
        holder.number.text = drugs[position].number.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drug_name : TextView = itemView.drug_name
        val number : TextView = itemView.number_of_drugs
    }

}