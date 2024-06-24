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
        //tsugiNoSyoriに置いても、onDrawに置いてもうごくのか
        //どっちに置くのがいいのだろう？↓↓↓
        frame += 1

        val r = Rect(100, 100, 200+hueru, 200+hueru)
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.FILL
        canvas.drawRect(r, p)
        hueru += 5

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
            //弾一発だすのは成功。
            tamaPaint.color = Color.WHITE
            tamaPaint.style = Paint.Style.FILL

            if (xxx == 0){
            xxx = posX
            yyy = posY
            }

            val tamaIchi = Rect(xxx+40, yyy-frame*10-60, xxx+60, yyy-frame*10-40)
            canvas.drawRect(tamaIchi, tamaPaint)
            //じゃぁ2発は？


        }
     }


    var frame = 0
    fun tsugiNoSyori() {
        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
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