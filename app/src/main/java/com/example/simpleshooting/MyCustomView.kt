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
    var tamaList = mutableListOf(0,0,0,0,1,1,1,1,2,2,2,2)

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
        tamaPaint.style = Paint.Style.FILL



        if (frame % 3 == 2) {
            tamaPaint.color = Color.RED
        }
        if (frame % 3 == 1) {
            tamaPaint.color = Color.GREEN
        }
        if (frame % 3 == 0) {
            tamaPaint.color = Color.BLUE
        }
        
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
            tamaList[0] = xx
            tamaList[1] = yy
            tamaList[2] = xxx
            tamaList[3] = yyy
            val tamaIchi = Rect(tamaList[0],tamaList[1],tamaList[2],tamaList[3])
            canvas.drawRect(tamaIchi, tamaPaint)
            tamaFrame += 1
            //弾が進んだ処理をする。
            if (tamaY-susumu  < 1){
                tamaX = 0
            }



            if (tamaFrame>30){

                val tamaX2 = posX
                val tamaY2 = posY
                susumuNi = tamaNiFrame*30
//弾２が消えてしまうのを直したい
                val xx2 = tamaX2-tamaOokisa/2
                val xxx2 =xx2+tamaOokisa
                val yy2 = tamaY2-susumuNi-(tamaOokisa/2)
                val yyy2 =yy2+tamaOokisa
                tamaList[4] = xx2
                tamaList[5] = yy2
                tamaList[6] = xxx2
                tamaList[7] = yyy2
                val tamaNi = Rect(tamaList[4],tamaList[5],tamaList[6],tamaList[7])
                canvas.drawRect(tamaNi, tamaPaint)
                tamaNiFrame += 1

            }






        
    }

    var tamaFrame = 0
    var tamaNiFrame = 0
    var susumu = 0
    var frame = 0
    var tamaOokisa = 10
    var susumuNi = 0




    fun tsugiNoSyori() {
        frame += 1


        val ccFrame = frame % 100
        if(ccFrame <= 25) {
            tamaOokisa = 20
        }
        if(ccFrame > 25) {
            tamaOokisa = 80
        }
        if(ccFrame > 50) {
            tamaOokisa = 200
        }
        if(ccFrame > 75) {
            tamaOokisa = 300
        }



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