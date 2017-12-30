package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.set
import kotlinx.android.synthetic.main.fragment_option_set_message.*

/**
 * @author Kostiantyn Prysiazhnyi on 08.12.2017.
 */
public class FragmentOptionSetMessage : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    override lateinit var fragmentContext: Context

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        return inflater.inflate(R.layout.fragment_option_set_message, container, false)
    }

    override fun initializeDependOnContextVariables() {
        fragmentContext = /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.context
        else*/
            activity.applicationContext

        sharedPreferences = SharedPreferencesHelper.customSharedPreferences(fragmentContext, PreferencesConstants.ALARM_SP_NAME.getKeyValue())
    }

    override fun saveSettingsInSharedPreferences() {
        //todo set after onclick on confirm or save message button
        sharedPreferences[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue()] = tvTextMessage.text.toString()

    }
}