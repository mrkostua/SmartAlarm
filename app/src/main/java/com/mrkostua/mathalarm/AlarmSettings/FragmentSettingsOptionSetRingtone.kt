package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R

/**
 * @author k.prysiazhny on 07-Dec-17.
 * k.prysiazhny@samsung.com
 */
public class FragmentSettingsOptionSetRingtone : Fragment(),SettingsFragmentInterface {
    override var settingsOptionIndex: Int = ConstantValues.alarmSettingsOptionsList.indexOf(FragmentSettingsOptionSetRingtone())
    private val TAG = this.javaClass.simpleName


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        saveSettingsInSharedPreferences()

        return inflater.inflate(R.layout.fragment_option_set_ringtone, container, false)
    }


    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 1 need to be some ListView populated by someArrayList of music that can be updated by user
     * 2 we need add methods to play and stop music MainAlarmActivity. AlarmMusic_AlertDialogBuilder method
     * 3 view need to be scrolled
     *
     * it can extend FragmentDialog and try to use setSingleChoiceItems
     *
     * Looks like RecycleView instead of ListView so we gonna learn some more complex approach
     *
     */

    /**
     * todo create some class for RecycleView adapter make it to work with any ListView and any arguments.
     *  Create adapter for RecycleView
     *  https://android.jlelse.eu/recyclerview-listview-basic-comparison-91e844a2fbc4
     *
     *  looks of future layout is in noteBook.
     *  https://www.androidhive.info/2016/01/android-working-with-recycler-view/
     *
     */


    /**
     *
     */


}