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
    var tamaX = 0
    var tamaY = 0
    var tamaPaint = Paint()
    override fun onDraw(canvas: Canvas) {
        val r = Rect(100, 100, 200, 200)
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.FILL
        canvas.drawRect(r, p)
        val ookisa = 100
        val jikiIchi = Rect(posX-ookisa/2, posY-ookisa/2, posX+ookisa/2, posY+ookisa/2)
        val jikiIro = Paint()
        if (frame % 2 ==1) {
            jikiIro.color = Color.WHITE
        }else {
            jikiIro.color = Color.GRAY
        }
        jikiIro.style = Paint.Style.FILL
        canvas.drawRect(jikiIchi, jikiIro)
        //これよる上は関係ない
         tamaOokisa = 30
        //弾の大きさは固定

        tamaPaint.style = Paint.Style.FILL

        val countFrame = frame % 100
        if(countFrame > 25) {
            tamaOokisa = 80
        }
        if(countFrame > 50) {
            tamaOokisa = 100
        }
        if(countFrame > 75) {
            tamaOokisa = 120
        }
        //一応できた　なんかもっとスマートにかけそうな気がする。1...10の間は、みたいな指定ができないのかな＝みたいな記号で

        if (frame % 3 == 2) {
            tamaPaint.color = Color.RED
        }
        if (frame % 3 == 1) {
            tamaPaint.color = Color.GREEN
        }
        if (frame % 3 == 0) {
            tamaPaint.color = Color.BLUE
        }



        //弾の塗りもとりあえず固定

        for (num in 1..2) {
            //ここでリピートするとフレームが動かないのか！？　たぶんそだね。
            val tamaList = mutableListOf(100,200,120,220)
            if (tamaX == 0){
                tamaX = posX
                tamaY = posY
                tamaFrame = 1
            }
            susumu = tamaFrame*30

            val xx = tamaX-tamaOokisa/2
            val xxx =xx+tamaOokisa
            val yy = tamaY-susumu-(tamaOokisa/2)
            val yyy =yy+tamaOokisa

            val tamaListTest = mutableListOf(xx,yy,xxx,yyy)

            val tamaIchi = Rect(tamaListTest[0],tamaListTest[1],tamaListTest[2],tamaListTest[3])
            canvas.drawRect(tamaIchi, tamaPaint)

            tamaFrame += 1
            //弾が進んだ処理をする。
            if (tamaY-susumu  < 1){
                tamaX = 0
            }
        }

    }

    var tamaFrame = 0
    var susumu = 0
    var frame = 0
    var tamaOokisa = 0





    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
    }

    fun beginAnimation() {
        tsugiNoSyori()
    }

    var posX = 300
    var posY = 300
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}