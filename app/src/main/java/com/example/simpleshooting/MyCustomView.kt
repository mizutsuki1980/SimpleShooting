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


class myUgoki(posXx:Int,posYy:Int,val myookisa:Int,){
    val left = posXx - myookisa / 2
    val top = posYy - myookisa / 2
    val right = posXx + myookisa / 2
    val bottom = posYy + myookisa / 2
    val jikiIchi = Rect(left, top, right,bottom)
    val jikiIro = Paint()
}

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaFrameIchi = 0
    var frame = 0
    var tamaOokisa = 10
    var jikiX = 550
    var jikiY = 1500
    var clickX = jikiX
    var clickY = jikiY
    var ookisa = 100
    var myJiki = myUgoki(jikiX,jikiY,ookisa)

    override fun onDraw(canvas: Canvas) {
        //まず、座標と大きさをしていると描画する
        myJiki = myUgoki(jikiX,jikiY,ookisa)
        myJiki.jikiIro.style = Paint.Style.FILL
        myJiki.jikiIro.color = Color.WHITE
        canvas.drawRect(myJiki.jikiIchi, myJiki.jikiIro)


        //座標が違ったら、比較して移動させる。ただ、ワープのような移動はしない。
        val saX = jikiX - clickX

        if (saX<=-5 && saX >= 5){

            //ここがなんか変？
            jikiX = clickX


        }else {
            if (saX > 0) {
                jikiX -= 20
            }
            if (saX < 0) {
                jikiX += 20
            }
            myJiki = myUgoki(jikiX, jikiY, ookisa)
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }
}