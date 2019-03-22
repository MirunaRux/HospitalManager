package com.miruna.hospitalmanager.application.request

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.request_row.view.*

class RequestsAdapter (private val requests: MutableList<Request>) : RecyclerView.Adapter<RequestsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.request_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = requests.size

    var onItemClick: ((Request) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.request_id.text = requests[position].id.toString()
        holder.request_drug_name.text = requests[position].drugName
        holder.request_id.text = requests[position].cantity.toString()

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val request_id : TextView = itemView.request_id
        val request_drug_name : TextView = itemView.request_drug_name
        val request_cantity : TextView = itemView.request_cantity

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(requests[adapterPosition])
            }
        }
    }
}