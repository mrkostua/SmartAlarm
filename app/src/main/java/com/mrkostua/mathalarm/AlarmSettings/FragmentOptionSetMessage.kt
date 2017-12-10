package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.AlarmObject
import com.mrkostua.mathalarm.PreferenceHelper.defaultPrefs
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.R

import kotlinx.android.synthetic.main.fragment_option_set_message.*

/**
 * @author Kostiantyn Prysiazhnyi on 08.12.2017.
 */
public class FragmentOptionSetMessage : Fragment(), SettingsFragmentInterface {
    private val activityContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        this.context
    else
        activity
//    private val sharedPreferences = SharedPreferencesHelper.customSharedPreferences(activityContext,SharedPreferencesConstants.ALARM_SP_NAME.getKeyValue())

    override var settingsOptionIndex: Int = ConstantValues.alarmSettingsOptionsList.indexOf(FragmentOptionSetTime())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        return inflater.inflate(R.layout.fragment_option_set_message, container, false)
    }

    override fun saveSettingsInSharedPreferences() {
        //todo set after onclick on confirm or save message button
        val alarmObject = AlarmObject(tvTextMessage.text.toString())


    }
}