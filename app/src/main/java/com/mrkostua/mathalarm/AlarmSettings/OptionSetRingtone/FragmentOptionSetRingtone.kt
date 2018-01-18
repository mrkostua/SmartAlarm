package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.app.Fragment
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.mrkostua.mathalarm.AlarmSettings.SettingsFragmentInterface
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.fragment_option_set_ringtone.*

/**
 *  @author Kostiantyn Prysiazhnyi on 07-12-17.
 */
public class FragmentOptionSetRingtone : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    override lateinit var fragmentContext: Context
    private val TAG = this.javaClass.simpleName
    private lateinit var notificationTools: NotificationTools

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
        notificationTools = NotificationTools(fragmentContext)
    }

    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initializeRecycleView(context: Context) {
        rvListOfRingtones.layoutManager = LinearLayoutManager(context)
        rvListOfRingtones.itemAnimator = DefaultItemAnimator()
        rvListOfRingtones.addItemDecoration(getCustomDividerItemDecoration())
        rvListOfRingtones.adapter = RingtonesRecycleViewAdapter(context, listGet(), getRingtoneClickListeners())
        //todo maybe set adapter once more after changing icon checkBox
        //todo think about fixing the problem with checkBox one checked and one random checkBox from the list also begging to be checked
    }

    private fun getCustomDividerItemDecoration(): DividerItemDecoration {
        return object : DividerItemDecoration(fragmentContext, LinearLayoutManager.VERTICAL) {
            override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.onDraw(c, parent, state)
            }

            override fun setDrawable(drawable: Drawable) {
                super.setDrawable(drawable)
            }
        }
    }

    private fun getRingtoneClickListeners(): RingtoneClickListeners {
        return object : RingtoneClickListeners {
            override fun recycleViewClickListener(view: View, position: Int) {
                //in case of setting clickListener for whole row view
            }

            override fun imageButtonClickListener(view: View, position: Int) {
                ShowLogs.log(TAG, "imageButtonClickListener : " + position)

                //todo start playing or stopping
                //todo changing icon from play to stop
                //todo block other buttons from being pushed
            }

            override fun checkBoxCheckListener(compoundButton: CompoundButton, isChecked: Boolean, position: Int) {
                ShowLogs.log(TAG, "checkBoxCheckListener : " + position)
                //todo only one checkBox can be checked (method for it) if new one checked, last one checked changing to unchecked
                //todo show Toast with music name and explanation inf.

            }
        }
    }

    private fun listGet(): ArrayList<RingtoneObject> {
        val list = ArrayList<RingtoneObject>()
        list.add(2, RingtoneObject("Guitar song", 2))
        list.add(1, RingtoneObject("Guitar song", 1))
        list.add(3, RingtoneObject("Guitar song", 3))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        list.add(RingtoneObject("Adele Hello "))
        return list
    }

    //todo also consider creating ringtone object with name, author, rating(how often user choose this item) etc.
    private fun getDefaultPhoneRingtonesList(): List<Int> {
        TODO("not implemented")

    }

    private fun addRingtoneFromExternalPath(): Int {
        TODO("not implemented")
        /**
         * return the path to this file and probably this list need to be saved somewhere (local DB ) SharedPreferences
         *  1 need to be some ListView populated by some ArrayList of music that can be updated by user
         * todo user can click the button and search throw files tree to music location and by choosing ->
         * file with proper type it will be added to the list of ringtone's.
         */
    }
}

