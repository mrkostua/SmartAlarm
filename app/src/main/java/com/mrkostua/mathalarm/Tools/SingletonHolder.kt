package com.mrkostua.mathalarm.Tools

/**
 * @author Kostiantyn Prysiazhnyi on 2018-02-04.
 */

open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg0: A): T {
        val firstInstanceCheck = instance
        return when {
            firstInstanceCheck != null -> firstInstanceCheck
            else -> {
                synchronized(this) {
                    val secondInstanceCheck = instance
                    if (secondInstanceCheck != null) {
                        secondInstanceCheck
                    } else {
                        val created = creator!!(arg0)
                        instance = created
                        creator = null
                        created
                    }
                }

            }
        }
    }

}

