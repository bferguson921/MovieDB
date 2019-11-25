package com.example.moviedb.util

import android.util.Log

object Logger {
    private const val ERROR_TAG = "ERROR"
    private const val DEBUG_TAG = "DEBUG"

    fun error(throwable : Throwable) {
        Log.e(ERROR_TAG, throwable.toString())
    }

    fun debug(log: String){
        Log.d(DEBUG_TAG, log)
    }
}