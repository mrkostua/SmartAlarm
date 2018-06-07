package com.mrkostua.mathalarm.tools

import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 6/7/2018.
 */
object CustomRandom {
    private val random = Random()
    private val uniqueRandomList = ArrayList<Int>()

    fun getUniqueRandomValues(minBound: Int, maxInclusiveBound: Int, size: Int = 1): ArrayList<Int> {
        if (size > maxInclusiveBound - minBound + 1) {
            throw UnsupportedOperationException("getUniqueRandomValues impossible to generate more unique values," +
                    " than (maxInclusiveBound + 1) - minBound")
        }
        uniqueRandomList.clear()
        var result: Int
        while (uniqueRandomList.size < size) {
            result = random.nextInt((maxInclusiveBound - minBound) + 1) + minBound
            if (!uniqueRandomList.contains(result)) {
                uniqueRandomList.add(result)
            }
        }
        return uniqueRandomList
    }

    fun getUniqueRandomBorderedValues(minBound: Int, maxInclusiveBound: Int, size: Int = 1, bordersInDP: Int): ArrayList<Int> {
        if (size * ((bordersInDP / 2) * 2) + size > (maxInclusiveBound - minBound + 1)) {
            throw UnsupportedOperationException("getUniqueRandomValues impossible to generate more unique values with borders," +
                    " than (maxInclusiveBound + 1) - minBound")
        }
        val oneSideBorder = bordersInDP / 2
        val uniqueRandomListWithPadding = ArrayList<Int>()
        val uniqueRandomList = ArrayList<Int>()
        val bufferArray = ArrayList<Int>()

        var generatedValue: Int

        while (uniqueRandomList.size < size) {
            generatedValue = random.nextInt((maxInclusiveBound - minBound) + 1) + minBound
            if (!uniqueRandomListWithPadding.contains(generatedValue)) {
                if (generatedValue - minBound >= oneSideBorder && maxInclusiveBound - generatedValue >= oneSideBorder) {
                    for (i in generatedValue - oneSideBorder..generatedValue + oneSideBorder) {
                        bufferArray.add(i)
                    }
                    if (!containsAtLeastOne(uniqueRandomListWithPadding, bufferArray)) {
                        uniqueRandomList.add(generatedValue)
                        uniqueRandomListWithPadding.addAll(bufferArray)

                    }
                    bufferArray.clear()
                }
            }

        }
        return uniqueRandomList
    }

    private fun <T> containsAtLeastOne(array: ArrayList<T>, what: ArrayList<T>): Boolean {
        what.forEach {
            if (array.contains(it)) {
                return true
            }
        }
        return false
    }

}