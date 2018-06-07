package com.mrkostua.mathalarm.tools

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*
import kotlin.collections.ArrayList


/**
 * @author Kostiantyn Prysiazhnyi on 4/18/2018.
 */
@RunWith(Parameterized::class)
class AlarmToolsTestParameterized(private val calendarTime: Pair<Int, Int>, private val alarmTime: Pair<Int, Int>,
                                  private val expected: Pair<Int, Int>) {
    @JvmField
    @Rule
    public val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var calendar: Calendar

    @Test
    fun testGetTimeToAlarmStart() {
        calendar.timeInMillis = System.currentTimeMillis()
        given(calendar.get(Calendar.HOUR_OF_DAY)).willReturn(calendarTime.first)
        given(calendar.get(Calendar.MINUTE)).willReturn(calendarTime.second)

        val result = AlarmTools.getTimeToAlarmStart(alarmTime.first, alarmTime.second, calendar)
        assertEquals("time (hour) to alarm boom is wrong", expected.first, result.first)
        assertEquals("time (minute) to alarm boom is wrong", expected.second, result.second)
    }

    /**
     * depends on current time! Calendar object is real
     */
    @Ignore
    @Test
    fun testGetTimeToAlarmStartReal() {
        val alarmH = 20
        val alarmM = 0
        val expectedTimeToBoom = Pair(0, 29)

        val result = AlarmTools.getTimeToAlarmStart(alarmH, alarmM)
        assertEquals("hour is wrong", expectedTimeToBoom.first, result.first)
        assertEquals("minute is wrong", expectedTimeToBoom.second, result.second)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getTestDataSet(): List<Array<Pair<Int, Int>>> {
            val testData = ArrayList<Array<Pair<Int, Int>>>()
            testData.add(arrayOf(Pair(12, 10), Pair(12, 12), Pair(0, 2)))
            testData.add(arrayOf(Pair(12, 10), Pair(14, 10), Pair(2, 0)))
            testData.add(arrayOf(Pair(23, 10), Pair(8, 20), Pair(9, 10)))
            testData.add(arrayOf(Pair(0, 1), Pair(0, 3), Pair(0, 2)))

            return testData
        }
    }
}