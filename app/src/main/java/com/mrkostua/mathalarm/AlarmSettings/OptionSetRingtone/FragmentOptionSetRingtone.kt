package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.app.Fragment
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import com.mrkostua.mathalarm.AlarmSettings.SettingsFragmentInterface
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.AlarmTools
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

    private lateinit var ringtonesRecycleViewAdapter: RingtonesRecycleViewAdapter
    private val ringtonesList = ArrayList<RingtoneObject>()


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
        notificationTools = NotificationTools(fragmentContext)
        initializeRecycleView(fragmentContext)
    }

    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initializeRecycleView(context: Context) {
        rvListOfRingtones.layoutManager = LinearLayoutManager(context)
        rvListOfRingtones.itemAnimator = DefaultItemAnimator()
        rvListOfRingtones.addItemDecoration(getCustomDividerItemDecoration())

        ringtonesRecycleViewAdapter = RingtonesRecycleViewAdapter(context, listGet(), getRingtoneClickListeners())
        rvListOfRingtones.adapter = ringtonesRecycleViewAdapter
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
            override fun checkBoxClickListener(view: CheckBox, position: Int) {
                ShowLogs.log(TAG, "getRingtoneClickListeners checkBoxClickListener position: " + position)
                if (view.isChecked) {
                    ringtonesList.forEachIndexed { index, ringtoneObject ->
                        if (index == position) {
                            ringtoneObject.isChecked = true
                            ringtonesRecycleViewAdapter.notifyItemChanged(position)

                        } else if (ringtoneObject.isChecked) {
                            ringtoneObject.isChecked = false
                            ringtonesRecycleViewAdapter.notifyItemChanged(index)

                        }
                    }
                } else {
                    run loopBreaker@ {
                        ringtonesList.forEach { ringtoneObject ->
                            if (ringtoneObject.isChecked) {
                                ringtoneObject.isChecked = false
                                ringtonesRecycleViewAdapter.notifyItemChanged(position)
                                return@loopBreaker
                            }
                        }
                    }

                }
            }

            override fun imageButtonClickListener(view: ImageButton, position: Int) {
                ShowLogs.log(TAG, "getRingtoneClickListeners imageButtonClickListener position: " + position)
                if (isRingtoneIconPlay(view)) {
                    ShowLogs.log(TAG, "imageButtonClickListener isRingtoneIcon Play true")
                    ringtonesList.forEachIndexed { index, ringtoneObject ->
                        if (index == position) {
                            ringtoneObject.isPlaying = true
                            ringtonesRecycleViewAdapter.notifyItemChanged(position)

                        } else if (ringtoneObject.isPlaying) {
                            ringtoneObject.isPlaying = false
                            ringtonesRecycleViewAdapter.notifyItemChanged(index)

                        }
                    }
                } else {
                    ShowLogs.log(TAG, "imageButtonClickListener isRingtoneIcon Play false")

                    run loopBreaker@ {
                        ringtonesList.forEach { ringtoneObject ->
                            if (ringtoneObject.isPlaying) {
                                ringtoneObject.isPlaying = false
                                ringtonesRecycleViewAdapter.notifyItemChanged(position)
                                return@loopBreaker
                            }
                        }
                    }
                }
                //todo start playing or stopping
                //todo changing icon from play to stop
                //todo block other buttons from being pushed
            }

            override fun recycleViewClickListener(view: View, position: Int) {
                //in case of setting clickListener for whole row view
            }
        }
    }
    //todo find better way for comparing drawables!!!
    private fun isRingtoneIconPlay(view: ImageView): Boolean {
        val bitmap = (view.drawable as BitmapDrawable).bitmap
        val bitmap2 = (AlarmTools.getDrawable(fragmentContext.resources, R.mipmap.ic_play_ringtone) as BitmapDrawable).bitmap
        return bitmap == bitmap2
    }


    private fun listGet(): ArrayList<RingtoneObject> {
        ringtonesList.add(RingtoneObject("-2Guitar song"))
        ringtonesList.add(RingtoneObject("-1Guitar song", 1, false, false))
        ringtonesList.add(RingtoneObject("0Guitar song", 2, false, true))
        ringtonesList.add(RingtoneObject("1Adele Hello "))
        ringtonesList.add(RingtoneObject("2Adele Hello "))
        ringtonesList.add(RingtoneObject("3Adele Hello "))
        ringtonesList.add(RingtoneObject("4Adele Hello "))
        ringtonesList.add(RingtoneObject("5Adele Hello "))
        ringtonesList.add(RingtoneObject("6Adele Hello "))
        ringtonesList.add(RingtoneObject("7Adele Hello "))
        ringtonesList.add(RingtoneObject("8Adele Hello "))
        ringtonesList.add(RingtoneObject("12Adele Hello "))
        ringtonesList.add(RingtoneObject("13Adele Hello "))
        ringtonesList.add(RingtoneObject("14Adele Hello "))
        ringtonesList.add(RingtoneObject("15Adele Hello "))
        ringtonesList.add(RingtoneObject("16Adele Hello "))
        ringtonesList.add(RingtoneObject("17Adele Hello "))
        ringtonesList.add(RingtoneObject("18Adele Hello "))
        ringtonesList.add(RingtoneObject("19Adele Hello "))
        return ringtonesList
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

