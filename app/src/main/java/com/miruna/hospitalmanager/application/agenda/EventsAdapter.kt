package com.miruna.hospitalmanager.application.agenda

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.event_row.view.*

class EventsAdapter (private val events: MutableList<Event>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = events.size

    var onItemClick: ((Event) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.event_name.text = events[position].name
        holder.location.text = events[position].location
        holder.pacient.text = events[position].pacientName
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val event_name : TextView = itemView.event_name
        val location : TextView = itemView.location
        val pacient : TextView = itemView.pacient

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(events[adapterPosition])
            }
        }
    }
}