package com.mrkostua.mathalarm.tools

import android.support.annotation.VisibleForTesting
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Kostiantyn Prysiazhnyi on 6/7/2018.
 */
/**
 * This class methods must be used in synchronise way
 */
object CustomRandom {
    private val random = Random()

    fun getUniqueRandomValues(minBound: Int, maxInclusiveBound: Int, size: Int = 1): ArrayList<Int> {
        if (size > maxInclusiveBound - minBound + 1) {
            throw UnsupportedOperationException("getUniqueRandomValues impossible to generate more unique values," +
                    " than (maxInclusiveBound + 1) - minBound")
        }
        val uniqueRandomList = ArrayList<Int>()
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
        if (bordersInDP < 2) {
            throw UnsupportedOperationException("Padding in 1 dimensional space must be > 1 (null-> result <- padding)")
        }
        if (size * getClosestEvenNumber(bordersInDP) + size > (maxInclusiveBound - minBound + 1)) {
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
                        if (isGeneratedArrayCanBeAccepted(bufferArray, uniqueRandomListWithPadding,
                                        IntRange(minBound, maxInclusiveBound).toList() as ArrayList<Int>, size)) {
                            uniqueRandomList.add(generatedValue)
                            uniqueRandomListWithPadding.addAll(bufferArray)
                        }

                    }
                    bufferArray.clear()
                }
            }

        }
        return uniqueRandomList
    }

    private fun isGeneratedArrayCanBeAccepted(generatedElements: ArrayList<Int>, acceptedGeneratedElements: ArrayList<Int>,
                                              initialFreeElementsList: ArrayList<Int>, wantedResultSize: Int): Boolean {
        var restAmountOfElementsToGenerate = (wantedResultSize - acceptedGeneratedElements.size / generatedElements.size) - 1 //1 is our generatedElements
        val oneSideBorder = (generatedElements.size - 1) / 2
        var waitingIndex = 0

        acceptedGeneratedElements.forEach {
            initialFreeElementsList.remove(it)
        }
        generatedElements.forEach {
            initialFreeElementsList.remove(it)
        }
        for (i in 0..initialFreeElementsList.lastIndex) {
            if (waitingIndex > 0) {
                --waitingIndex
                continue
            }
            if (i < oneSideBorder) {
                continue
            }
            if (checkLeftElements(i, initialFreeElementsList, oneSideBorder)) {
                if (initialFreeElementsList.lastIndex - oneSideBorder >= i && checkRightElements(i, initialFreeElementsList, oneSideBorder)) {
                    if (--restAmountOfElementsToGenerate == 0) {
                        break
                    }
                    //checking if it is not the end of the loop and skipping padding elements in the loop
                    if (i + oneSideBorder * 2 + 1 < initialFreeElementsList.lastIndex) {
                        waitingIndex = oneSideBorder * 2
                    } else {
                        break
                    }

                }
            }

        }
        return restAmountOfElementsToGenerate <= 0
    }

    private fun checkLeftElements(currentElementIndex: Int, arrayList: ArrayList<Int>, paddingAmount: Int): Boolean {
        for (i in 1..paddingAmount) {
            if (arrayList[currentElementIndex] - i == arrayList[currentElementIndex - i]) {
                continue
            }
            return false
        }
        return true
    }

    private fun checkRightElements(currentElementIndex: Int, arrayList: ArrayList<Int>, paddingAmount: Int): Boolean {
        for (i in 1..paddingAmount) {
            if (arrayList[currentElementIndex] + i == arrayList[currentElementIndex + i]) {
                continue
            }
            return false
        }
        return true
    }

    private fun <T> containsAtLeastOne(array: ArrayList<T>, what: ArrayList<T>): Boolean {
        what.forEach {
            if (array.contains(it)) {
                return true
            }
        }
        return false
    }

    private fun getClosestEvenNumber(from: Int) = ((from / 2) * 2)

    @VisibleForTesting
    fun getTestingIsGeneratedArrayCanBeAccepted(generatedElements: ArrayList<Int>, savedGeneratedElements: ArrayList<Int>,
                                                initialFreeElementsList: ArrayList<Int>, wantedResultSize: Int) =
            isGeneratedArrayCanBeAccepted(generatedElements, savedGeneratedElements, initialFreeElementsList, wantedResultSize)
}