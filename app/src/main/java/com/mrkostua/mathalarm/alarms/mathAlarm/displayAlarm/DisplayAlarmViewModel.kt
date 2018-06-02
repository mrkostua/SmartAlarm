package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.arch.lifecycle.ViewModel
import com.mrkostua.mathalarm.data.AlarmDataHelper
import java.util.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */
class DisplayAlarmViewModel @Inject constructor(dataHelper: AlarmDataHelper) : ViewModel() {
    val userTextMessage: String = "\"" + dataHelper.getTextMessageFromSP() + "\""
    private val random = Random()

    fun getUniqueRandomValues(minBound: Int, maxInclusiveBound: Int, size: Int = 1): ArrayList<Int> {
        val uniqueRandomList = ArrayList<Int>()
        if (size > maxInclusiveBound - minBound + 1) {
            throw UnsupportedOperationException("getUniqueRandomValues impossible to generate more unique values," +
                    " than (maxInclusiveBound + 1) - minBound")
        }
        var result: Int
        uniqueRandomList.clear()
        while (uniqueRandomList.size < size) {
            result = random.nextInt((maxInclusiveBound - minBound) + 1) + minBound
            if (!uniqueRandomList.contains(result)) {
                uniqueRandomList.add(result)
            }
        }
        return uniqueRandomList
    }
}