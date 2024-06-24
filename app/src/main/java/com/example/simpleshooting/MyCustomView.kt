package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var hueru = 0
    override fun onDraw(canvas: Canvas) {
        val r = Rect(100, 100, 200+hueru, 200+hueru)
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.FILL
        canvas.drawRect(r, p)
        val rMyShikaku = Rect(posX, posY, posX+100, posY+100)
        val pMyShikaku = Paint()

        if (frame % 2 ==1) {
            pMyShikaku.color = Color.BLUE
        }else {
            pMyShikaku.color = Color.YELLOW
        }

        pMyShikaku.style = Paint.Style.FILL
        canvas.drawRect(rMyShikaku, pMyShikaku)

        var xxx = 0
        var yyy = 0

        //まずは一発撃つ
        if (frame % 10 ==1) {
            val tamaPaint = Paint()
            //o
             xxx = posX
             yyy = posY

            tamaPaint.color = Color.WHITE
            tamaPaint.style = Paint.Style.FILL
            val tamaIchi = Rect(xxx+40, yyy-frame*10-60, xxx+60, yyy-frame*10-40)
                canvas.drawRect(tamaIchi, tamaPaint)

        }
     }


    var frame = 0
    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
        hueru += 5
    }

    fun beginAnimation() {
        tsugiNoSyori()
    }

    var posX = 150
    var posY = 150
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            hueru = 0
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            hueru = 0
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            hueru = 0
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}