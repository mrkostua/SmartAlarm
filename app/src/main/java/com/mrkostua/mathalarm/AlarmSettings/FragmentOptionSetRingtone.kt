package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone.RingtonesRecycleViewAdapter
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.fragment_option_set_ringtone.*

/**
 *  @author Kostiantyn Prysiazhnyi on 07-12-17.
 */
public class FragmentOptionSetRingtone : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    override lateinit var fragmentContext: Context
    private val TAG = this.javaClass.simpleName
    private lateinit var ringtonesAdapter: RingtonesRecycleViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_ringtone, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        initializeDependOnContextVariables()
    }

    override fun initializeDependOnContextVariables() {
        fragmentContext = activity.applicationContext
        initializeRecycleView(fragmentContext)
    }

    private fun initializeRecycleView(context: Context) {
        ringtonesAdapter = RingtonesRecycleViewAdapter(listGet())
        val layoutManager = LinearLayoutManager(context)
        rvListOfRingtones.layoutManager = layoutManager
        rvListOfRingtones.itemAnimator = DefaultItemAnimator()
        rvListOfRingtones.adapter = ringtonesAdapter
    }

    private fun listGet(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        return list
    }

    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 1 need to be some ListView populated by some ArrayList of music that can be updated by user
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

    //todo also consider creating ringtone object with name, author, rating(how often user choose this item) etc.

    private fun getDefaultPhoneRingtonesList(): List<Int> {
        TODO("not implemented")
    }

    private fun addRingtoneFromExternalPath() {
        TODO("not implemented")
        /**
         * todo user can click the button and search throw files tree to music location and by choosing ->
         * file with proper type it will be added to the list of ringtone's.
         */
    }
}

