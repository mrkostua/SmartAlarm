package com.mrkostua.mathalarm.smartAlarm

import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmViewModel
import com.mrkostua.mathalarm.testingTools.AlarmDataHelperStub
import org.junit.Before

/**
 * @author Kostiantyn Prysiazhnyi on 6/2/2018.
 */
class DisplayAlarmViewModelTest {
    private val dataHelperStub = AlarmDataHelperStub(ArrayList(), 2)
    private lateinit var testedClass: DisplayAlarmViewModel

    @Before
    fun setUp() {
        testedClass = DisplayAlarmViewModel(dataHelperStub)
    }
}