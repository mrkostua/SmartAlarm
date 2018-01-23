package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.app.Fragment
import android.content.Context
import android.graphics.Bitmap
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
class FragmentOptionSetRingtone : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    private val TAG = this.javaClass.simpleName
    private val ringtonesList = ArrayList<RingtoneObject>()
    override lateinit var fragmentContext: Context

    private lateinit var notificationTools: NotificationTools
    private lateinit var ringtonesRecycleViewAdapter: RingtonesRecycleViewAdapter
    private lateinit var bitmapPlayRingtoneForComparing: Bitmap
    private lateinit var mediaPlayerHelper: MediaPlayerHelper

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_ringtone, container, false)
    }

    //todo consider making some variables as singleton check if it null create new and otherwise
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        initializeDependOnContextVariables()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerHelper.stopRingtone()
    }

    override fun initializeDependOnContextVariables() {
        fragmentContext = activity.applicationContext
        notificationTools = NotificationTools(fragmentContext)
        mediaPlayerHelper = MediaPlayerHelper(fragmentContext)
        bitmapPlayRingtoneForComparing = (AlarmTools.getDrawable(fragmentContext.resources, R.drawable.ic_play_ringtone_48dp) as BitmapDrawable).bitmap
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
    }

    /**
     * Not initialized, for custom divider between list items
     */
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

            //todo think about putting similar code into one method for checkBoxClickListener and imageButtonClickListener
            override fun imageButtonClickListener(view: ImageButton, position: Int) {
                ShowLogs.log(TAG, "getRingtoneClickListeners imageButtonClickListener position: " + position)
                if (isRingtoneImagePlay(view)) {
                    ShowLogs.log(TAG, "imageButtonClickListener isRingtoneIcon Play true")
                    if (ringtonesList[position].uri == null) {
                        mediaPlayerHelper.playRingtoneFromStringResource(ringtonesList[position].name)

                    } else {
                        mediaPlayerHelper.playRingtoneFromUri(ringtonesList[position].uri!!)

                    }
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
                    mediaPlayerHelper.stopRingtone()
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
            }

            override fun recycleViewClickListener(view: View, position: Int) {
                //in case of setting clickListener for whole row view
            }
        }
    }

    /**
     * Think about it is really in need ?
     * todo We have two ways for storing ringtones data (1 Sql or SharedPreferences)
    Answer is to use Sql -> Room Persistence Library
    update project gradle and other things and follow steps in this link to create ease DB
    https://developer.android.com/training/data-storage/room/index.html
     */


    //todo find better way for comparing drawables!!! (is button Play or Pause?)
    private fun isRingtoneImagePlay(view: ImageView): Boolean =
            (view.drawable as BitmapDrawable).bitmap == bitmapPlayRingtoneForComparing


    private fun listGet(): ArrayList<RingtoneObject> {
        ringtonesList.clear()
        ringtonesList.add(RingtoneObject("ringtone_mechanic_clock", 2))
        ringtonesList.add(RingtoneObject("ringtone_energy", 1))
        ringtonesList.add(RingtoneObject("ringtone_loud", 3))
        ringtonesList.addAll(RingtoneManagerHelper(fragmentContext).getDefaultAlarmRingtonesList())

        ringtonesList.sortBy { ringtoneObject -> ringtoneObject.rating }
        return ringtonesList
    }

    private fun addRingtoneFromExternalPath(): Int {

        TODO("not implemented")
        /**
         * return the path to this file and probably this list need to be saved somewhere (local DB ) SharedPreferences
         *  1 need to be some ListView populated by some ArrayList of music that can be updated by user
         * todo user can click the button and search throw files tree to music location and by choosing ->
         * file with proper type it will be added to the list of ringtone's.
         */


        //todo so by adding some String path with name it need to be unique (because SharedPreferences can contain only Set<String>
        //check by some filter of List or anything else
    }
    //todo increase ringtone rating if it was set (in sql after starting alarm not in Settings)
}

