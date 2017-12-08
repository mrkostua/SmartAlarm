package com.mrkostua.mathalarm

/**
 * @author Kostiantyn on 21.11.2017.
 */

class AlarmObject() {
    var hours = -1
    var minutes = -1
    var textMessage = ""


    constructor(hours: Int, minutes: Int) : this() {
        this.hours = hours
        this.minutes = minutes
    }

    constructor(textMessage: String) : this() {
        this.textMessage = textMessage
    }

}
