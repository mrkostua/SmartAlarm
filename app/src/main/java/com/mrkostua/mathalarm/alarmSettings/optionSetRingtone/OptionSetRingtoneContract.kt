package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import com.mrkostua.mathalarm.Interfaces.BasePresenter
import com.mrkostua.mathalarm.Interfaces.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
interface OptionSetRingtoneContract {

    interface View : BaseView<Presenter> {
        fun itemChangedRefreshRecycleView()
    }

    interface Presenter : BasePresenter {
         var positionOfPlayingButtonItem : Int
        fun setIsPlayingToFalse(ringtoneList: ArrayList<RingtoneObject>): ArrayList<RingtoneObject>
        fun stopPlayingRingtone()
    }
}