package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Scoreboard(
    context: Context,
    private val screenX: Int,
    private val screenY: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.scoreboard)

    val width = screenX / 1.5f
    private val height = width

    val position = RectF(
        screenX / 6f,
        screenY / 30f,
        screenX/2 + width,
        screenY.toFloat())

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap,
            width.toInt() ,
            height.toInt() ,
            false)
    }

}