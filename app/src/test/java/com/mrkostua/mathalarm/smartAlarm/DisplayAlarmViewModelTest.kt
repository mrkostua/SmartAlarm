package com.mrkostua.mathalarm.smartAlarm

import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmViewModel
import com.mrkostua.mathalarm.testingTools.AlarmDataHelperStub
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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

    @Test
    fun getUniqueRandomValues() {
        val result = testedClass.getUniqueRandomValues(0, 3, 4)
        val uniqueSet = HashSet<Int>()
        result.forEachIndexed { index, i ->
            assertTrue("generated value is from the wrong range", i in 0..3)
            uniqueSet.add(i)
        }
        assertEquals("generated array contains duplicates", uniqueSet.size, result.size)

        var check = false
        try {
            testedClass.getUniqueRandomValues(0, 10, 12)
        } catch (e: UnsupportedOperationException) {
            check = true
        }
        assertTrue("for incorrect fun arguments exception wasn't thrown", check)

    }

    @Test
    fun getUniqueRandomValuesMultiTime() {
        val testData = arrayOf(Pair(0, 5), Pair(20, 100), Pair(15, 35))
        var result: ArrayList<Int>
        val uniqueSet = HashSet<Int>()
        for (i in 0 until testData.size) {
            result = testedClass.getUniqueRandomValues(testData[i].first, testData[i].second, testData[i].second - testData[i].first)
            result.forEachIndexed { index, j ->
                assertTrue("generated value is from the wrong range", j in testData[i].first..testData[i].second)
                uniqueSet.add(j)
            }
            assertEquals("generated array contains duplicates", uniqueSet.size, result.size)
            uniqueSet.clear()
            result.clear()
        }


    }
}