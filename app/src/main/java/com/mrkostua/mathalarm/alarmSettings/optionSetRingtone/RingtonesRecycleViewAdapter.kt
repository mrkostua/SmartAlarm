package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.tools.AlarmTools

/**
 * @author Kostiantyn Prysiazhnyi on 13.01.2018.
 */
class RingtonesRecycleViewAdapter(private val context: Context, private val ringtonesList: ArrayList<RingtoneObject>,
                                  private val ringtoneClickListeners: RingtoneClickListeners)
    : RecyclerView.Adapter<RingtonesRecycleViewAdapter.RingtonesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RingtonesViewHolder {
        return RingtonesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ringtone_list_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RingtonesViewHolder, position: Int) {
        setRingtoneNameAndNumber(holder, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return ringtonesList.size
    }

    inner class RingtonesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ibPlayPauseRingtone = view.findViewById(R.id.ibPlayPauseRingtone) as ImageButton
        val cbChosenAlarmRingtone = view.findViewById(R.id.cbChosenAlarmRingtone) as CheckBox
        val tvRingtoneName = view.findViewById(R.id.tvRingtoneName) as TextView
        val tvRingtoneNumberInList = view.findViewById(R.id.tvRingtoneNumberInList) as TextView

        init {
            ibPlayPauseRingtone.setOnClickListener { ibView ->
                ringtoneClickListeners.imageButtonClickListener(ibView as ImageButton, adapterPosition)
            }
            cbChosenAlarmRingtone.setOnClickListener { cbView ->
                ringtoneClickListeners.checkBoxClickListener(cbView as CheckBox, adapterPosition)
            }
        }
    }

    private fun setRingtoneNameAndNumber(holder: RingtonesViewHolder?, position: Int) {
        holder?.tvRingtoneName?.text = ringtonesList[position].name
        holder?.tvRingtoneNumberInList?.text = String.format("%d" + ".", position)
        holder?.cbChosenAlarmRingtone?.isChecked = ringtonesList[position].isChecked

        if (ringtonesList[position].isPlaying) {
            holder?.ibPlayPauseRingtone?.setImageDrawable(AlarmTools.getDrawable(context.resources, R.drawable.ic_pause_ringtone_48dp))
            holder?.ibPlayPauseRingtone?.contentDescription = context.resources.getString(R.string.contentDescription_pauseRingtone)

        } else {
            holder?.ibPlayPauseRingtone?.setImageDrawable(AlarmTools.getDrawable(context.resources, R.drawable.ic_play_ringtone_48dp))
            holder?.ibPlayPauseRingtone?.contentDescription = context.resources.getString(R.string.contentDescription_playRingtone)

        }
    }

}