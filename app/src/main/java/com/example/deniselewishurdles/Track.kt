package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Track(
    context: Context,
    private val screenX: Int,
    private val screenY: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.track)

    val width = screenX * 1.7f
    private val height = width

    val position = RectF(
        -10f,
        screenY / 4f,
        screenX/2 + width,
        screenY.toFloat())

    private val speed  = 450f

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long) {

        if (position.left > -((screenX * 0.7f)-50)) {
            position.left -= speed / fps
        }
    }
}