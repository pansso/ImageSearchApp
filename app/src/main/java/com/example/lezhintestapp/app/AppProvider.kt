package com.example.lezhintestapp.app

import android.content.Context
import timber.log.Timber

class AppProvider : InitProvider() {

    private var internalContext: Context? = null
    val appCompat: Context
        get() = internalContext!!

    override fun onCreate(): Boolean {
        internalContext = context
        initTimber()
        return true
    }

    private fun initTimber() {
        Timber.uprootAll()
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                val threadName = Thread.currentThread().name
                return "(${element.fileName}:${element.lineNumber})"
            }
        })
    }
}