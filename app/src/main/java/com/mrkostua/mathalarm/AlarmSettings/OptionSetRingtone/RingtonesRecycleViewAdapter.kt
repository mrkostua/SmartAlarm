package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.AlarmTools
import com.mrkostua.mathalarm.Tools.ShowLogs


/**
 * @author Kostiantyn Prysiazhnyi on 13.01.2018.
 */
class RingtonesRecycleViewAdapter(private val context: Context, private val ringtonesList: ArrayList<RingtoneObject>,
                                  private val ringtoneClickListeners: RingtoneClickListeners)
    : RecyclerView.Adapter<RingtonesRecycleViewAdapter.RingtonesViewHolder>() {

    private val TAG = this.javaClass.simpleName
    private var isBind = false

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RingtonesViewHolder {
        return RingtonesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.ringtone_list_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RingtonesViewHolder?, position: Int) {
        isBind = false
        setRingtoneNameAndNumber(holder, holder?.adapterPosition ?: position)
        isBind = true
    }

    override fun getItemCount(): Int {
        return ringtonesList.size
    }

    fun updateRingtoneData(ringtonesList: ArrayList<RingtoneObject>, position: Int) {
        if (isBind && !ringtonesList.isEmpty()) {
            this.ringtonesList.clear()
            this.ringtonesList.addAll(ringtonesList)

            notifyItemChanged(position)
        }
    }

    inner class RingtonesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ibPlayPauseRingtone = view.findViewById(R.id.ibPlayPauseRingtone) as ImageButton
        val cbChosenAlarmRingtone = view.findViewById(R.id.cbChosenAlarmRingtone) as CheckBox
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

    private fun setRingtoneNameAndNumber(holder: RingtonesViewHolder?, position: Int) {
        holder?.tvRingtoneName?.text = ringtonesList[position].name
        holder?.tvRingtoneNumberInList?.text = Integer.toString(position) + "."

        if (ringtonesList[position].isPlaying) {
            ShowLogs.log(TAG, "setRingtoneNameAndNumber :  posit : " + position + "Playing true")
            holder?.ibPlayPauseRingtone?.setImageDrawable(AlarmTools.getDrawable(context.resources, R.drawable.ic_alarm_off_white_36dp))
        } else {
            holder?.ibPlayPauseRingtone?.setImageDrawable(AlarmTools.getDrawable(context.resources, R.mipmap.play_music))
        }
        if (ringtonesList[position].isChecked) {
            ShowLogs.log(TAG, "setRingtoneNameAndNumber :  posit : " + position + "Checked true")
            holder?.cbChosenAlarmRingtone?.isChecked = true
        } else {
            holder?.cbChosenAlarmRingtone?.isChecked = false
        }
    }


}