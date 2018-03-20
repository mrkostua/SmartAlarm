package com.mrkostua.mathalarm.alarmSettings.mainSettings

import com.mrkostua.mathalarm.Interfaces.BasePresenter
import com.mrkostua.mathalarm.Interfaces.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 3/19/2018.
 */
interface AlarmSettingsContract {
    interface View : BaseView<Presenter> {
        fun blockInitialButton(loadedFragmentName: AlarmSettingsNames)
        fun moveToMainActivity()
        fun getCurrentFragmentIndex(): Int
        fun moveToNextFragment(currentFragmentIndex: Int)
        fun moveToPreviousFragment(currentFragmentIndex: Int)
        fun loadChosenFragment(fragmentName: AlarmSettingsNames)
    }

    interface Presenter : BasePresenter<View> {
        fun showChosenFragment(fragmentIndex: Int)
        fun showNextPreviousFragment(isNextFragment: Boolean)

        /**
         * @param whichFragmentIndex used for cases when new fragment is still loading
         * but gerCurrentFragmentIndex() returns previous (old one).
         */
        fun isLastSettingsFragment(whichFragmentIndex: Int = 0): Boolean

        fun isFirstSettingsFragment(whichFragmentIndex: Int = 0): Boolean
    }

}