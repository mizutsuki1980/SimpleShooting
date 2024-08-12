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


    class myJikinoUgoki(posXx:Int,posYy:Int,myookisa:Int){
        val left = posXx - myookisa / 2
        val top = posYy - myookisa / 2
        val right = posXx + myookisa / 2
        val bottom = posYy + myookisa / 2
    }

    class myTamanoUgoki(posXx:Int,posYy:Int,myTamaNoOkisa:Int,myFrame:Int,tamasokudo:Int,val tamaFrame:Int){
        val myX = posXx
        val myY = posYy
        val susumu = tamaFrame * tamasokudo
        val xx = myX - myTamaNoOkisa / 2
        val xxx = xx + myTamaNoOkisa
        val yy = myY - susumu - (myTamaNoOkisa / 2)
        val yyy = yy + myTamaNoOkisa
        val tamaIchi = Rect(xx,yy,xxx,yyy)


        fun hantei():Boolean{
            var seizon :Boolean
            if (myY - susumu < 1) {
                 seizon = true
            }else{
                 seizon = false
            }
            return seizon
        }
    }



class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaX = 0
    var tamaY = 0
    var tamasokudo = 100
    var tamaFrame = 0
    var frame = 0
    var tamaOokisa = 10
    var susumu = 0
    var tamaPaint = Paint()
    var myTama = myTamanoUgoki(0,0,tamaOokisa,frame,tamasokudo,tamaFrame)

    override fun onDraw(canvas: Canvas) {
        val ookisa = 100
        val myJiki = myJikinoUgoki(posX,posY,ookisa)
        val jikiIchi = Rect(myJiki.left, myJiki.top, myJiki.right, myJiki.bottom)
        val jikiIro = Paint()
        jikiIro.style = Paint.Style.FILL
        jikiIro.color = Color.WHITE
        canvas.drawRect(jikiIchi, jikiIro)

        tamaPaint.style = Paint.Style.FILL
        val tamaIchiPaint = Paint()
        tamaIchiPaint.style = Paint.Style.FILL
        tamaIchiPaint.color = Color.RED



        //弾①の処理
        if (tamaFrame>=1) {
            if (tamaX == 0) {
                tamaX = posX
                tamaY = posY
                tamaFrame = 1
            }

            myTama = myTamanoUgoki(posX,posY,tamaOokisa,frame,tamasokudo,tamaFrame)
            canvas.drawRect(myTama.tamaIchi, tamaIchiPaint)


            tamaFrame += 1
            if (myTama.hantei()){
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