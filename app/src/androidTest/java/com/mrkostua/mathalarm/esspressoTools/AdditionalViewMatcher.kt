package com.mrkostua.mathalarm.esspressoTools

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * @author Kostiantyn Prysiazhnyi on 5/14/2018.
 */
class AdditionalViewMatcher {
    companion object {
        fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("atPosition")
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    val viewHolder = view.findViewHolderForAdapterPosition(position)
                            ?: throw UnsupportedOperationException("no item on position $position")
                    return itemMatcher.matches(viewHolder.itemView)
                }

            }
        }
    }

}