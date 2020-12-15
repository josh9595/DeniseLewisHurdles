package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Hurdle(
    context: Context,
    private val screenX: Int,
    private val screenY: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.hurdle)

    val width = screenX / 5f
    private val height = width

    val position = RectF(
        screenX.toFloat(),
        screenY / 1.66f,
        screenX/2 + width,
        screenY.toFloat())

    private val speed  = 450f

    companion object {
        // Which ways can the ship move
        const val stopped = 0
        const val left = 1
        const val right = 2
    }

    var moving = stopped

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long, score: Int) {
            if (position.left < -(screenX / 5f)) {
                position.left = screenX.toFloat()
            } else {
                if (score > 20) position.left -= speed / fps
            }
    }

}