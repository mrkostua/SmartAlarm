package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import com.mrkostua.mathalarm.testingTools.AlarmDataHelperStub
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * @author Kostiantyn Prysiazhnyi on 5/11/2018.
 */

class OptionSetRingtonePresenterTest {
    @JvmField
    @Rule
    public val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @Mock
    lateinit var playerHelper: MediaPlayerHelper

    private val lastSavedRingtoneIndex = 1
    private lateinit var ringtonePresenter: OptionSetRingtonePresenter
    private lateinit var dataHelperStub: AlarmDataHelperStub

    @Before
    fun setUp() {
        dataHelperStub = AlarmDataHelperStub(initializeRingtonesList(), lastSavedRingtoneIndex)
        ringtonePresenter = OptionSetRingtonePresenter(dataHelperStub, playerHelper)
        ringtonePresenter.takeView(RingtoneViewStub())

    }

    private fun initializeRingtonesList(): ArrayList<RingtoneObject> {
        val ringtonesList = ArrayList<RingtoneObject>()
        ringtonesList.add(RingtoneObject("ringtone_mechanic_clock", 2, false, false))
        ringtonesList.add(RingtoneObject("energy", 1, false, false))
        ringtonesList.add(RingtoneObject("loud", 3, false, false))
        ringtonesList.add(RingtoneObject("digital_clock", 4, false, false))
        return ringtonesList
    }

    @Test
    fun setCheckedOrPlayingToFalse() {
        ringtonePresenter.ringtonePopulationList[2].isChecked = true
        ringtonePresenter.setCheckedOrPlayingToFalse({ it.isChecked }, { it.isChecked = false })
        ringtonePresenter.ringtonePopulationList.forEach { assertFalse(it.isChecked) }

        ringtonePresenter.ringtonePopulationList[2].isPlaying = true
        ringtonePresenter.setCheckedOrPlayingToFalse({ it.isPlaying }, { it.isPlaying = false })
        ringtonePresenter.ringtonePopulationList.forEach { assertFalse(it.isPlaying) }
    }

    @Test
    fun setClickedIndexToTrue() {
        val playPosition = 2
        ringtonePresenter.setClickedIndexToTrue({ it.isPlaying = true }, { it.isPlaying = false },
                { it.isPlaying }, playPosition)
        assertTrue(ringtonePresenter.ringtonePopulationList[playPosition].isPlaying)
        ringtonePresenter.ringtonePopulationList.forEachIndexed { index, it ->
            if (index != playPosition) {
                assertFalse(it.isPlaying)
            }
        }
        val checkPosition = 3
        ringtonePresenter.setClickedIndexToTrue({ it.isChecked = true }, { it.isChecked = false },
                { it.isChecked }, checkPosition)
        assertTrue(ringtonePresenter.ringtonePopulationList[checkPosition].isChecked)
        ringtonePresenter.ringtonePopulationList.forEachIndexed { index, it ->
            if (index != checkPosition) {
                assertFalse(it.isChecked)
            }
        }
    }

    @Test
    fun initializeLastSavedRingtone() {
        ringtonePresenter.initializeLastSavedRingtone()
        assertTrue(ringtonePresenter.ringtonePopulationList[lastSavedRingtoneIndex].isChecked)
        ringtonePresenter.ringtonePopulationList.forEachIndexed { index, it ->
            if (index != lastSavedRingtoneIndex) {
                assertFalse(it.isChecked)
            }
        }
    }

    inner class RingtoneViewStub : OptionSetRingtoneContract.View {
        override var positionOfPlayingButtonItem: Int
            get() = 0
            set(value) {}

        override fun itemChangedRefreshRecycleView(itemPosition: Int) {
        }
    }
}