package com.miruna.hospitalmanager.application.pacient.file

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.file_row.view.*

class FilesAdapter (private val files: MutableList<File>) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = files.size

    var onItemClick: ((File) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.file_id.text = "File " + files[position].id
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val file_id :TextView = itemView.file_id

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(files[adapterPosition])
            }
        }
    }

}
