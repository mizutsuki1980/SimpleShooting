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
    var tamaX2 = 0
    var tamaY2 = 0
    var tamaX3 = 0
    var tamaY3 = 0


    var tamasokudo = 100

    var tamaFrame = 1
    var tamaNiFrame = 0
    var tamaSanFrame = 0
    var frame = 0
    var tamaOokisa = 10

    var susumu = 0
    var susumuNi = 0
    var susumuSan = 0


    var tamaPaint = Paint()
    var tamaList = mutableListOf(0,0,0,0,1,1,1,1,2,2,2,2)

    //なんか一定の間隔で出ていない気がするので、修正する。

    override fun onDraw(canvas: Canvas) {
        val r = Rect(100, 100, 200, 200)
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.FILL
        canvas.drawRect(r, p)
        val ookisa = 100
        val jikiIchi =
            Rect(posX - ookisa / 2, posY - ookisa / 2, posX + ookisa / 2, posY + ookisa / 2)
        val jikiIro = Paint()
        if (frame % 2 == 1) {
            jikiIro.color = Color.WHITE
        } else {
            jikiIro.color = Color.GRAY
        }
        jikiIro.style = Paint.Style.FILL
        canvas.drawRect(jikiIchi, jikiIro)
        tamaPaint.style = Paint.Style.FILL


        val tamaIchiPaint = Paint()
        tamaIchiPaint.style = Paint.Style.FILL
        tamaIchiPaint.color = Color.RED

        val tamaNiPaint = Paint()
        tamaNiPaint.style = Paint.Style.FILL
        tamaNiPaint.color = Color.BLUE

        val tamaSanPaint = Paint()
        tamaSanPaint.style = Paint.Style.FILL
        tamaSanPaint.color = Color.WHITE

        //白と青の間隔が極端に狭い
        //というか、白が青と赤の間にめり込んでるのか。
        //かならず青のちょっと前に白が来る。
        //①赤　②青　③白　なので
        //


        //弾①の処理
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
            //弾が進んだ処理をする。
            if (tamaY - susumu < 1) {
                tamaX = 0
                tamaFrame = 0
            }
        }

        //弾②の処理
        if (tamaNiFrame == 0) {
            //frameじゃなくてtamaFrameにしたらどうだろう？
            if (tamaFrame % 10 == 0) {
                tamaNiFrame = 1
            }
        }
        if (tamaNiFrame>=1) {
            if (tamaNiFrame==1){
                tamaX2 = posX
                tamaY2 = posY
            }
            susumuNi = tamaNiFrame * tamasokudo
            val xx2 = tamaX2 - tamaOokisa / 2
            val xxx2 = xx2 + tamaOokisa
            val yy2 = tamaY2 - susumuNi - (tamaOokisa / 2)
            val yyy2 = yy2 + tamaOokisa
            tamaList[4] = xx2
            tamaList[5] = yy2
            tamaList[6] = xxx2
            tamaList[7] = yyy2
            val tamaNi = Rect(tamaList[4], tamaList[5], tamaList[6], tamaList[7])
            canvas.drawRect(tamaNi, tamaNiPaint)
            tamaNiFrame += 1

            if (tamaY2 - susumuNi < 1) {
                tamaNiFrame = 0
            }

            //弾③の処理
            if (tamaSanFrame == 0) {
                if (tamaNiFrame % 20 == 0) {
                    tamaSanFrame = 1
                }
            }

            if (tamaSanFrame>=1) {
                if (tamaSanFrame == 1) {
                    tamaX3 = posX
                    tamaY3 = posY
                }

                susumuSan = tamaSanFrame * tamasokudo
                val xx3 = tamaX3 - tamaOokisa / 2
                val xxx3 = xx3 + tamaOokisa
                val yy3 = tamaY3 - susumuSan - (tamaOokisa / 2)
                val yyy3 = yy3 + tamaOokisa
                tamaList[8] = xx3
                tamaList[9] = yy3
                tamaList[10] = xxx3
                tamaList[11] = yyy3
                val tamaSan = Rect(tamaList[8], tamaList[9], tamaList[10], tamaList[11])
                canvas.drawRect(tamaSan, tamaSanPaint)
                tamaSanFrame += 1

                if (tamaY3 - susumuSan < 1) {
                    tamaSanFrame = 0
                    tamaFrame = 1
                }
            }


            //弾①～③の処理、終了


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