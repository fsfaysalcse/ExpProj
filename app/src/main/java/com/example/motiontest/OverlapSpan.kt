package com.example.motiontest

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

class OverlapSpan(private val text: String, private val textSize: Float) : ReplacementSpan() {

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val oldTextSize = paint.textSize
        paint.textSize = textSize
        canvas.drawText(this.text, x, y.toFloat(), paint)
        paint.textSize = oldTextSize
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val oldTextSize = paint.textSize
        paint.textSize = textSize
        val width = paint.measureText(this.text)
        paint.textSize = oldTextSize
        return width.toInt()
    }
}
