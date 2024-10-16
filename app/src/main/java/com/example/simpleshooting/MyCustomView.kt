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
    var m = jiki()
    var t = teki()
    var jt = jTama()


    class myPosition(var x:Int,var y:Int,var Ookisa:Int,val tamaOokisa:Int){
        var alive = true
        var left = x  - Ookisa / 2
        var right = x  + Ookisa / 2
        var top = y  - (Ookisa)
        var bottom = y
        var iro = Paint()
        fun myShikakuRectXY(x:Int,y:Int,Ookisa:Int):Rect{
            left = x  - Ookisa / 2
            right = x  + Ookisa / 2
            top = y  - Ookisa
            bottom = y
            val m = Rect(left, top, right,bottom)
            return m
        }
    }


    override fun onDraw(canvas: Canvas) {
        //CircleはとにかくFloat型
        canvas.drawCircle(m.x.toFloat(),m.y.toFloat(),(m.Ookisa/2).toFloat(),m.iro)
        clickShitaBshoNiIdou()

        canvas.drawRect(t.myShikakuRectXY(t.x,t.y,t.Ookisa), t.iro)
        t.x = tekiUgki(t.x)

        //自機の弾　処理
        canvas.drawRect(jt.myShikakuRectXY(jt.x,jt.y,jt.Ookisa), jt.iro)
        tamaSyori()
        //ここはたぶん、繰り返してる。だけど二発目が出ないのはなんでー？
        //おそらくだけど、出来てるんだと思う。それが、ずーーーと上の方とかにある。
        //２０フレームでリピートするところがうまくいってないとか？
        //値が変化してないとかかなぁ。
    }

    fun jTama():myPosition{
        val m = myPosition(jikiX,jikiY,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.GREEN
        return m
    }

    fun tamaSyori(){
        val tamaSpeed = 6.0
        val tamaPlus = 10 * tamaSpeed .toInt()

        //画面の上部で消える
        if(jt.y < 5){
            tamaFrameIchi = 0
            jTama()
        }

        //100フレームでリセット
        if (tamaFrameIchi == 20){
            tamaFrameIchi = 0
            jTama()
        }

        if (tamaFrameIchi == 0) {
            tamaFrameIchi = 1
        }

        if (tamaFrameIchi > 0) {
            jt.y-= tamaPlus
        }
        tamaFrameIchi += 1
    }

    fun startSetUp(){
        m.x = jikiX
        m.y = jikiY
    }



    fun jiki():myPosition{
        val m = myPosition(jikiX,jikiY,50,30)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.LTGRAY
        return m
    }

    fun teki():myPosition{
        val t = myPosition(20,100,70,30)
        t.iro.style = Paint.Style.FILL
        t.iro.color = Color.CYAN
        return t
    }


    fun tekiUgki(x:Int):Int{
        var xx = x
        if(xx<800){
            xx += 50
        }
        if(xx >= 800) {
            xx = -100
        }
        return xx
    }
    fun clickShitaBshoNiIdou(){
        val saX = m.x - clickX
        val saY = m.y - clickY
        var x = m.x
        var y = m.y
        val mySpeed = 2.5
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

