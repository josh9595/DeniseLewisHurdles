package com.example.deniselewishurdles

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView

class GameView (
    context: Context,
    private val size: Point
): SurfaceView(context), Runnable {

    private val gameThread = Thread(this)
    private var playing = false
    private var paused = true
    private var lost = false
    private var score = 0
    private var startOfGameTime: Long = 0

    private val assetManager = context.assets
    private var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()
    private var player: Player = Player(context, size.x, size.y)
    private var track: Track = Track(context, size.x, size.y)
    private var hurdle: Hurdle = Hurdle(context, size.x, size.y)
    private var crowd1: Crowd = Crowd(context, size.x, size.y, 1)
    private var crowd2: Crowd = Crowd(context, size.x, size.y, 2)
    private var cloud1: Cloud = Cloud(context, size.x, size.y, 1)
    private var cloud2: Cloud = Cloud(context, size.x, size.y, 2)
    private var scoreboard: Scoreboard = Scoreboard(context, size.x, size.y)
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "denise-lewis",
        Context.MODE_PRIVATE)

    private var highScore =  prefs.getInt("highScore", 0)

    private fun prepareLevel() {
        // Here we will initialize the game objects
        track = Track(context, size.x, size.y)
        player = Player(context, size.x, size.y)
        hurdle = Hurdle(context, size.x, size.y)
        crowd1 = Crowd(context, size.x, size.y, 1)
        crowd2 = Crowd(context, size.x, size.y, 2)
        cloud1 = Cloud(context, size.x, size.y, 1)
        cloud2 = Cloud(context, size.x, size.y, 2)
        score = 0
        startOfGameTime = System.currentTimeMillis()
        lost = false
    }

    override fun run() {
        var fps: Long = 0

        while (playing) {
            val startFrameTime = System.currentTimeMillis()

            if (!paused) {
                if (startOfGameTime == 0L) startOfGameTime = System.currentTimeMillis()
                update(fps)
                if (!lost){
                    score = (((startFrameTime - startOfGameTime) / 100)).toInt()
                    if (score > highScore) highScore = score
                }

            }

            draw()

            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }
        }
    }

    private fun update(fps: Long) {
        player.update(fps, score)
        track.update(fps)
        hurdle.update(fps, score)
        crowd1.update(fps, score)
        crowd2.update(fps, score)
        cloud1.update(fps)
        cloud2.update(fps)

        if (player.position.left + player.width >= hurdle.position.left &&
                (player.position.top + player.height) >= hurdle.position.top) {
            lost = true
        }

        if (lost) {
            paused = true
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.argb(255, 0, 164, 1))
            paint.color = Color.argb(255, 83, 121, 203)

            canvas.drawRect(0f, 0f, size.x.toFloat(), size.y / 2f, paint)

            canvas.drawBitmap(
                cloud1.bitmap,
                cloud1.position.left,
                cloud1.position.top,
                paint)

            canvas.drawBitmap(
                cloud2.bitmap,
                cloud2.position.left,
                cloud2.position.top,
                paint)

            canvas.drawBitmap(
                scoreboard.bitmap,
                scoreboard.position.left,
                scoreboard.position.top,
                paint)

            canvas.drawBitmap(
                crowd1.bitmap,
                crowd1.position.left,
                crowd1.position.top,
                paint)

            canvas.drawBitmap(
                crowd2.bitmap,
                crowd2.position.left,
                crowd2.position.top,
                paint)

            canvas.drawBitmap(
                track.bitmap,
                track.position.left,
                track.position.top,
                paint)

            canvas.drawBitmap(
                hurdle.bitmap,
                hurdle.position.left,
                hurdle.position.top,
                paint)

            canvas.drawBitmap(
                player.visibleBitmap,
                player.position.left,
                player.position.top,
                paint)

            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 70f
            paint.typeface = Typeface.createFromAsset(assetManager, "modes.ttf")
            canvas.drawText("Score: $score", size.x / 4f, size.y / 5f, paint)
            canvas.drawText("HI: $highScore", size.x / 4f, (size.y / 5f) + 80f, paint)

//            canvas.drawText("Player ${player.position.left.toInt() + player.width.toInt()} ${player.position.top.toInt() + player.height.toInt()} ", 0f, 50f, paint)
//            canvas.drawText("Hurdle ${hurdle.position.left.toInt()} ${hurdle.position.top.toInt()} ", 0f, 150f, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        playing = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }

        val prefs = context.getSharedPreferences(
            "denise-lewis",
            Context.MODE_PRIVATE)

        val oldHighScore = prefs.getInt("highScore", 0)

        if(highScore > oldHighScore) {
            val editor = prefs.edit()

            editor.putInt(
                "highScore", highScore)

            editor.apply()
        }
    }

    fun resume() {
        playing = true
        gameThread.start()
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        if (!lost) {
            when (motionEvent.action and MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                // Or moved their finger while touching screen
                MotionEvent.ACTION_POINTER_DOWN,
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE-> {
                    paused = false
                    player.state = Player.jump
                }

                // Player has removed finger from screen
                MotionEvent.ACTION_POINTER_UP,
                MotionEvent.ACTION_UP -> {
                    player.state = Player.run
                }

            }
        } else {
            prepareLevel()
        }

        return true
    }
}