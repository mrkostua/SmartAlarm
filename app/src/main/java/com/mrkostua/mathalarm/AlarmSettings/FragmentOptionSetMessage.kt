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
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.fragment_option_set_message.*

/**
 * @author Kostiantyn Prysiazhnyi on 08.12.2017.
 */
public class FragmentOptionSetMessage : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    //can be only set after onAttach() otherwise getContext() return null
    override lateinit var fragmentContext: Context

    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = FragmentOptionSetTime::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
        initializeDependOnContextVariables(fragmentContext)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ShowLogs.log(TAG, "onCreateView")

        return inflater?.inflate(R.layout.fragment_option_set_message, container, false)
    }

    override fun initializeDependOnContextVariables(context: Context) {
        sharedPreferences = SharedPreferencesHelper.customSharedPreferences(fragmentContext, PreferencesConstants.ALARM_SP_NAME.getKeyValue())
    }

    override fun initializeDependOnViewVariables(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSettingsInSharedPreferences() {
        //todo set after onclick on confirm or save message button
        sharedPreferences[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue()] = tvTextMessage.text.toString()

    }
    /**
     * TODO simplest plan is to just set text which will appear as a view or some animation at the screen.
     */
}