package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.mrkostua.mathalarm.R


/**
 * @author Kostiantyn Prysiazhnyi on 13.01.2018.
 */
class RingtonesRecycleViewAdapter(private val ringtonesList: ArrayList<String>) : RecyclerView.Adapter<RingtonesRecycleViewAdapter.RingtonesViewHolder>() {

    inner class RingtonesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bPlayMusic: Button = view.findViewById(R.id.bPlayMusic2) as Button
        val bPauseMusic: Button = view.findViewById(R.id.bPauseMusic2) as Button
        val tvName: TextView = view.findViewById(R.id.tvName2) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RingtonesViewHolder {
        return RingtonesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.ringtone_list_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RingtonesViewHolder?, position: Int) {
        holder?.bPauseMusic?.setText("Pause")
        holder?.bPlayMusic?.setText("Play")
        holder?.tvName?.setText("Name")
    }

    override fun getItemCount(): Int {
        return ringtonesList.size
    }


}