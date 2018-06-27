package com.mrkostua.mathalarm.alarmSettings.optionDeepWakeUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.injections.scope.FragmentScope
import com.mrkostua.mathalarm.tools.AlarmTools
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_option_set_deep_wake_up.*
import kotlinx.android.synthetic.main.ringtone_list_row_layout.view.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 2/21/2018.
 */

@FragmentScope
class FragmentOptionSetDeepWakeUp @Inject constructor() : DaggerFragment(), SettingsFragmentInterface, KotlinActivitiesInterface, View.OnClickListener {
    @Inject
    public lateinit var presenter: OptionSetDeepWakeUpContract.Presenter
    override lateinit var fragmentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity!!.applicationContext
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_option_set_deep_wake_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        initializeDependOnViewVariables(view)
        saveSettingsInSharedPreferences()

    }

    override fun onPause() {
        super.onPause()
        presenter.stopPlayingRingtone()
        presenter.releaseObjects()
    }

    override fun initializeDependOnViewVariables(view: View?) {
        swDeepWakeUpOption.isChecked = presenter.getStateFromSP()

        layoutDeepWakeUpPlayer.tvRingtoneName.text = presenter.getDeepWakeUpRingtoneName()
        layoutDeepWakeUpPlayer.ibPlayPauseRingtone.setOnClickListener(this)
    }

    override fun saveSettingsInSharedPreferences() {
        swDeepWakeUpOption.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.saveStateInSP(isChecked)

        }
    }

    override fun onClick(v: View?) {
        when (v) {
            layoutDeepWakeUpPlayer.ibPlayPauseRingtone -> {
                if (AlarmTools.isRingtoneImagePlay(fragmentContext, v as ImageButton)) {
                    v.contentDescription = fragmentContext.resources.getString(R.string.contentDescription_pauseRingtone)
                    v.setImageDrawable(AlarmTools.getDrawable(fragmentContext.resources, R.drawable.ic_pause_ringtone_48dp))
                    presenter.playRingtone()

                } else {
                    presenter.stopPlayingRingtone()
                    v.setImageDrawable(AlarmTools.getDrawable(fragmentContext.resources, R.drawable.ic_play_ringtone_48dp))
                    v.contentDescription = fragmentContext.resources.getString(R.string.contentDescription_playRingtone)

                }
            }
        }

    }

    override fun initializeDependOnContextVariables(context: Context) {
    }

}