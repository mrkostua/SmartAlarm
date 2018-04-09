package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.injections.scope.FragmentScope
import com.mrkostua.mathalarm.tools.ShowLogs
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_option_set_ringtone.*
import javax.inject.Inject

/**
 *  @author Kostiantyn Prysiazhnyi on 07-12-17.
 */
@FragmentScope
class FragmentOptionSetRingtone @Inject constructor() : DaggerFragment(), SettingsFragmentInterface, KotlinActivitiesInterface, OptionSetRingtoneContract.View {
    private val TAG = this.javaClass.simpleName
    private lateinit var ringtonesRecycleViewAdapter: RingtonesRecycleViewAdapter
    override lateinit var fragmentContext: Context
    override var positionOfPlayingButtonItem: Int = 0

    @Inject
    public lateinit var presenter: OptionSetRingtoneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        fragmentContext = activity!!.applicationContext
        initializeDependOnContextVariables(fragmentContext)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_ringtone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
        initializeDependOnViewVariables(view)
        presenter.initializeLastSavedRingtone()

    }

    override fun onPause() {
        super.onPause()
        presenter.stopPlayingRingtone()
        presenter.releaseObjects()
    }

    override fun onResume() {
        super.onResume()
        presenter.setIsPlayingToFalse(positionOfPlayingButtonItem)
        itemChangedRefreshRecycleView(positionOfPlayingButtonItem)
    }

    override fun itemChangedRefreshRecycleView(itemPosition: Int) {
        ShowLogs.log(TAG, "itemChangedRefreshRecycleView position : " + positionOfPlayingButtonItem)
        ringtonesRecycleViewAdapter.notifyItemChanged(itemPosition)
        // TODO fix problem with notify changes (calling this fun from Presenter
    }

    override fun initializeDependOnContextVariables(context: Context) {
    }

    override fun initializeDependOnViewVariables(view: View?) {
        rvListOfRingtones.layoutManager = LinearLayoutManager(fragmentContext)
        rvListOfRingtones.itemAnimator = DefaultItemAnimator()
        rvListOfRingtones.addItemDecoration(DividerItemDecoration(fragmentContext, LinearLayoutManager.VERTICAL))

        ringtonesRecycleViewAdapter = RingtonesRecycleViewAdapter(fragmentContext, presenter.ringtonePopulationList, SetRingtoneClickListener())
        rvListOfRingtones.adapter = ringtonesRecycleViewAdapter

    }

    override fun saveSettingsInSharedPreferences() {
        //saving is implemented in OnClickListener for CheckBox and ImageButton
    }

    //TODO add button so user can trigger Presenter fun for adding ringtoneFromEP and after ringtone uri will be saved in Model DB
    private fun addRingtoneFromExternalPath(): Int {
        val openFileActionIntent: Intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            openFileActionIntent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MUSIC)
            openFileActionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } else {
            openFileActionIntent = Intent("android.intent.action.MUSIC_PLAYER")
        }

        presenter.saveRingtoneFromExternalPath()
        startActivity(openFileActionIntent)

        //TODO to do it faster we can just add one ringtone and save in SharedPreferences so one last added ringtone will be available all the time or SharedPreferences Set<String>
        /**
         * return the path to this file and probably this list need to be saved somewhere (local DB ) SharedPreferences
         *  1 need to be some ListView populated by some ArrayList of music that can be updated by user
         * todo user can click the button and search throw files tree to music location and by choosing ->
         * file with proper type it will be added to the list of ringtone's.
         */

        return 0
    }

    inner class SetRingtoneClickListener : RingtoneClickListeners {
        override fun checkBoxClickListener(view: CheckBox, position: Int) {
            ShowLogs.log(TAG, "getRingtoneClickListeners checkBoxClickListener position: " + position)
            if (view.isChecked) {
                presenter.setClickedIndexToTrue({ it.isChecked = true }, { it.isChecked = false },
                        { it.isChecked }, position)
                presenter.saveChosenRingtoneNameSP(position)

            } else {
                presenter.setAllIndexesToFalse({ it.isChecked }, { it.isChecked = false }, position)

            }
        }

        override fun imageButtonClickListener(view: ImageButton, position: Int) {
            ShowLogs.log(TAG, "getRingtoneClickListeners imageButtonClickListener position: " + position)
            when {
                isRingtoneImagePlay(view) -> {
                    positionOfPlayingButtonItem = position
                    presenter.playChosenRingtone(position)
                    presenter.setClickedIndexToTrue({ it.isPlaying = true }, { it.isPlaying = false },
                            { it.isPlaying }, position)

                }
                !isRingtoneImagePlay(view) -> {
                    presenter.stopPlayingRingtone()
                    presenter.setAllIndexesToFalse({ it.isPlaying }, { it.isPlaying = false }, position)
                }
            }
        }

        override fun recycleViewClickListener(view: View, position: Int) {
            //in case of setting clickListener for whole row view
        }

    }

    private fun isRingtoneImagePlay(view: ImageView): Boolean =
            view.contentDescription == fragmentContext.resources.getString(R.string.contentDescription_playRingtone)
}