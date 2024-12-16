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
    var dgCount = 0
    var scoreCount = 0
    var jikiOokisa = 50
    var tekiOkisa = 70
    var tamaOkisa = 30
    var tamaFrameIchi = 0
    var enemyTamaSpeed = 2.0    //デフォはこれにしといて、変えれるようにしよう

    val initialJikiX = 300 //初期位置
    val initialJikiY = 800 //初期位置

    var clickX = initialJikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = initialJikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置

    var jk =JikiJoho(jikiOokisa,initialJikiX, initialJikiY,tamaOkisa)


    var e = teki()
    var jt = jTama()
    var et = eTama()
    var et2 = eTama()



    init{

    }

    fun beginAnimation() {
        tsugiNoSyori()  //最初に一回だけ呼ばれる
    }

    fun tsugiNoSyori() {
        frame += 1  //繰り返し処理はここでやってる
        invalidate()

        clickShitaBshoNiIdou()        //自機の移動　処理
        e.x = tekiUgki(e.x)        //敵の移動　処理
        tamaSyori()        //自機の弾　処理
        tamaJikiSyori()     //自機の弾が当たったら、相手が消える処理をする
        enemyTama()        //敵の弾　処理
        enemyTamaAtatta()        //敵の弾が当たったら、敵の弾は消滅する
        et2.iro.color = Color.BLUE
        enemyTama2()        //敵の弾　処理
        enemyTamaAtatta2()


        handler.postDelayed({ tsugiNoSyori() }, 100)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(jk.x.toFloat(),jk.y.toFloat(),(jk.jikiOokisa/2).toFloat(),jk.iro) //自機の移動　処理
        canvas.drawRect(e.shikakuRectXY(e.x,e.y,e.Ookisa), e.iro)   //敵の移動　処理
        canvas.drawRect(jt.shikakuRectXY(jt.x,jt.y,jt.Ookisa), jt.iro)  //自機の弾　処理   //自機の弾が当て相手が消え処理
        canvas.drawRect(et.shikakuRectXY(et.x,et.y,et.Ookisa), et.iro)  //敵の弾　処理    //敵の弾が当たったら、敵の弾は消滅する
        canvas.drawRect(et2.shikakuRectXY(et2.x,et2.y,et2.Ookisa), et2.iro) //敵の弾　処理
    }

    fun enemyTama2(){
        //ここでtamaSpeedっていうのを設定している。他の弾にはない
        var tamaSpeed = 3.5
        var xhanai =650
        var yHani = 900
        var vx = jk.x - et2.x
        var vy = jk.y - et2.y

        var resetKyori = 500 //よけ始める距離
        if(vx<resetKyori && vx > -resetKyori && vy<resetKyori && vy > -resetKyori){ et2.homing = false }
        if (et2.homing == false) {
            vx = et2.zenkaiVect[0]
            vy = et2.zenkaiVect[1]
        }
        et2.zenkaiVect[0] = vx
        et2.zenkaiVect[1] = vy
        //敵の弾の移動
        val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        et2.x += ((vx / v)*10 * tamaSpeed).toInt()
        et2.y += ((vy / v)*10 * tamaSpeed).toInt()
        if (et2.x > xhanai || et2.x < 0){et2.zenkaiVect[0] = -vx }
        if (et2.y > yHani || et2.y < 0){et2.zenkaiVect[1] = -vy}
    }

    fun enemyTamaAtatta2(){
        val vx = et2.x - jk.x
        val vy = et2.y - jk.y
        if(et2.Ookisa == 30){
            dgCount += 1

            //弾２情報をリセット
            et2 = eTama()
            //以下２行を追加したら動いた。//なんかet2の値を取出でエラーが起きてる？//et2を作り直したらzenkaiVectだけでも設定してないとダメ
            et2.zenkaiVect[0] = e.x - jk.x //- et2.x
            et2.zenkaiVect[1] = e.y - jk.y //- et2.y
        }else{
            val atariKyori = jk.atariKyori(jk.jikiOokisa)
            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                et2.iro.color = Color.DKGRAY
                et2.Ookisa = 30
            }
        }
    }


    fun tamaJikiSyori(){
        if(jt.Ookisa == 30){
            jt = jTama()    //弾のエフェクトだけちょっとだしたい。
        }
        val vx = jt.x - e.x
        val vy = jt.y - e.y
        val atariKyori = jk.atariKyori(jk.jikiOokisa)

        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            jt.iro.color = Color.WHITE
            jt.Ookisa = 30
            e = teki()
            scoreCount += 1
        }
    }

    fun enemyTamaAtatta(){
        val vx = et.x - jk.x
        val vy = et.y - jk.y
        if(et.Ookisa == 30){
            dgCount += 1
            et = eTama()
            }else{
            val atariKyori = jk.atariKyori(jk.jikiOokisa)


            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                et.iro.color = Color.DKGRAY
                et.Ookisa = 30
            }

        }
    }


    fun tamaSyori(){
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()
        tamaFrameIchi += 1
        jt.y-= tamaPlus

        if(jt.y<5){
            tamaFrameIchi=0
        }        //画面の上部で消える

        if(tamaFrameIchi==20){
            tamaFrameIchi=0
        }        //20フレームでリセット

        if(tamaFrameIchi==0){
            jt=jTama()
        }
    }






    fun enemyTama(){
        var vx = jk.x - et.x
        var vy = jk.y - et.y

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




    fun eTama():IchiJoho{
        val m = IchiJoho(e.x,e.y,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.MAGENTA
        return m
    }

    fun jTama():IchiJoho{
       //弾をリセットする。最初の状態にする。
        val z = IchiJoho(jk.x,jk.y,10,jk.tamaOkisa)
        z.iro.style = Paint.Style.FILL
        z.iro.color = Color.GREEN
        return z
    }


    fun startSetUp(){

        jk = JikiJoho(initialJikiX,initialJikiY,jikiOokisa,tamaOkisa)
        jk.x = initialJikiX
        jk.y = initialJikiY
        e = teki()
        jt = jTama()
        et = eTama()
        et2 = eTama()
        frame = 0
        dgCount = 0
        scoreCount = 0
        jikiOokisa = 50

    }


    fun hyperShotPowerUp(){
        if (jk.jikiOokisa==200){
            jk.jikiOokisa = 50
        }else{
            jk.jikiOokisa = 200
        }
    }

    fun hyperPowerUp(){
        if (jk.jikiOokisa==200){
            jk.jikiOokisa = 50
        }else{
            jk.jikiOokisa = 200
        }
    }

    fun tekiHyperPowerUp(){
        if (e.Ookisa==140){
            e.Ookisa = 70
        }else{
            e.Ookisa = 140
        }
    }

    fun teki():IchiJoho{
        val e = IchiJoho(20,100,tekiOkisa,10)
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
        val saX = jk.x - clickX
        val saY = jk.y - clickY
        var x = jk.x
        var y = jk.y
        val speed = 2.5
        val plus = 10 * speed .toInt()

        if (saX >= -(plus) && saX <= plus){
            x = clickX
        }else {
            if (saX > 0) {
                x -= plus
            }
            if (saX < 0) {
                x += plus
            }
        }
        if (saY >= -plus && saY <= plus){
            y = clickY
        }else {
            if (saY > 0) {
                y -= plus
            }
            if (saY < 0) {
                y += plus
            }
        }

        jk.x = x
        jk.y = y
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

