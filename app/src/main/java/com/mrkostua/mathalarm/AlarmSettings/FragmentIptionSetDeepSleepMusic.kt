package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R

/**
 * @author Kostiantyn Prysiazhnyi on 2/21/2018.
 */


/**
 * TODO Just ask user if he want this fun to be turn on (explain what it is,how it works)
 */
class FragmentIptionSetDeepSleepMusic : Fragment(), KotlinActivitiesInterface, SettingsFragmentInterface {
    override lateinit var fragmentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
        initializeDependOnContextVariables(fragmentContext)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_deep_sleep_music, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeDependOnViewVariables(view)
    }

    override fun initializeDependOnContextVariables(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initializeDependOnViewVariables(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}