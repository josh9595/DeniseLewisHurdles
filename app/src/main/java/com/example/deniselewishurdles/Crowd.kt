package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Crowd(
    context: Context,
    private val screenX: Int,
    private val screenY: Int,
    number: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        if (number == 1) R.drawable.crowd_1 else R.drawable.crowd_2)

    val width = screenX * 1.5f
    private val height = width

    val position = RectF(
        if (number == 1) 0f else screenX * 1.5f,
        screenY / 3f,
        screenX/2 + width,
        screenY.toFloat())

    private val speed  = 450f

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long, score: Int) {
        if (position.left <= -(screenX * 1.5f)) {
            position.left = screenX * 1.5f
        } else {
            position.left -= (speed/2.5f) / fps
        }
    }
}