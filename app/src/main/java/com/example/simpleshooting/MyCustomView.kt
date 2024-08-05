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

    var tamaLoopCout = 0

    var tamasokudo = 100

    var tamaFrame = 0
    var frame = 0
    var tamaOokisa = 10

    var susumu = 0

    var tamaPaint = Paint()
    var tamaList = mutableListOf(0,0,0,0,1,1,1,1,2,2,2,2)

    override fun onDraw(canvas: Canvas) {
        val ookisa = 100
        val jikiIchi = Rect(posX - ookisa / 2, posY - ookisa / 2, posX + ookisa / 2, posY + ookisa / 2)
        val jikiIro = Paint()
        jikiIro.style = Paint.Style.FILL
        jikiIro.color = Color.WHITE
        canvas.drawRect(jikiIchi, jikiIro)

        tamaPaint.style = Paint.Style.FILL
        val tamaIchiPaint = Paint()
        tamaIchiPaint.style = Paint.Style.FILL
        tamaIchiPaint.color = Color.RED

        //弾①の処理
        if (tamaLoopCout == 100){
            tamaLoopCout = 1
        }else {
            tamaLoopCout += 1
        }

        if (tamaFrame>=1) {
            if (tamaX == 0) {
                tamaX = posX
                tamaY = posY
                tamaFrame = 1
            }
            susumu = tamaFrame * tamasokudo
            val xx = tamaX - tamaOokisa / 2
            val xxx = xx + tamaOokisa
            val yy = tamaY - susumu - (tamaOokisa / 2)
            val yyy = yy + tamaOokisa
            tamaList[0] = xx
            tamaList[1] = yy
            tamaList[2] = xxx
            tamaList[3] = yyy
            val tamaIchi = Rect(tamaList[0], tamaList[1], tamaList[2], tamaList[3])
            canvas.drawRect(tamaIchi, tamaIchiPaint)

            tamaFrame += 1
            tamaLoopCout += 1

            if (tamaY - susumu < 1) {
                tamaX = 0
                tamaFrame = 1
            }
        }






        
    }


    fun myCharacter(posXXX:Int,posYYY:Int) {
        if (tamaLoopCout == 100) {
            tamaLoopCout = 1
        } else {
            tamaLoopCout += 1
        }

        if (tamaFrame >= 1) {
            if (tamaX == 0) {
                tamaX = posXXX
                tamaY = posYYY
                tamaFrame = 1
            }
            susumu = tamaFrame * tamasokudo
            val xx = tamaX - tamaOokisa / 2
            val xxx = xx + tamaOokisa
            val yy = tamaY - susumu - (tamaOokisa / 2)
            val yyy = yy + tamaOokisa
            tamaList[0] = xx
            tamaList[1] = yy
            tamaList[2] = xxx
            tamaList[3] = yyy
            val tamaIchi = Rect(tamaList[0], tamaList[1], tamaList[2], tamaList[3])
            // canvas.drawRect(tamaIchi, tamaIchiPaint)

            tamaFrame += 1
            tamaLoopCout += 1

            if (tamaY - susumu < 1) {
                tamaX = 0
                tamaFrame = 1
            }


        }
    }

    fun tsugiNoSyori() {
        frame += 1


        val ccFrame = frame % 100
        if(ccFrame <= 25) {
            tamaOokisa = 20
        }
        if(ccFrame > 25) {
            tamaOokisa = 30
        }
        if(ccFrame > 50) {
            tamaOokisa = 40
        }
        if(ccFrame > 75) {
            tamaOokisa = 60
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
            if (tamaFrame==0){tamaFrame=1}
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (tamaFrame==0){tamaFrame=1}
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (tamaFrame==0){tamaFrame=1}
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}