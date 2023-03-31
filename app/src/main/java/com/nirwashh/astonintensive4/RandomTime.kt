package com.nirwashh.astonintensive4

import android.util.Log
import kotlin.random.Random

class RandomTime(
    private var hours: Int = Random.nextInt(0, 11),
    private var minutes: Int = Random.nextInt(0, 59),
    private var seconds: Int = Random.nextInt(0, 59),
) {

    fun getHours() = hours + minutes/60f
    fun getMinutes() = minutes*1f
    fun getSeconds() = seconds*1f

    private fun updateHours() {
        if (hours < 11) hours++ else hours = 0
    }

    private fun updateMinutes() {
        if (minutes < 59) {
            minutes++
        } else {
            updateHours()
            minutes = 0
        }
    }

    private fun updateSeconds() {
        if (seconds < 59) {
            seconds++
        } else {
            updateMinutes()
            seconds = 0
        }
    }

    fun update() {
        updateSeconds()
        Log.d("Time", "$hours:$minutes:$seconds")
    }
}
