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
    var et = eTama()


    class myPosition(var x:Int,var y:Int,var Ookisa:Int,val tamaOokisa:Int){
        var alive = true    //念のため　使うのかわからないけど
        var homing = true   //敵の弾が自機を捕まえに来るのに使う
        var zenkaiVect = mutableListOf<Int>(0,0)    //敵の弾が自機を捕まえに来るのに使う

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
        canvas.drawCircle(m.x.toFloat(),m.y.toFloat(),(m.Ookisa/2).toFloat(),m.iro) //自機は丸にした
        clickShitaBshoNiIdou()        //自機の移動　処理


        canvas.drawRect(t.myShikakuRectXY(t.x,t.y,t.Ookisa), t.iro)
        t.x = tekiUgki(t.x)        //敵の移動　処理


        canvas.drawRect(jt.myShikakuRectXY(jt.x,jt.y,jt.Ookisa), jt.iro)
        tamaSyori()        //自機の弾　処理


        //敵の弾　処理
        canvas.drawRect(et.myShikakuRectXY(et.x,et.y,et.Ookisa), et.iro)
        eTamaIdoSyori()

        enemyTamaAtatta()


    }
    fun enemyTamaAtatta(){
        val saX = m.x - et.x
        val saY = m.y - et.y
        val length = (saX*saX) + (saY*saY)
        val kurauHanni =  et.tamaOokisa/2 + m.Ookisa/2
        if (length < kurauHanni){
              //弾のリセット処理をここにかく
        }


        if (et.x > 700 || et.x < 0){
            //敵の弾のリセット
        }

        if (et.y > 1300 || et.y < 0){
            //敵の弾のリセット
        }
    }


    fun eTamaIdoSyori() {
        val enemyTamaSpeed = 2.0

        val ex = et.x
        val ey = et.y

        val jx = m.x
        val jy = m.y

        var vx = jx - ex
        var vy = jy - ey

        var resetKyori = 70

        if(vx<resetKyori && vx > -resetKyori){
            if(vy<resetKyori && vy > -resetKyori) {
                et.homing = false
            }
        }

        if (et.homing) {
        }else{
            vx = et.zenkaiVect[0]
            vy = et.zenkaiVect[1]
        }
        et.zenkaiVect[0] = vx
        et.zenkaiVect[1] = vy


        val vv = (vx * vx) + (vy * vy) .toDouble()
        val vvv = Math.sqrt(vv)
        val vvx = (vx / vvv)*10 * enemyTamaSpeed
        val vvy = (vy / vvv)*10 * enemyTamaSpeed

        et.x += vvx.toInt()
        et.y += vvy.toInt()

        //0だったらリセットする。これ、弾フレーム使わなくても、座標だけでいけんじゃねぇの？
        //たぶん衝突判定もここにはさむ

        if(et.y>1050){et = eTama()}        //画面の下部で消える
        if(et.x>690){et = eTama()}        //画面の右部で消える
        if(et.x<1){et = eTama()}        //画面の左部で消える

    }


    fun tamaSyori(){
        val tamaSpeed = 6.0
        val tamaPlus = 10 * tamaSpeed .toInt()
        tamaFrameIchi += 1
        jt.y-= tamaPlus
        if(jt.y<5){tamaFrameIchi=0}        //画面の上部で消える
        if(tamaFrameIchi==20){tamaFrameIchi=0}        //20フレームでリセット
        if(tamaFrameIchi==0){jt=jTama()}
    }

    fun eTama():myPosition{
        val m = myPosition(t.x,t.y,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.MAGENTA
        return m
    }

    fun jTama():myPosition{
        val m = myPosition(jikiX,jikiY,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.GREEN
        return m
    }


    fun startSetUp(){
        m.x = jikiX
        m.y = jikiY
    }



    fun jiki():myPosition{

        val m = myPosition(jikiX,jikiY,50,30)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
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
        if(xx<800){ xx += 50 }
        if(xx >= 800) { xx = -100 }
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

