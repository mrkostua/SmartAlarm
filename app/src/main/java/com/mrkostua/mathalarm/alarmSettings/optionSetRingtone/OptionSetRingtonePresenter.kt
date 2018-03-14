package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import com.mrkostua.mathalarm.data.AlarmDataHelper

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
class OptionSetRingtonePresenter(private val optionSetRingtoneView: OptionSetRingtoneContract.View,
                                 private val alarmDataHelper: AlarmDataHelper,
                                 private val playerHelper: MediaPlayerHelper) : OptionSetRingtoneContract.Presenter {

    override lateinit var ringtonePopulationList: ArrayList<RingtoneObject>

    init {
        optionSetRingtoneView.presenter = this
        start()
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
        if (ringtonePopulationList[position].uri == null) {
            playerHelper.playRingtoneFromStringResource(ringtonePopulationList[position].name)

        } else {
            playerHelper.playRingtoneFromUri(ringtonePopulationList[position].uri!!)

        }
    }

    override fun saveRingtoneFromExternalPath() {
        TODO("not implemented")
    }

    override fun saveChosenRingtone() {

    }

}