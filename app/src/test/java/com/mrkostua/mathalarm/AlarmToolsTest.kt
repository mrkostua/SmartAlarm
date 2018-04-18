package com.mrkostua.mathalarm

import com.mrkostua.mathalarm.tools.AlarmTools
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Kostiantyn Prysiazhnyi on 4/18/2018.
 */
class AlarmToolsTest {
    @Test
    fun testCovertTo24Format() {
        val minToAlarmStart = 340
        val expected = Pair(5, 40)
        val actual = AlarmTools.convertTo24Format(340)
        assertEquals("convertion $minToAlarmStart min. to 24Format failed", expected, actual)
    }

    @Test
    fun testGetTimeToAlarmStart(){
        //TODO mock Calender and test AlarmTools.getTimeToAlarmStart()
    }
}