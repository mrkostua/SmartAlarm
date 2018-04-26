package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.database.Cursor
import android.media.RingtoneManager
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * @author Kostiantyn Prysiazhnyi on 4/26/2018.
 */

class RingtoneManagerHelperTest {
    @JvmField
    @Rule
    public val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @Mock
    private lateinit var ringtoneManagerHelper: RingtoneManagerHelper

    private lateinit var context: Context

    @Before
    fun setUp() {
        given(context.applicationContext).willReturn(context)
        ringtoneManagerHelper = RingtoneManagerHelper(context)
    }

    @Test
    fun getDefaultRingtoneListTest() {
        val ringtoneManager = mock(RingtoneManager::class.java)
        given(ringtoneManager.cursor).willReturn(mock(Cursor::class.java))

        val result = ringtoneManagerHelper.getDefaultAlarmRingtonesList(ringtoneManager)
        Assert.assertEquals("empty ringtone list", false, result.isEmpty())
    }
}