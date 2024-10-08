package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var frame = 0
    var tamaOokisa = 10
    var tamaFrameIchi = 0
    var enemyTamaSpeed = 2.0
    var jikiX = 300 //初期位置
    var jikiY = 800 //初期位置
    var clickX = jikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = jikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置

    class myPosition(var x:Int,var y:Int,jOokisa:Int,val tamaOokisa:Int){
        //xとyには中心とする座標が入る。大きさを計算して左右上下の４点を決める。弾の大きさはいるのか？って感じ。生存フラグは一応つける。
        var alive = true
        var left = x  - jOokisa / 2
        var right = x  + jOokisa / 2
        var top = y  - (jOokisa)
        var bottom = y
        var iro = Paint()


        //x,yから四角を描画する時に利用
        fun myShikakuRectXY(x:Int,y:Int,Ookisa:Int):Rect{
            left = x  - Ookisa / 2
            right = x  + Ookisa / 2
            top = y  - Ookisa
            bottom = y
            val m = Rect(left, top, right,bottom)
            return m
        }


        //x,yから円形を描画する時に利用
        fun myCircleRectXY(x:Int,y:Int,Ookisa:Int){

        }

    }




    override fun onDraw(canvas: Canvas) {
//色だけの設定は外に出すことができるようんだ。
        val m = myPosition(500,500,30,30)
        m.iro = nuriGreen(m)
        canvas.drawRect(m.myShikakuRectXY(m.x,m.y,m.tamaOokisa), m.iro)




    }


    fun nuriGreen(m:myPosition):Paint{
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.GREEN
        return m.iro
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

    fun beginAnimation() {
        tsugiNoSyori()
    }

    fun tsugiNoSyori() {
        invalidate()
        handler.postDelayed({ tsugiNoSyori() }, 100)
    }

}

