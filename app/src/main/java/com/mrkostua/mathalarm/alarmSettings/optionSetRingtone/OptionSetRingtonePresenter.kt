package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
class OptionSetRingtonePresenter(private val optionSetRingtoneView: OptionSetRingtoneContract.View, private val playerHelper: MediaPlayerHelper) : OptionSetRingtoneContract.Presenter {
    override var positionOfPlayingButtonItem: Int = 0

    override fun setIsPlayingToFalse(ringtoneList: ArrayList<RingtoneObject>): ArrayList<RingtoneObject> {
        ringtoneList.forEachIndexed { index, ringtoneObject ->
            if (index == positionOfPlayingButtonItem) {
                ringtoneObject.isPlaying = false
                return@forEachIndexed
            }
        }
        return ringtoneList
    }

    override fun stopPlayingRingtone() {
        playerHelper.stopRingtone()
    }

    init {
        optionSetRingtoneView.presenter = this
    }

    override fun start() {
        TODO("here we can start some transmission of data to View as it run start() in onCreate method")
    }


}