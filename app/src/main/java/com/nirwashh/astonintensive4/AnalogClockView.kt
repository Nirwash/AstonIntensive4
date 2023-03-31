package com.nirwashh.astonintensive4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import java.lang.Integer.min
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val time = RandomTime()
    private lateinit var timer: CountDownTimer
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val uiMode = context.resources.getString(R.string.uiMode)
    private val defColor = when (uiMode) {
        "day" -> Color.BLACK
        "night" -> Color.WHITE
        else -> Color.BLACK
    }
    private val minutesColor = Color.RED
    private val hoursColor = Color.BLUE
    private val hourMarksSize = 48f
    private val smallBorderWidth = 16.0f
    private val mediumBorderWidth = 22.0f
    private val bigBorderWidth = 26.0f
    private val padding = 14
    private var clockHeight = 0
    private var clockWidth = 0
    private var centreX = 0f
    private var centreY = 0f
    private var radius = 0f
    private var isInit = false


    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            initClock()
        }
        drawCircle(canvas)
        drawHoursMarks(canvas)
        drawArrows(canvas)
        invalidate()
    }

    private fun initClock() {
        isInit = true
        clockHeight = height
        clockWidth = width
        centreX = clockWidth / 2f
        centreY = clockHeight / 2f
        val min = min(clockHeight, clockWidth)
        radius = min / 2f - padding
        timer = object : CountDownTimer(86400000, 1000) {
            override fun onTick(p0: Long) {
                time.update()
            }

            override fun onFinish() {}
        }
        timer.start()
    }

    private fun drawCircle(canvas: Canvas) {
        paint.color = defColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = bigBorderWidth
        canvas.drawCircle(centreX, centreY, radius, paint)
    }

    private fun drawHoursMarks(canvas: Canvas) {
        paint.color = defColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = bigBorderWidth
        for (i in 0..11) {
            val angle = i * (360f / 12f) * (PI.toFloat() / 180f)
            canvas.drawLine(
                centreX + cos(angle) * (radius - hourMarksSize),
                centreY + sin(angle) * (radius - hourMarksSize),
                centreX + cos(angle) * (radius),
                centreY + sin(angle) * (radius),
                paint
            )
        }
    }

    private fun drawArrow(
        canvas: Canvas,
        time: Float,
        color: Int,
        strokeWidth: Float,
        strokeLength: Float
    ) {
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        val angle = PI.toFloat() * time / 30 - PI.toFloat() / 2
        canvas.drawLine(
            centreX - cos(angle) * (strokeLength / 100 * 30),
            centreY - sin(angle) * (strokeLength / 100 * 30),
            centreX + cos(angle) * (strokeLength / 100 * 70),
            centreY + sin(angle) * (strokeLength / 100 * 70),
            paint
        )
    }

    private fun drawArrows(canvas: Canvas) {
        val lengthSecondArrow = radius
        val lengthMinuteArrow = radius / 100 * 65
        val lengthHourArrow = radius / 2
        drawArrow(canvas, time.getSeconds(), defColor, bigBorderWidth, lengthSecondArrow)
        drawArrow(canvas, time.getMinutes(), minutesColor, mediumBorderWidth, lengthMinuteArrow)
        drawArrow(canvas, time.getHours() * 5, hoursColor, smallBorderWidth, lengthHourArrow)
    }
}