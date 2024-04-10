package com.sample.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class PaintView(context:Context) : View(context) {
    private var path = Path()
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }
    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val x = it.x
            val y = it.y

            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(x, y)
                    lastX = x
                    lastY = y
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = abs(x - lastX)
                    val dy = abs(y - lastY)
                    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2)
                        lastX = x
                        lastY = y
                    }
                    invalidate()
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    path.lineTo(x, y)
                    invalidate()
                    return true
                }

                else -> return false
            }
        }

        return false
    }

    companion object {
        private const val TOUCH_TOLERANCE = 4f
    }

}