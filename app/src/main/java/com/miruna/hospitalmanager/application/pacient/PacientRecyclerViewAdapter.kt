package com.example.mirunabudoias.myfirstapplication.user

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.pacient.Pacient
import com.miruna.hospitalmanager.application.pacient.PacientsFragment


class PacientRecyclerViewAdapter(
    private var pacientList: List<Pacient>?,
    private val mListener: PacientsFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PacientRecyclerViewAdapter.ViewHolder>() {

    fun setData(userList: List<Pacient>) {
        this.pacientList = pacientList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pacients_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(pacientList!![position])
    }

    override fun getItemCount(): Int {
        return pacientList!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvName: TextView
        val tvSurname: TextView
        lateinit var currentPacient: Pacient

        init {
            tvName = mView.findViewById(R.id.tv_item_user_name) as TextView
            tvSurname = mView.findViewById(R.id.tv_item_user_surname) as TextView

            mView.setOnClickListener {
                if (null != mListener) {
                    mListener!!.onListFragmentInteraction(pacientList!![adapterPosition], adapterPosition)
                }
            }
        }

        fun onBind(user: Pacient) {
            currentPacient = user
            tvName.setText(currentPacient.name)
            tvSurname.setText(currentPacient.surname)
        }

        override fun toString(): String {
            return super.toString() + " '" + tvSurname.text + "'"
        }
    }
}
