package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.tools.ConstantsPreferences
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * @author Kostiantyn Prysiazhnyi on 4/23/2018.
 */
class AlarmDataHelperTest {
    /**
     * MockitoJUnit.rule() can't be static so we can't use @Mock annotation to create mocked objects and to use @BeforeClass
     */
    companion object {
        private lateinit var dataHelper: AlarmDataHelper
        private lateinit var mRingtoneManagerHelper: RingtoneManagerHelper
        private lateinit var mSharedPreferences: SharedPreferences
        private lateinit var mEditor: SharedPreferences.Editor

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            MockitoAnnotations.initMocks(this)
            mSharedPreferences = mock(SharedPreferences::class.java)
            mRingtoneManagerHelper = mock(RingtoneManagerHelper::class.java)
            mEditor = mock(SharedPreferences.Editor::class.java)

            dataHelper = AlarmDataHelper(initializeMockedSharedPreferences(), mRingtoneManagerHelper)
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            //todo is it important to destroy initialized objects or garbage collector is ok?
        }

        private fun initializeMockedSharedPreferences(): SharedPreferences {
            given(mSharedPreferences.edit()).willReturn(mEditor)
            return mSharedPreferences
        }
    }

    @Test
    fun saveTimeInSPTest() {
        dataHelper.saveTimeInSP(20, 22)

        verify(mEditor).putInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), 20)
        verify(mEditor).putInt(ConstantsPreferences.ALARM_MINUTES.getKeyValue(), 22)
        verify(mEditor, never()).putInt(ConstantsPreferences.ALARM_MINUTES.getKeyValue(), 10)
    }

    @Test
    fun getTimeFromSPTest() {
        given(mSharedPreferences.getInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), ConstantsPreferences.ALARM_HOURS.getDefaultIntValue())).willReturn(2)
        given(mSharedPreferences.getInt(ConstantsPreferences.ALARM_MINUTES.getKeyValue(), ConstantsPreferences.ALARM_MINUTES.getDefaultIntValue())).willReturn(199)
        val result = dataHelper.getTimeFromSP()

        verify(mSharedPreferences).getInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), ConstantsPreferences.ALARM_HOURS.getDefaultIntValue())
        assertEquals("get mocked value(hour) from SP is different", 2, result.first)

        verify(mSharedPreferences).getInt(ConstantsPreferences.ALARM_MINUTES.getKeyValue(), ConstantsPreferences.ALARM_MINUTES.getDefaultIntValue())
        assertEquals("get mocked value(hour) from SP is different", 199, result.second)
    }

    @Test
    fun saveRingtoneInSPTest() {
        dataHelper.saveRingtoneInSP("testRingtone")
        verify(mEditor).putString(ConstantsPreferences.ALARM_RINGTONE_NAME.getKeyValue(), "testRingtone")
    }

    @Test
    fun getDeepWakeUpStateInSPTest() {
        given(mSharedPreferences.getBoolean(ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getKeyValue(),
                ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getDefaultIntValue() == 1)).willReturn(false)
        val result = dataHelper.getDeepWakeUpStateFromSP()

        verify(mSharedPreferences).getBoolean(ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getKeyValue(),
                ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getDefaultIntValue() == 1)
        assertEquals("", false, result)
    }

    @Test
    fun saveDeepWakeUpStateInSPTest() {
        dataHelper.saveDeepWakeUpStateInSP(true)
        verify(mEditor).putBoolean(ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getKeyValue(), true)
    }

    @Test
    fun saveDeepWakeUpRingtoneInSPTest() {
        dataHelper.saveDeepWakeUpRingtoneInSP("testRingtone")
        verify(mEditor).putString(ConstantsPreferences.ALARM_DEEP_WAKE_UP_RINGTONE.getKeyValue(), "testRingtone")
    }

    @Test
    fun getRingtoneFromSPTest() {
        given(mSharedPreferences.getString(ConstantsPreferences.ALARM_RINGTONE_NAME.getKeyValue(), ConstantsPreferences.ALARM_RINGTONE_NAME.defaultRingtoneName)).willReturn("Don't worry, be happy")
        val result = dataHelper.getRingtoneFromSP()

        verify(mSharedPreferences).getString(ConstantsPreferences.ALARM_RINGTONE_NAME.getKeyValue(), ConstantsPreferences.ALARM_RINGTONE_NAME.defaultRingtoneName)
        assertEquals("get mocked value(ringtone name) form Sp is different", "Don't worry, be happy", result)
    }

    @Test
    fun getTextMessageFromSP() {
        given(mSharedPreferences.getString(ConstantsPreferences.ALARM_TEXT_MESSAGE.getKeyValue(), ConstantsPreferences.ALARM_TEXT_MESSAGE.defaultTextMessage))
                .willReturn("Hello babe")
        val result = dataHelper.getTextMessageFromSP()
        verify(mSharedPreferences).getString(ConstantsPreferences.ALARM_TEXT_MESSAGE.getKeyValue(), ConstantsPreferences.ALARM_TEXT_MESSAGE.defaultTextMessage)
        assertEquals("et mocked value(text message) form Sp is different", "Hello babe", result)

    }

    @Test
    fun isFirstAlarmSavingTest() {
        given(mSharedPreferences.getInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), ConstantsPreferences.ALARM_HOURS.emptyPreferencesValue))
                .willReturn(ConstantsPreferences.ALARM_HOURS.emptyPreferencesValue)
        val result = dataHelper.isFirstAlarmSaving()
        verify(mSharedPreferences).getInt(ConstantsPreferences.ALARM_HOURS.getKeyValue(), ConstantsPreferences.ALARM_HOURS.emptyPreferencesValue)
        assertEquals("", true, result)
    }

}