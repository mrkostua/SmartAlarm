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
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import com.mrkostua.mathalarm.AlarmSettings.SettingsFragmentInterface
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.fragment_option_set_ringtone.*

/**
 *  @author Kostiantyn Prysiazhnyi on 07-12-17.
 */
class FragmentOptionSetRingtone : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    /**
     *1) todo think about if playing ringtone it will ends so the button need to change to Play back ?
     * we can check with mediaPlayer isPlaying or other way is play music in loop all the time???
     *
     * 2) //todo increase ringtone rating think about it and way to save implement this solution
     *  to solve this for sure we gonna need to save somewhere _rating and _name if the ringtone so in new user session data will be updated.
     *      * So it is SharedPreferences And Set<String> with names and probably one more for Rating.
     *      * Or local SQL like now new library or dependency : Room.
     *  So we gonna user SQL.
     *  //todo create helper for SQL with dependency Room.
     *
     */
    override lateinit var fragmentContext: Context
    private val TAG = this.javaClass.simpleName
    private var positionOfPlayingButtonItem = 0

    private lateinit var ringtonePopulationList: ArrayList<RingtoneObject>
    private lateinit var notificationTools: NotificationTools
    private lateinit var ringtonesRecycleViewAdapter: RingtonesRecycleViewAdapter
    private lateinit var mediaPlayerHelper: MediaPlayerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
        initializeDependOnContextVariables(fragmentContext)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_ringtone, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initializeDependOnViewVariables(view)

    }

    override fun onPause() {
        super.onPause()
        mediaPlayerHelper.stopRingtone()
    }

    override fun onResume() {
        super.onResume()
        ringtonePopulationList.forEachIndexed { index, ringtoneObject ->
            if (index == positionOfPlayingButtonItem) {
                ringtoneObject.isPlaying = false
                return@forEachIndexed
            }
        }
        ringtonesRecycleViewAdapter.notifyItemChanged(positionOfPlayingButtonItem)
    }

    override fun initializeDependOnContextVariables(context: Context) {
        notificationTools = NotificationTools(fragmentContext)
        mediaPlayerHelper = MediaPlayerHelper(fragmentContext)
        ringtonePopulationList = getRingtonesForPopulation()

    }

    override fun initializeDependOnViewVariables(view: View?) {
        rvListOfRingtones.layoutManager = LinearLayoutManager(fragmentContext)
        rvListOfRingtones.itemAnimator = DefaultItemAnimator()
        rvListOfRingtones.addItemDecoration(getCustomDividerItemDecoration())

        ringtonesRecycleViewAdapter = RingtonesRecycleViewAdapter(fragmentContext, ringtonePopulationList, SetRingtoneClickListener())
        rvListOfRingtones.adapter = ringtonesRecycleViewAdapter
    }

    override fun saveSettingsInSharedPreferences() {
        //saving is implemented in OnClickListener for CheckBox and ImageButton
    }

    private fun getCustomDividerItemDecoration(): DividerItemDecoration {
        /**
         * todo Not initialized, for custom divider between list items
         * read some tutorial about RecycleView
         */
        return object : DividerItemDecoration(fragmentContext, LinearLayoutManager.VERTICAL) {
            override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.onDraw(c, parent, state)
            }

            override fun setDrawable(drawable: Drawable) {
                super.setDrawable(drawable)
            }
        }
    }

    private fun isRingtoneImagePlay(view: ImageView): Boolean =
            view.contentDescription == fragmentContext.resources.getString(R.string.contentDescription_playRingtone)

    private fun getRingtonesForPopulation(): ArrayList<RingtoneObject> {
        val ringtonesList = ArrayList<RingtoneObject>()
        ringtonesList.add(RingtoneObject("ringtone_mechanic_clock", 2))
        ringtonesList.add(RingtoneObject("ringtone_energy", 1))
        ringtonesList.add(RingtoneObject("ringtone_loud", 3))
        ringtonesList.addAll(RingtoneManagerHelper.getDefaultAlarmRingtonesList(fragmentContext))

        ringtonesList.sortByDescending { ringtoneObject -> ringtoneObject.rating }
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

    inner class SetRingtoneClickListener : RingtoneClickListeners {

        override fun checkBoxClickListener(view: CheckBox, position: Int) {
            ShowLogs.log(TAG, "getRingtoneClickListeners checkBoxClickListener position: " + position)
            if (view.isChecked) {
                setClickedIndexToTrue({ it.isChecked = true }, { it.isChecked = false },
                        { it.isChecked }, position)

            } else {
                setAllIndexesToFalse({ it.isChecked }, { it.isChecked = false }, position)

            }
        }

        override fun imageButtonClickListener(view: ImageButton, position: Int) {
            ShowLogs.log(TAG, "getRingtoneClickListeners imageButtonClickListener position: " + position)
            when {
                isRingtoneImagePlay(view) -> {
                    positionOfPlayingButtonItem = position
                    if (ringtonePopulationList[position].uri == null) {
                        mediaPlayerHelper.playRingtoneFromStringResource(ringtonePopulationList[position].name)
                    } else {
                        mediaPlayerHelper.playRingtoneFromUri(ringtonePopulationList[position].uri!!)

                    }
                    setClickedIndexToTrue({ it.isPlaying = true }, { it.isPlaying = false },
                            { it.isPlaying }, position)

                }
                !isRingtoneImagePlay(view) -> {
                    mediaPlayerHelper.stopRingtone()
                    setAllIndexesToFalse({ it.isPlaying }, { it.isPlaying = false }, position)
                }
            }
        }

        override fun recycleViewClickListener(view: View, position: Int) {
            //in case of setting clickListener for whole row view
        }

        private inline fun setClickedIndexToTrue(actionSetTrue: (RingtoneObject) -> Unit,
                                                 actionSetFalse: (RingtoneObject) -> Unit,
                                                 actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                                 position: Int) {
            ringtonePopulationList.forEachIndexed { index, ringtoneObject ->
                if (index == position) {
                    actionSetTrue(ringtoneObject)
                    ringtonesRecycleViewAdapter.notifyItemChanged(position)

                } else if (actionIsCheckedOrPlaying(ringtoneObject)) {
                    actionSetFalse(ringtoneObject)
                    ringtonesRecycleViewAdapter.notifyItemChanged(index)

                }
            }
        }

        private inline fun setAllIndexesToFalse(actionIsCheckedOrPlaying: (RingtoneObject) -> Boolean,
                                                actionSetFalse: (RingtoneObject) -> Unit, position: Int) {
            ringtonePopulationList.forEach { ringtoneObject ->
                if (actionIsCheckedOrPlaying(ringtoneObject)) {
                    actionSetFalse(ringtoneObject)
                    ringtonesRecycleViewAdapter.notifyItemChanged(position)
                    return

                }
            }
        }

    }

}

