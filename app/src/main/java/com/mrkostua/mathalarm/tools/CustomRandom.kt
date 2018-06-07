package com.mrkostua.mathalarm.tools

import android.support.annotation.VisibleForTesting
import java.util.*
import kotlin.collections.ArrayList

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
            println("getUniqBV() generatedValue $generatedValue")
            if (!uniqueRandomListWithPadding.contains(generatedValue)) {
                if (generatedValue - minBound >= oneSideBorder && maxInclusiveBound - generatedValue >= oneSideBorder) {
                    for (i in generatedValue - oneSideBorder..generatedValue + oneSideBorder) {
                        bufferArray.add(i)
                    }
                    if (!containsAtLeastOne(uniqueRandomListWithPadding, bufferArray)) {
                        if (isThereMoreEmptySpaceToGenerate(bufferArray, getIntegerListCopy(uniqueRandomListWithPadding), IntRange(minBound, maxInclusiveBound).toList() as ArrayList<Int>, size)) {
                            uniqueRandomList.add(generatedValue)
                            println("getUniqBV() acceptedValue is: $generatedValue")
                            uniqueRandomListWithPadding.addAll(bufferArray)
                        }

                    }
                    bufferArray.clear()
                }
            }

        }
        return uniqueRandomList
    }

    private fun getIntegerListCopy(array: ArrayList<Int>): ArrayList<Int> {
        val result = ArrayList<Int>()
        result.addAll(array)
        return result
    }

    private fun isThereMoreEmptySpaceToGenerate(generatedElements: ArrayList<Int>, savedGeneratedElements: ArrayList<Int>, initialFreeElementsList: ArrayList<Int>, wantedResutSize: Int): Boolean {
        val oneTimeGenerationWeigh = generatedElements.size
        var restAmountOfElementsToGenerate = (wantedResutSize - savedGeneratedElements.size / oneTimeGenerationWeigh) - 1 //1 is our generatedElements
        val oneSideBorder = (generatedElements.size - 1) / 2
        initialFreeElementsList.sort()
        var waitingIndex = 0
        //simple solution savedGenElem is empty
        savedGeneratedElements.addAll(generatedElements)
        savedGeneratedElements.forEach {
            initialFreeElementsList.remove(it)
        }
        for (i in 0..initialFreeElementsList.lastIndex) {
            println("main loop $i")
            if (waitingIndex > 0) {
                --waitingIndex
                continue
            }
            if (i < oneSideBorder) {
                continue
            }
            println("main loop after skipping ${initialFreeElementsList[i]}")

            if (checkLeftElements(i, initialFreeElementsList, oneSideBorder)) {
                if (initialFreeElementsList.lastIndex - oneSideBorder >= i && checkRightElements(i, initialFreeElementsList, oneSideBorder)) {
                    //decrementing our result variable
                    if (--restAmountOfElementsToGenerate <= 0) {
                        break
                    }
                    //checking if it is not the end of the loop and skipping padding elements in the loop
                    if (i + oneSideBorder * 2 + 1 > initialFreeElementsList.lastIndex) {
                        waitingIndex = oneSideBorder * 2 + 1
                    } else {
                        break
                    }

                }
            }
            initialFreeElementsList[i]
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
    fun getTestingIsThereMoreEmptySpaceToGenerate(generatedElements: ArrayList<Int>, savedGeneratedElements: ArrayList<Int>, initialFreeElementsList: ArrayList<Int>, wantedResultSize: Int) =
            isThereMoreEmptySpaceToGenerate(generatedElements, savedGeneratedElements, initialFreeElementsList, wantedResultSize)
}