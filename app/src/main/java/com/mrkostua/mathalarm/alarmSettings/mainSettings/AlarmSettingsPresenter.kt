package com.mrkostua.mathalarm.alarmSettings.mainSettings

import com.mrkostua.mathalarm.tools.AlarmTools
import com.mrkostua.mathalarm.tools.ShowLogs
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/19/2018.
 */
class AlarmSettingsPresenter @Inject constructor(private var fragmentToLoadFirst: AlarmSettingsNames,
                                                 private val alarmSettingsView: AlarmSettingsContract.View)
    : AlarmSettingsContract.Presenter {
    private val TAG = this.javaClass.simpleName

    override fun takeView(view: AlarmSettingsContract.View) {
        //view is inserted using Injection to Presenter constructor
    }

    override fun start() {
    }

    override fun showChosenFragment(fragmentIndex: Int) {
        if (fragmentToLoadFirst == AlarmSettingsNames.OPTION_WRONG) {
            fragmentToLoadFirst = AlarmSettingsNames.OPTION_SET_TIME.getAlarmSettingName(fragmentIndex)

        }
        ShowLogs.log(TAG,"showChosenFragment ${fragmentToLoadFirst.getKeyValue()}")
        alarmSettingsView.loadChosenFragment(fragmentToLoadFirst)
        alarmSettingsView.blockInitialButton(fragmentToLoadFirst)

    }

    override fun showNextPreviousFragment(isNextFragment: Boolean) {
        val currentFragmentIndex = alarmSettingsView.getCurrentFragmentIndex()
        when (currentFragmentIndex) {
            0 -> {
                alarmSettingsView.moveToNextFragment(currentFragmentIndex)

            }
            in 1 until AlarmTools.getLastFragmentIndex() -> {
                if (isNextFragment) {
                    alarmSettingsView.moveToNextFragment(currentFragmentIndex)
                } else {
                    alarmSettingsView.moveToPreviousFragment(currentFragmentIndex)
                }

            }
            AlarmTools.getLastFragmentIndex() -> {
                alarmSettingsView.moveToPreviousFragment(currentFragmentIndex)

            }
            else -> {
                ShowLogs.log(TAG, " showNextPreviousFragment wrong index of currently showing fragment")
                alarmSettingsView.moveToMainActivity()
            }
        }

    }

    override fun isLastSettingsFragment(whichFragmentIndex: Int): Boolean =
            alarmSettingsView.getCurrentFragmentIndex() + whichFragmentIndex == AlarmTools.getLastFragmentIndex()

    override fun isFirstSettingsFragment(whichFragmentIndex: Int): Boolean =
            alarmSettingsView.getCurrentFragmentIndex() - whichFragmentIndex == 0

}