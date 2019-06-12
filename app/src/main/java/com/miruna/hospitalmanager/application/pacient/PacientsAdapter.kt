package com.miruna.hospitalmanager.application.pacient

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.pacient_row.view.*

class PacientsAdapter(private val pacients: MutableList<Pacient>) : RecyclerView.Adapter<PacientsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pacient_row, parent, false))

    override fun getItemCount() = pacients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pacient_name.text = pacients[position].name
        holder.pacient_surname.text = pacients[position].surname
        holder.pacient_birthday.text = pacients[position].birthday
        holder.pacient_date_in.text = pacients[position].dateIn
        holder.pacient_date_ex.text = pacients[position].dateEx
    }

    var onItemClick: ((Pacient) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val pacient_name : TextView = itemView.pacient_name
        val pacient_surname : TextView = itemView.surname
        val pacient_birthday : TextView = itemView.birthday
        val pacient_date_in : TextView = itemView.date_in
        val pacient_date_ex : TextView = itemView.date_ex

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(pacients[adapterPosition])
            }
        }
    }
}
