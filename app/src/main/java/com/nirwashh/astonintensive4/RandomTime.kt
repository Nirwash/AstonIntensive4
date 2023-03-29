package com.nirwashh.astonintensive4

import kotlin.random.Random

class RandomTime(
    private var hours: Int = Random.nextInt(0, 11),
    private var minutes: Int = Random.nextInt(0, 59),
    private var seconds: Int = Random.nextInt(0, 59),
) {

    fun getHours() = hours
    fun getMinutes() = minutes
    fun getSeconds() = seconds

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
    }
}
