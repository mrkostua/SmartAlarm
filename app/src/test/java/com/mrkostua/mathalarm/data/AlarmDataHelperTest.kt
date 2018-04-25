package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
//what is the best way to write tests for apps with Dagger (check some projects, bluePrints, maybe Android Testing)
//JUnit + Mockito Today's topic
/**
 * @author Kostiantyn Prysiazhnyi on 4/23/2018.
 */
class AlarmDataHelperTest {
    //We can use here method verify from Mockito to test if proper method from SharedPreferences was triggered
    @Mock
    public lateinit var ringtoneManager : RingtoneManagerHelper

    @Mock
    public lateinit var sp : SharedPreferences

    private lateinit var dataHelper: AlarmDataHelper
    @Before
    fun initialize(){
        dataHelper = AlarmDataHelper(sp,ringtoneManager)
    }
    @Test
    fun saveTimeInSPTest(){
    }



    private fun testSharedPreferencesSet(){

    }
}