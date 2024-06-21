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
        //目チカチカすんで


        if (frame % 10 ==1) {
            pMyShikaku.color = Color.RED
        }

        //球をだす、、とは？？？
        //一度だけ座標を覚えておいて、それをｙのみプラスしつづけていく
        //だからtamaIchiの座標にposXを使い続けてはだめなんだろうなぁ

        val tamaIchi = Rect(posX+45, posY-45, posX+10, posY-10)
        val tamaPaint = Paint()
        tamaPaint.color = Color.WHITE
        if (frame % 10 ==1) {
            tamaPaint.style = Paint.Style.FILL
        }else{
            tamaPaint.style = Paint.Style.STROKE
        }
        //塗りをなしにして消えたように見せらんねー？と思ったが

        //見せたくないものは描画しなければいいのか
        if (frame % 10 ==1) {
            canvas.drawRect(tamaIchi, tamaPaint)
        }


        pMyShikaku.style = Paint.Style.FILL
        canvas.drawRect(rMyShikaku, pMyShikaku)

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