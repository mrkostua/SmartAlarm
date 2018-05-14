package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.SharedPreferences
import com.mrkostua.mathalarm.data.AlarmDataHelper
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
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
    @Mock
    lateinit var sp: SharedPreferences
    @Mock
    lateinit var ringtoneManager: RingtoneManagerHelper
    @Mock
    lateinit var alarmDataHelper: AlarmDataHelper
    lateinit var ringtonePresenter: OptionSetRingtonePresenter


    private var ringtonesList = ArrayList<RingtoneObject>()


    @Before
    fun setUp() {
        //alarmDataHelper = mock(AlarmDataHelper::class.java)
        ringtonePresenter = OptionSetRingtonePresenter(initializeRingtonesList(), playerHelper)
        //ringtonePresenter.takeView(RingtoneView())

    }

    private fun initializeRingtonesList(): AlarmDataHelper {
        ringtonesList.add(RingtoneObject("ringtone_mechanic_clock", 2, false, false))
        ringtonesList.add(RingtoneObject("ringtone_energy", 1, false, false))
        ringtonesList.add(RingtoneObject("ringtone_loud", 3, false, false))
        ringtonesList.add(RingtoneObject("ringtone_digital_clock", 4, false, true))
        given(alarmDataHelper.getRingtonesForPopulation()).willReturn(ringtonesList)
        return alarmDataHelper
    }

    @Test
    fun setAllIndexesToFalse() {
        ringtonesList[0].isChecked = true
        ringtonePresenter.setCheckedOrPlayingToFalse({ it.isChecked })
        assertFalse(ringtonesList[0].isChecked)
        ringtonesList.forEach { assertFalse(it.isChecked) }

    }

    @Test
    fun setClickedIndexToTrue() {
    }

    @Test
    fun initializeLastSavedRingtone() {
    }

    inner class RingtoneView : OptionSetRingtoneContract.View {
        override var positionOfPlayingButtonItem: Int
            get() = 0
            set(value) {}

        override fun itemChangedRefreshRecycleView(itemPosition: Int) {
        }
    }
}