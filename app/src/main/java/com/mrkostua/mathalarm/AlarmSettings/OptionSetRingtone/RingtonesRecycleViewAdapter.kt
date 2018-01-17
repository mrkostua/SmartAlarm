package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import com.mrkostua.mathalarm.R


/**
 * @author Kostiantyn Prysiazhnyi on 13.01.2018.
 */
class RingtonesRecycleViewAdapter(private val ringtonesList: ArrayList<String>, private val ringtoneClickListeners: SetRingtoneClickListeners) : RecyclerView.Adapter<RingtonesRecycleViewAdapter.RingtonesViewHolder>() {
    private val TAG = this.javaClass.simpleName
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RingtonesViewHolder {
        return RingtonesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.ringtone_list_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RingtonesViewHolder?, position: Int) {
        setRingtoneNameAndNumber(holder, position)
    }

    override fun getItemCount(): Int {
        return ringtonesList.size
    }

    private fun setRingtoneNameAndNumber(holder: RingtonesViewHolder?, position: Int) {
        holder?.tvRingtoneName?.text = ringtonesList[position]
        holder?.tvRingtoneNumberInList?.text = position.toString() + "."
    }

    inner class RingtonesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ibPlayPauseRingtone = view.findViewById(R.id.ibPlayPauseRingtone) as ImageButton
        private val cbChosenAlarmRingtone = view.findViewById(R.id.cbChosenAlarmRingtone) as CheckBox
        val tvRingtoneName = view.findViewById(R.id.tvRingtoneName) as TextView
        val tvRingtoneNumberInList = view.findViewById(R.id.tvRingtoneNumberInList) as TextView

        init {
            ibPlayPauseRingtone.setOnClickListener({ ibView ->
                ringtoneClickListeners.imageButtonClickListener(ibView, adapterPosition)
            })
            cbChosenAlarmRingtone.setOnCheckedChangeListener({ compoundButton: CompoundButton, isChecked: Boolean ->
                ringtoneClickListeners.checkBoxCheckListener(compoundButton, isChecked, adapterPosition)
            })
        }

        //todo implement onClickListener for Play and Pause button here
        //but first design architecture of play pause stop, set music and appearance of setRingtone layout
        //example : https://stackoverflow.com/questions/30284067/handle-button-click-inside-a-row-in-recyclerview

    }


}