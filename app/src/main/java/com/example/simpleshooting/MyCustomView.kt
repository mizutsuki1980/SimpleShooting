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
    var jikiOkisa = 50
    var tekiOkisa = 70
    var tamaFrameIchi = 0
    var enemyTamaSpeed = 2.0
    var jikiX = 300 //初期位置
    var jikiY = 800 //初期位置
    var clickX = jikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = jikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var dgCount = 0
    var scoreCount = 0

    var m = jiki()
    var e = teki()
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

        canvas.drawRect(e.myShikakuRectXY(e.x,e.y,e.Ookisa), e.iro)
        e.x = tekiUgki(e.x)        //敵の移動　処理


        canvas.drawRect(jt.myShikakuRectXY(jt.x,jt.y,jt.Ookisa), jt.iro)
        tamaSyori()        //自機の弾　処理
        tamaJikiSyori()     //自機の弾が当たったら、相手が消える処理をする
        //canvas.drawPicture()とか
        //canvas.drawBitmap()　あやしいのあるなー
        //ここはまぁいいや

        canvas.drawRect(et.myShikakuRectXY(et.x,et.y,et.Ookisa), et.iro)
        enemyTama()        //敵の弾　処理
        enemyTamaAtatta()        //敵の弾が当たったら、敵の弾は消滅する

        //だいたいの処理が出来た。スコアでもつけてみるか。
        //そとからボタンで操作できるようにしたい

    }



    fun tamaJikiSyori(){
        if(jt.Ookisa == 30){
            jt = jTama()    //弾のエフェクトだけちょっとだしたい。
        }
        val vx = jt.x - e.x
        val vy = jt.y - e.y
        val atariKyori = 40 //当たり判定の距離
        //弾が白くなってるのに敵がリセットされていないことがあるなぁ。
        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            jt.iro.color = Color.WHITE
            jt.Ookisa = 30
            e = teki()
            scoreCount += 1
        }
    }

    fun enemyTamaAtatta(){
        val vx = et.x - m.x
        val vy = et.y - m.y
        if(et.Ookisa == 30){
            et = eTama()
        }
        val atariKyori = 15 //当たり判定の距離
        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            if(et.Ookisa != 30) { dgCount += 1 }

            et.iro.color = Color.DKGRAY
            et.Ookisa = 30
        }
    }



    fun tamaSyori(){
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()
        tamaFrameIchi += 1
        jt.y-= tamaPlus
        if(jt.y<5){tamaFrameIchi=0}        //画面の上部で消える
        if(tamaFrameIchi==20){tamaFrameIchi=0}        //20フレームでリセット
        if(tamaFrameIchi==0){jt=jTama()}
    }






    fun enemyTama(){
        var vx = m.x - et.x
        var vy = m.y - et.y

        var resetKyori = 90 //よけ始める距離
        if(vx<resetKyori && vx > -resetKyori && vy<resetKyori && vy > -resetKyori){ et.homing = false }
        if (et.homing == false) {
            vx = et.zenkaiVect[0]
            vy = et.zenkaiVect[1]
        }
        et.zenkaiVect[0] = vx
        et.zenkaiVect[1] = vy
        //敵の弾の移動
        val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        et.x += ((vx / v)*10 * enemyTamaSpeed).toInt()
        et.y += ((vy / v)*10 * enemyTamaSpeed).toInt()
        if (et.x > 690 || et.x < 0 || et.y > 1050 || et.y < 0){et = eTama()}    //画面外で敵の弾のリセット
    }




    fun eTama():myPosition{
        val m = myPosition(e.x,e.y,10,10)
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
         m = jiki()
         e = teki()
         jt = jTama()
         et = eTama()


    }

    fun hyperPowerUp(){
        if (m.Ookisa==200){
            m.Ookisa = 50
        }else{
            m.Ookisa = 200
        }
    }


    fun jiki():myPosition{

        val m = myPosition(jikiX,jikiY,jikiOkisa,30)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
        return m
    }

    fun teki():myPosition{
        val e = myPosition(20,100,tekiOkisa,10)
        e.iro.style = Paint.Style.FILL
        e.iro.color = Color.CYAN
        return e
    }


    fun tekiUgki(x:Int):Int{
        val tekiSpeed = 20
        var xx = x
        if(xx<800){ xx += tekiSpeed }
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
        tsugiNoSyori()
    }

    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed({ tsugiNoSyori() }, 100)
    }



}

