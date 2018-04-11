package com.mrkostua.mathalarm.Interfaces

/**
 * @author Kostiantyn Prysiazhnyi on 3/11/2018.
 */
interface BasePresenter<T> {
    /**
     * method for initializing and starting some initial data or services.
     */
    fun start()

    fun takeView(view: T)
}