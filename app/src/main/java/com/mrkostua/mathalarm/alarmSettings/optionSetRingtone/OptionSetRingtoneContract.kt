package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import com.mrkostua.mathalarm.Interfaces.BasePresenter
import com.mrkostua.mathalarm.Interfaces.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
interface OptionSetRingtoneContract {

    interface View : BaseView<Presenter> {
        var positionOfPlayingButtonItem: Int
        fun itemChangedRefreshRecycleView(itemPosition: Int)
    }

    interface Presenter : BasePresenter<View> {
        val ringtonePopulationList: ArrayList<RingtoneObject>
        fun setIsPlayingToFalse(whichIndex: Int)
        fun stopPlayingRingtone()

        fun setCheckedOrPlayingToFalse(actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                 actionSetFalse: (RingtoneObject) -> Unit)

        fun setClickedIndexToTrue(actionSetTrue: (RingtoneObject) -> Unit,
                                  actionSetFalse: (RingtoneObject) -> Unit,
                                  actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                  position: Int)

        fun playChosenRingtone(position: Int)

        fun saveChosenRingtoneNameSP(elementIndex : Int)

        fun getSavedRingtonePosition() : RingtoneObject

        fun initializeLastSavedRingtone()

        fun releaseObjects()

    }
}