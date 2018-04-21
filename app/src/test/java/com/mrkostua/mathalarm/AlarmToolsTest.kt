package com.mrkostua.mathalarm

import com.mrkostua.mathalarm.tools.AlarmTools
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*


/**
 * @author Kostiantyn Prysiazhnyi on 4/18/2018.
 */
class AlarmToolsTest {
    @Mock
    private lateinit var calendar: Calendar

    @Before
    fun initiate() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun testCovertTo24Format() {
        val minToAlarmStart = 340
        val expected = Pair(5, 40)
        val actual = AlarmTools.convertTo24Format(340)
        assertEquals("convertion $minToAlarmStart min. to 24Format failed", expected, actual)
    }

    //TODO create parametrized test with testData and expected result
    @Test
    fun testGetTimeToAlarmStart() {
        calendar.timeInMillis = System.currentTimeMillis()
        `when`(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(12)
        `when`(calendar.get(Calendar.MINUTE)).thenReturn(10)

        val expected = Pair(0, 2)

        val result = AlarmTools.getTimeToAlarmStart(12, 12, calendar)
        assertEquals("not working properly", expected.first + expected.second, result.first + result.second)
    }

    /**
     * depends on current time! Calendar object is real
     */
    @Test
    fun testGetTimeToAlarmStartReal() {
        val alarmH = 20
        val alarmM = 0
        val expectedTimeToBoom = Pair(0, 29)

        val result = AlarmTools.getTimeToAlarmStart(alarmH, alarmM)
        assertEquals("hour is wrong", expectedTimeToBoom.first, result.first)
        assertEquals("minute is wrong", expectedTimeToBoom.second, result.second)
    }
}