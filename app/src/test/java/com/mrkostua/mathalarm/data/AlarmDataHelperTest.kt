package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.tools.ConstantsPreferences
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

//what is the best way to write tests for apps with Dagger (check some projects, bluePrints, maybe Android Testing)
//JUnit + Mockito Today's topic
/**
 * @author Kostiantyn Prysiazhnyi on 4/23/2018.
 */
class AlarmDataHelperTest {
    //We can use here method verify from Mockito to test if proper method from SharedPreferences was triggered
    @Mock
    public lateinit var mRingtoneManagerHelper: RingtoneManagerHelper

    @Mock
    public lateinit var mSharedPreferences: SharedPreferences

    @Mock
    public lateinit var mEditor: SharedPreferences.Editor

    @JvmField
    @Rule //for initializing @Mock
    public val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var dataHelper: AlarmDataHelper

    @Before
    fun initialize() {
        initializeMockedSharedPreferences()
        dataHelper = AlarmDataHelper(mSharedPreferences, mRingtoneManagerHelper)
    }

    @Test
    fun saveTimeInSPTest() {
        dataHelper.saveTimeInSP(20, 20)
        //Assert.assertEquals("torba",4,mSharedPreferences.get(ConstantsPreferences.ALARM_HOURS.getKeyValue(),25))
        verify(mEditor).putInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), 20)

    }

    @Test
    fun getTimeFromSPTest() {
        dataHelper.getTimeFromSP()
        verify(mSharedPreferences).getInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), 2)
    }


    private fun initializeMockedSharedPreferences() {
        given(mEditor.commit()).willReturn(true)
        given(mSharedPreferences.edit()).willReturn(mEditor)
        given(mSharedPreferences.getInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), 2)).willReturn(2)
    }
}