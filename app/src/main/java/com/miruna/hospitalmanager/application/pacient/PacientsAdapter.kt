package com.miruna.hospitalmanager.application.pacient

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.pacient_row.view.*

class PacientsAdapter(private val pacients: MutableList<Pacient>) : RecyclerView.Adapter<PacientsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pacient_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pacients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pacient_name.text = pacients[position].name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pacient_name : TextView = itemView.pacient_name
    }

}
