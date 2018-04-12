package com.mrkostua.mathalarm.alarmSettings.optionDeepSleepMusic

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageButton
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import kotlinx.android.synthetic.main.fragment_option_set_deep_wake_up.*
import kotlinx.android.synthetic.main.ringtone_list_row_layout.view.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 2/21/2018.
 */

/**
 * TODO Just ask user if he want this fun to be turn on (explain what it is, how it works)
 */
class FragmentOptionSetDeepWakeUp : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface,
        OptionSetDeepWakeUpContract.View, CompoundButton.OnCheckedChangeListener {

    @Inject
    private lateinit var presenter: OptionSetDeepWakeUpContract.Presenter

    override lateinit var fragmentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_deep_wake_up, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun saveSettingsInSharedPreferences() {
    }

    override fun initializeDependOnViewVariables(view: View?) {
        swDeepWakeUpOption.setOnCheckedChangeListener(this)
        layoutDeepWakeUpPlayer.ibPlayPauseRingtone.setOnClickListener {
            if (isRingtoneImagePlay(it as ImageButton)) {
                it.contentDescription = fragmentContext.resources.getString(R.string.contentDescription_pauseRingtone)
                presenter.playRingtone()

            } else {
                presenter.stopPlayingRingtone()
                it.contentDescription = fragmentContext.resources.getString(R.string.contentDescription_playRingtone)

            }
        }
    }

    private fun isRingtoneImagePlay(view: ImageButton): Boolean =
            view.contentDescription == fragmentContext.resources.getString(R.string.contentDescription_playRingtone)

    override fun initializeDependOnContextVariables(context: Context) {

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        presenter.saveStateInSP(isChecked)
    }


}