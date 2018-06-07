package com.mrkostua.mathalarm.tools

import junit.framework.Assert
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

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
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            check = true
        }
        Assert.assertTrue("for incorrect fun arguments exception wasn't thrown", check)


    }

    @Test
    fun getUniqueRandomBorderedValues() {
        val testBorderData = arrayOf(Pair(10,25),Pair(0, 5), Pair(20, 100), Pair(5, 25))
        val testSizePaddingData = arrayOf(Pair(5,2),Pair(2, 2), Pair(6, 4), Pair(5, 5))
        var result: ArrayList<Int>
        val uniqueSet = HashSet<Int>()
        var oneSideBorder: Int
        Assert.assertEquals("wrong implementation of the test data", testBorderData.size, testSizePaddingData.size)

        for (i in 0 until testBorderData.size) {
            result = CustomRandom.getUniqueRandomBorderedValues(testBorderData[i].first, testBorderData[i].second, testSizePaddingData[i].first, testSizePaddingData[i].second)
            result.sort()

            oneSideBorder = testSizePaddingData[i].second / 2
            //testing
            println("\noneSideBorder :$oneSideBorder")
            print("initial Data")
            result.forEach { print(it.toString() + " ") }
            result.forEachIndexed { index, j ->
                Assert.assertTrue("generated value $j is from the wrong range", j in testBorderData[i].first..testBorderData[i].second)
                uniqueSet.add(j)
                when (index) {
                    0 -> Assert.assertTrue("first generated value have bad padding for $j",
                            (j - oneSideBorder >= testBorderData[i].first) && (j + oneSideBorder < result[index + 1] - oneSideBorder))
                    result.lastIndex -> Assert.assertTrue("last generated value have bad padding for $j",
                            (j - oneSideBorder > result[index - 1] + oneSideBorder) && (j + oneSideBorder <= testBorderData[i].second))
                    else -> {
                        Assert.assertTrue("inner generated value have bad padding",
                                (j - oneSideBorder > result[index - 1] + oneSideBorder) && (j + oneSideBorder < result[index + 1] - oneSideBorder))

                    }
                }

            }
            Assert.assertEquals("generated array contains duplicates", uniqueSet.size, result.size)
            uniqueSet.clear()
            result.clear()
            break
        }
    }

    /**
     * 2 ways to test private method (reflection in case of some class we can't change) and simple visible getter
     */
    @Test
    fun isThereMoreEmptySpaceToGenerate() {
        assertTrue(CustomRandom.getTestingIsThereMoreEmptySpaceToGenerate(arrayListOf(1, 2, 3), ArrayList(), arrayListOf(0, 1, 2, 3, 4, 5, 6, 7), 2))
        assertFalse(CustomRandom.getTestingIsThereMoreEmptySpaceToGenerate(arrayListOf(1, 2, 3), ArrayList(), arrayListOf(0, 1, 2, 3, 4, 5), 2))
        assertFalse(CustomRandom.getTestingIsThereMoreEmptySpaceToGenerate(arrayListOf(1, 2, 3), arrayListOf(4, 5, 6), arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 4))
        assertTrue(CustomRandom.getTestingIsThereMoreEmptySpaceToGenerate(arrayListOf(8, 9, 10), arrayListOf(4, 5, 6), arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3))
    }
}