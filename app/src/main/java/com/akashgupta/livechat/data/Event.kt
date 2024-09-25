package com.akashgupta.livechat.data

open class Event<out T>(val content: T) { //<out T> - generic define
    var hasBeenHandled = false
    fun getContentOrNull(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }
}