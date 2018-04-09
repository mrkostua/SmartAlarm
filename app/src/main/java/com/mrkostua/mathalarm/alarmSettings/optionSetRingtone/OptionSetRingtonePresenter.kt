package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.tools.ShowLogs
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
        ringtonePopulationList.forEachIndexed { index, ringtoneObject ->
            if (index == whichIndex) {
                ringtoneObject.isPlaying = false
                return@forEachIndexed
            }
        }
    }

    override fun stopPlayingRingtone() {
        playerHelper.stopRingtone()
    }

    override fun start() {
        ringtonePopulationList = alarmDataHelper.getRingtonesForPopulation()
        // TODO here we can start some transmission of data to View as it run start() in onCreate method
    }


    override fun setAllIndexesToFalse(actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                      actionSetFalse: (RingtoneObject) -> Unit, position: Int) {
        ringtonePopulationList.forEach { ringtoneObject ->
            if (actionIsCheckedOrPlaying(ringtoneObject)) {
                actionSetFalse(ringtoneObject)
                optionSetRingtoneView.itemChangedRefreshRecycleView(position)
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
                optionSetRingtoneView.itemChangedRefreshRecycleView(position)

            }
        }
    }

    override fun playChosenRingtone(position: Int) {
        val ringtoneOb = ringtonePopulationList[position]
        if (ringtoneOb.uri == null) {
            playerHelper.playRingtoneFromStringResource(ringtoneOb.name)

        } else {
            playerHelper.playRingtoneFromUri(ringtoneOb.uri)

        }
    }

    override fun saveRingtoneFromExternalPath() {
        TODO("not implemented")
    }

    override fun saveChosenRingtoneNameSP(elementIndex: Int) {
        alarmDataHelper.saveRingtoneInSP(ringtonePopulationList[elementIndex].name)

    }

    override fun getSavedRingtonePosition(): RingtoneObject =
            alarmDataHelper.getSavedRingtoneAlarmObject(ringtonePopulationList)

    override fun initializeLastSavedRingtone() {
        val ringtonePosition = ringtonePopulationList.indexOf(getSavedRingtonePosition())
        ringtonePopulationList[ringtonePosition].isChecked = true
        optionSetRingtoneView.itemChangedRefreshRecycleView(ringtonePosition)
        ShowLogs.log(TAG, "initializeLastSavedRingtone  ringtone position : " + ringtonePosition)

    }

    override fun releaseObjects() {
        playerHelper.releaseMediaPlayer()

    }

}