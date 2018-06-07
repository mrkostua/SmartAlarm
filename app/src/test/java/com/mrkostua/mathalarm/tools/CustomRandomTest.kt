package com.mrkostua.mathalarm.tools

import junit.framework.Assert
import org.junit.Test
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 6/7/2018.
 */
class CustomRandomTest {
    @Test
    fun getUniqueRandomValues() {
        val result = CustomRandom.getUniqueRandomValues(0, 3, 4)
        val uniqueSet = HashSet<Int>()
        result.forEachIndexed { index, i ->
            Assert.assertTrue("generated value is from the wrong range", i in 0..3)
            uniqueSet.add(i)
        }
        Assert.assertEquals("generated array contains duplicates", uniqueSet.size, result.size)

        var check = false
        try {
            CustomRandom.getUniqueRandomValues(0, 10, 12)
        } catch (e: UnsupportedOperationException) {
            check = true
        }
        Assert.assertTrue("for incorrect fun arguments exception wasn't thrown", check)

    }

    @Test
    fun getUniqueRandomValuesMultiTime() {
        val testData = arrayOf(Pair(0, 5), Pair(20, 100), Pair(15, 35))
        var result: ArrayList<Int>
        val uniqueSet = HashSet<Int>()
        for (i in 0 until testData.size) {
            result = CustomRandom.getUniqueRandomValues(testData[i].first, testData[i].second, testData[i].second - testData[i].first)
            result.forEachIndexed { index, j ->
                Assert.assertTrue("generated value is from the wrong range", j in testData[i].first..testData[i].second)
                uniqueSet.add(j)
            }
            Assert.assertEquals("generated array contains duplicates", uniqueSet.size, result.size)
            uniqueSet.clear()
            result.clear()
        }

    }

    @Test
    fun getUniqueRandomBorderedValuesBadInput() {
        var check = false
        try {
            CustomRandom.getUniqueRandomBorderedValues(0, 5, 2, 4)
        } catch (e: UnsupportedOperationException) {
            check = true
        }
        Assert.assertTrue("for incorrect fun arguments exception wasn't thrown", check)


    }

    @Test
    fun getUniqueRandomBorderedValues() {
        val testData = arrayOf(Pair(0, 3), Pair(20, 100), Pair(5, 25))
        val testSizePaddingData = arrayOf(Pair(2, 1), Pair(6, 4), Pair(5, 5))
        var result: ArrayList<Int>
        val uniqueSet = HashSet<Int>()
        Assert.assertEquals("wrong implementation of the test data", testData.size, testSizePaddingData.size)

        for (i in 0 until testData.size) {
            result = CustomRandom.getUniqueRandomBorderedValues(testData[i].first, testData[i].second, testSizePaddingData[i].first, testSizePaddingData[i].second)
            result.sort()
            result.forEachIndexed { index, j ->
                Assert.assertTrue("generated value is from the wrong range", j in testData[i].first..testData[i].second)
                uniqueSet.add(j)
                when (index) {
                    0 -> Assert.assertTrue("first generated value have bad padding",
                            (j - testSizePaddingData[i].second / 2 >= 0) && (j + testSizePaddingData[i].second / 2 < result[j + 1] - testSizePaddingData[i].second / 2))
                    result.lastIndex -> Assert.assertTrue("last generated value have bad padding",
                            (j - testSizePaddingData[i].second / 2 > result[j - 1] + testSizePaddingData[i].second / 2) && (j + testSizePaddingData[i].second / 2 <= testData[i].second))
                    else -> {
                        Assert.assertTrue("inner generated value have bad padding",
                                (j - testSizePaddingData[i].second / 2 > result[j - 1] + testSizePaddingData[i].second / 2) && (j + testSizePaddingData[i].second / 2 < result[j + 1] - testSizePaddingData[i].second / 2))

                    }
                }

            }
            Assert.assertEquals("generated array contains duplicates", uniqueSet.size, result.size)
            uniqueSet.clear()
            result.clear()
        }
    }
}