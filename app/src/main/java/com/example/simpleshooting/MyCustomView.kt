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



    var m = jiki()
    var t = teki()
    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(m.myShikakuRectXY(m.x,m.y,m.tamaOokisa), m.iro)
        clickShitaBshoNiIdou()

        canvas.drawRect(t.myShikakuRectXY(t.x,t.y,t.tamaOokisa), t.iro)
        t.x = tekiUgki(t.x)

    }

    fun startSetUp(){
        m.x = jikiX
        m.y = jikiY
    }

    fun clickShitaBshoNiIdou(){
        val saX = m.x - clickX
        val saY = m.y - clickY
        var x = m.x
        var y = m.y
        val mySpeed = 2.0
        val myPlus = 10 * mySpeed .toInt()

        if (saX >= -(myPlus) && saX <= myPlus){
            x = clickX
        }else {
            if (saX > 0) {
                x -= myPlus
            }
            if (saX < 0) {
                x += myPlus
            }
        }
        if (saY >= -myPlus && saY <= myPlus){
            y = clickY
        }else {
            if (saY > 0) {
                y -= myPlus
            }
            if (saY < 0) {
                y += myPlus
            }
        }
        jikiX = x
        jikiY = y
        m.x = x
        m.y = y
    }


    fun jiki():myPosition{
        val m = myPosition(jikiX,jikiY,30,30)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.LTGRAY
        return m
    }

    fun teki():myPosition{
        val t = myPosition(20,50,30,30)
        t.iro.style = Paint.Style.FILL
        t.iro.color = Color.CYAN
        return t
    }


    fun tekiUgki(x:Int):Int{
        //ｘだけで作れなかったのが気持ち悪い。
        //なぜか引数のところにvarとかつけると赤線になる。なんなん？
        //ｘのかわりにｘｘを使ったらできたけど、なんかきもい。
        var xx = x
        if(xx<800){
            xx += 50
        }
        if(xx >= 800) {
            xx = -100
        }
        return xx
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
        startSetUp()
        tsugiNoSyori()
    }

    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed({ tsugiNoSyori() }, 100)
    }

}

