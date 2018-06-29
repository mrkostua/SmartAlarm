package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.injections.scope.FragmentScope
import com.mrkostua.mathalarm.tools.AlarmTools
import com.mrkostua.mathalarm.tools.ShowLogs
import dagger.android.support.DaggerFragment
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
        super.onCreate(savedInstanceState)
        fragmentContext = activity!!.applicationContext
        initializeDependOnContextVariables(fragmentContext)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_option_set_ringtone, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        ShowLogs.log(TAG, "itemChangedRefreshRecycleView position : " + itemPosition)
        ringtonesRecycleViewAdapter.notifyItemChanged(itemPosition)
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

    inner class SetRingtoneClickListener : RingtoneClickListeners {
        override fun checkBoxClickListener(view: CheckBox, position: Int) {
            ShowLogs.log(TAG, "getRingtoneClickListeners checkBoxClickListener position: " + position)
            if (view.isChecked) {
                presenter.setClickedIndexToTrue({ it.isChecked = true }, { it.isChecked = false },
                        { it.isChecked }, position)
                presenter.saveChosenRingtoneNameSP(position)

            } else {
                presenter.setCheckedOrPlayingToFalse({ it.isChecked }, { it.isChecked = false })

            }
        }

        override fun imageButtonClickListener(view: ImageButton, position: Int) {
            ShowLogs.log(TAG, "getRingtoneClickListeners imageButtonClickListener position: " + position)
            when {
                AlarmTools.isRingtoneImagePlay(fragmentContext, view) -> {
                    positionOfPlayingButtonItem = position
                    presenter.playChosenRingtone(position)
                    presenter.setClickedIndexToTrue({ it.isPlaying = true }, { it.isPlaying = false },
                            { it.isPlaying }, position)

                }
                !AlarmTools.isRingtoneImagePlay(fragmentContext, view) -> {
                    presenter.stopPlayingRingtone()
                    presenter.setCheckedOrPlayingToFalse({ it.isPlaying }, { it.isPlaying = false })
                }
            }
        }

        override fun recycleViewClickListener(view: View, position: Int) {
            //in case of setting clickListener for whole row view
        }

    }
}