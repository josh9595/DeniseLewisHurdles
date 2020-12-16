package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Cloud(
    context: Context,
    private val screenX: Int,
    private val screenY: Int,
    number: Int) {

    var selectedCloud = number

    private val cloud1: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        if (number == 1) R.drawable.cloud_1 else R.drawable.cloud_2)

    private val cloud2: Bitmap = BitmapFactory.decodeResource(
    context.resources,
    if (number == 1) R.drawable.cloud_1 else R.drawable.cloud_2)

    var bitmap: Bitmap = cloud1

    val width = screenX / 4f
    private val height = width

    val position = RectF(
        if (number == 1) screenX / 8f else screenX / 1.75f,
        if (number == 1) screenY / 50f else 0f,
        screenX/2 + width,
        screenY.toFloat())

    private val speed  = 450f

    init{
        bitmap = Bitmap.createScaledBitmap(
            if (selectedCloud == 1) cloud1 else cloud2,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long) {
        if (position.left <= -(screenX / 4f)) {
            selectedCloud = if (selectedCloud == 1) 2 else 1
            bitmap = Bitmap.createScaledBitmap(
                if (selectedCloud == 1) cloud1 else cloud2,
                width.toInt() ,
                height.toInt() ,
                false)
            position.left = screenX.toFloat()
        } else {
            position.left -= (speed/10f) / fps
        }
    }
}