package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
class OptionSetRingtonePresenter @Inject constructor(
        private val alarmDataHelper: AlarmDataHelper,
        private val playerHelper: MediaPlayerHelper) : OptionSetRingtoneContract.Presenter {

    override lateinit var ringtonePopulationList: ArrayList<RingtoneObject>
    private val TAG = this.javaClass.simpleName
    private lateinit var optionSetRingtoneView: OptionSetRingtoneContract.View

    init {
        start()
    }

    override fun takeView(view: OptionSetRingtoneContract.View) {
        optionSetRingtoneView = view
    }

    override fun setIsPlayingToFalse(whichIndex: Int) {
        ringtonePopulationList[whichIndex].isPlaying = false

    }

    override fun stopPlayingRingtone() {
        playerHelper.stopPlaying()
    }


    override fun start() {
        // here we can start some transmission of data to View as it run start() in onCreate method
        ringtonePopulationList = alarmDataHelper.getRingtonesForPopulation()
    }


    override fun setCheckedOrPlayingToFalse(actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                      actionSetFalse: (RingtoneObject) -> Unit) {
        ringtonePopulationList.forEachIndexed { index, ringtoneObject ->
            if (actionIsCheckedOrPlaying(ringtoneObject)) {
                actionSetFalse(ringtoneObject)
                optionSetRingtoneView.itemChangedRefreshRecycleView(index)
                return

            }
        }
    }

    override fun setClickedIndexToTrue(actionSetTrue: (RingtoneObject) -> Unit,
                                       actionSetFalse: (RingtoneObject) -> Unit,
                                       actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                       position: Int) {
        ringtonePopulationList.forEachIndexed { index, ringtoneObject ->
            if (index == position) {
                actionSetTrue(ringtoneObject)
                optionSetRingtoneView.itemChangedRefreshRecycleView(position)

            } else if (actionIsCheckedOrPlaying(ringtoneObject)) {
                actionSetFalse(ringtoneObject)
                optionSetRingtoneView.itemChangedRefreshRecycleView(index)

            }
        }
    }

    override fun playChosenRingtone(position: Int) {
        val ringtoneOb = ringtonePopulationList[position]
        playerHelper.playRingtone(ringtoneOb)

    }

    override fun saveChosenRingtoneNameSP(elementIndex: Int) {
        alarmDataHelper.saveRingtoneInSP(ringtonePopulationList[elementIndex].name)

    }

    override fun getSavedRingtonePosition(): RingtoneObject =
            alarmDataHelper.getSavedRingtoneAlarmOb(ringtonePopulationList)

    override fun initializeLastSavedRingtone() {
        val ringtonePosition = ringtonePopulationList.indexOf(getSavedRingtonePosition())
        ringtonePopulationList[ringtonePosition].isChecked = true
        optionSetRingtoneView.itemChangedRefreshRecycleView(ringtonePosition)

    }

    override fun releaseObjects() {
        playerHelper.releaseMediaPlayer()

    }

}