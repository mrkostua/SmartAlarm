package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
class OptionSetRingtonePresenter(private val optionSetRingtoneView : OptionSetRingtoneContract.View) : OptionSetRingtoneContract.Presenter {
    init {
        optionSetRingtoneView.presenter = this
    }
    override fun start() {
        TODO("here we can start some transmission of data to View as it run start() in onCreate method")
    }



}