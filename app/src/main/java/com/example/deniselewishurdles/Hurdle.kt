package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Hurdle(
    context: Context,
    private val screenX: Int,
    private val screenY: Int) {

    val hurdle: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.hurdle)

    var hurdleDown: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.hurdle_down)

    var bitmap: Bitmap = hurdle

    val width = screenX / 5f
    private val height = width

    val position = RectF(
        screenX.toFloat(),
        screenY / 1.34f,
        screenX/2 + width,
        screenY.toFloat())

    private val speed  = 450f

    init{
        bitmap = Bitmap.createScaledBitmap(hurdle,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long, score: Int, lost: Boolean) {
            if (lost) {
                bitmap = Bitmap.createScaledBitmap(hurdleDown,
                    width.toInt() ,
                    height.toInt() ,
                    false)
            }

            if (position.left < -(screenX / 5f)) {
                position.left = screenX.toFloat()
            } else {
                if (score > 20) position.left -= (speed * 1.5f) / fps
            }
    }

}