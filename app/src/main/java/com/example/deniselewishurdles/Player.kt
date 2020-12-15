package com.example.deniselewishurdles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Player(
    context: Context,
    private val screenX: Int,
    private val screenY: Int) {

    var currentScore = 0
    var currentRunBitmap = 0

    private val startBitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.denise_start)

    private val runningBitmaps = listOf<Bitmap>(
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.denise_run_1),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.denise_run_2),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.denise_run_3),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.denise_run_4),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.denise_run_5),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.denise_run_6)
    )

    val jump1Bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.denise_jump_1)

    val jump2Bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.denise_run_2)

    val jump3Bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.denise_run_3)

    var visibleBitmap: Bitmap = startBitmap

    val width = screenX / 2.5f
    val height = width

    val position = RectF(
        screenX / 30f,
        screenY / 2f,
        screenX/2 + width,
        screenY.toFloat())

    val lowestPosition = position.top

    private val speed  = 450f

    companion object {
        const val stopped = 0
        const val run = 1
        const val jump = 2
    }

    var state = stopped

    init{
        visibleBitmap = Bitmap.createScaledBitmap(visibleBitmap,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long, score: Int) {
        if (state == run) {
             if (score != currentScore) {
                 if (currentRunBitmap == 5){
                     currentRunBitmap = 0
                     currentScore = score
                 } else {
                     currentRunBitmap ++
                     currentScore = score
                 }
             }

            if (position.top < lowestPosition) {
                position.top += speed / fps
            }

            visibleBitmap = Bitmap.createScaledBitmap(runningBitmaps[currentRunBitmap],
                width.toInt() ,
                height.toInt() ,
                false)
        }

        else if (state == jump) {
            position.top -= (speed*1.5f) / fps
            visibleBitmap = Bitmap.createScaledBitmap(jump1Bitmap,
                width.toInt() ,
                height.toInt() ,
                false)
        }

    }

}