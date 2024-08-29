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


class myUgoki(jikiX:Int,jikiY:Int,val jikiOokisa:Int,){
    //クリックしたポイントを中心に自機ができる
    val left = jikiX - jikiOokisa / 2
    val right = jikiX + jikiOokisa / 2
    val top = jikiY - jikiOokisa / 2
    val bottom = jikiY + jikiOokisa / 2
    val jikiIchi = Rect(left, top, right,bottom)
    val jikiIro = Paint()
}

class myTama(jikiX:Int,jikiY:Int,jikiOokisa:Int,tamaOokisa:Int,var alive:Boolean){
    //弾のｘ軸はこれでいい
    var left = jikiX  - tamaOokisa / 2
    var right = jikiX  + tamaOokisa / 2

    var top = jikiY  - (tamaOokisa)
    var bottom = jikiY

    val tamaIchi = Rect(left, top, right,bottom)
    val tamaIro = Paint()

    fun tamaRect(left:Int, top:Int, right:Int,bottom:Int): Rect {
        return  Rect(left, top, right,bottom)
    }

}


class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaFrameIchi = 0
    var frame = 0
    var tamaOokisa = 10
    var jikiX = 550 //初期位置
    var jikiY = 1500 //初期位置
    var clickX = jikiX
    var clickY = jikiY
    var jikiOokisa = 100
    var myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
    var myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)

    override fun onDraw(canvas: Canvas) {
        //まず、座標と大きさを指定して描画する
        myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
        myJiki.jikiIro.style = Paint.Style.FILL
        myJiki.jikiIro.color = Color.WHITE



        myTama.tamaIro.style = Paint.Style.FILL
        myTama.tamaIro.color = Color.GREEN

        canvas.drawRect(myJiki.jikiIchi, myJiki.jikiIro)
        clickShitaBshoNiIdou()

        canvas.drawRect(myTama.tamaRect(myTama.left,myTama.top,myTama.right,myTama.bottom), myTama.tamaIro)
        shootingShot()


    }

    fun shootingShot(){
        //tamaSpeedはDouble型
        val tamaSpeed = 9.0
        val tamaPlus = 10 * tamaSpeed .toInt()


        if(myTama.top < 5){
            tamaFrameIchi = 0
            myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
        }

        if (tamaFrameIchi == 100){
            tamaFrameIchi = 0
            myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
        }


        if (tamaFrameIchi == 0) {
            tamaFrameIchi = 1
        }

        if (tamaFrameIchi > 0) {
            myTama.top -= tamaPlus
            myTama.bottom -= tamaPlus
        }

        tamaFrameIchi += 1
}


    fun clickShitaBshoNiIdou(){
        val saX = jikiX - clickX
        val saY = jikiY - clickY
        var x = jikiX
        var y = jikiY
        if (saX >= -10 && saX <= 10){
            x = clickX
        }else {
            if (saX > 0) {
                x -= 20
            }
            if (saX < 0) {
                x += 20
            }
        }
        if (saY >= -10 && saY <= 10){
            y = clickY
        }else {
            if (saY > 0) {
                y -= 20
            }
            if (saY < 0) {
                y += 20
            }
        }
        jikiX = x
        jikiY = y
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