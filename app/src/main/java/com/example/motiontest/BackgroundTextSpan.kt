package com.example.motiontest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.style.ReplacementSpan


class BackgroundTextSpan(
    private val backgroundText: String,
    private val backgroundColor: Int,
    private val textColor: Int
) : ReplacementSpan() {

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        // Draw the background text
        paint.color = backgroundColor
        canvas.drawText(backgroundText, x, y.toFloat(), paint)

        // Draw the main text
        paint.color = textColor
        val xPos = (canvas.width / 2 - paint.measureText(text, start, end) / 2)
        if (text != null) {
            canvas.drawText(text, start, end, xPos, y.toFloat(), paint)
        }
    }
}