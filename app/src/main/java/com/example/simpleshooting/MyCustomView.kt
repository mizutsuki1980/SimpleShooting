package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    //ピンクの敵の弾がまったく動かなくなる場合がある。

    var frame = 0
    var dgCount = 0
    var scoreCount = 0
    var tamaOkisa = 30
    var enemyTamaSpeed = 2.0    //デフォはこれにしといて、変えれるようにしよう
    var isFirstMove = false //動きだしたら弾も出るようにする


    val initialJikiX = 300 //初期位置
    val initialJikiY = 800 //初期位置

    var clickX = initialJikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = initialJikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置

    var jiki =JikiJoho(initialJikiX, initialJikiY,tamaOkisa)
    var jt = JikiTama(jiki.x,jiki.y)
    var teki = Teki()
    var tt = TekiTama(teki.x,teki.y)
    var ttr = TekiTamaRef(teki.x,teki.y)



    var et2 = eTama()



    init{

    }

    fun beginAnimation() {
        tsugiNoSyori()  //最初に一回だけ呼ばれる
    }

    fun tsugiNoSyori() {
        frame += 1  //繰り返し処理はここでやってる
        invalidate()
        jiki.clickShitaBshoNiIdou(clickX,clickY)
        teki.tekiYokoIdo()  //敵の移動　処理
        // 自機の弾が当たったら、カウントを増やして相手が消える処理をする
        if(isFirstMove){
            if (jt.tamaSyoriTekiJoho( jiki, teki)) {
                teki = Teki()
                scoreCount += 1
            }
        }
        tt.tekiTamaMove(jiki,teki)  //敵の弾　処理
        tt.tekiTamaAtatta(jiki) //敵の弾が当たっていたらカウントを増やして消える
        if (tt.hit){
            dgCount += 1
            tt = TekiTama(teki.x,teki.y)
        }


        ttr.tekiTamaRefMove(jiki)
        enemyTama2()        //敵の弾　処理
        ttr.tekiTamaRefAtatta(jiki,teki)
        if (ttr.hit){
            dgCount += 1
            ttr = TekiTamaRef(teki.x,teki.y)
        }

        enemyTamaAtatta2()


        handler.postDelayed({ tsugiNoSyori() }, 100)
    }




    override fun onDraw(canvas: Canvas) {
        jiki.draw(canvas)   //自機の処理
        jt.draw(canvas)     //自機の弾の処理
        teki.draw(canvas) //敵の移動　処理

        tt.draw(canvas) //敵の追尾弾の移動　処理
        ttr.draw(canvas) //敵の反射弾の移動　処理

        canvas.drawRect(et2.shikakuRectXY(et2.x,et2.y,et2.ookisa), et2.iro) //敵の弾　処理
    }

    fun enemyTama2(){
        //ここでtamaSpeedっていうのを設定している。他の弾にはない
        var tamaSpeed = 3.5
        var xhanai =650
        var yHani = 900
        var vx = jiki.x - et2.x
        var vy = jiki.y - et2.y

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
        val vx = et2.x - jiki.x
        val vy = et2.y - jiki.y
        if(et2.ookisa == 30){
            dgCount += 1

            //弾２情報をリセット
            et2 = eTama()
            //以下２行を追加したら動いた。//なんかet2の値を取出でエラーが起きてる？//et2を作り直したらzenkaiVectだけでも設定してないとダメ
            et2.zenkaiVect[0] = teki.x - jiki.x //- et2.x
            et2.zenkaiVect[1] = teki.y - jiki.y //- et2.y
        }else{
            val atariKyori = jiki.atariKyori()
            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                et2.iro.color = Color.DKGRAY
                et2.ookisa = 30
            }
        }
    }




    fun eTama():IchiJoho{
        val m = IchiJoho(teki.x,teki.y,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.MAGENTA
        return m
    }


    fun startSetUp(){

        jiki =JikiJoho(initialJikiX, initialJikiY,tamaOkisa)
        jt = JikiTama(jiki.x,jiki.y)
        teki = Teki()
        tt = TekiTama(teki.x,teki.y)

        clickX = initialJikiX
        clickY = initialJikiY


        et2 = eTama()
        frame = 0
        dgCount = 0
        scoreCount = 0

    }



    fun tekiHyperPowerUp(){
        if (teki.ookisa==140){
            teki.ookisa = 70
        }else{
            teki.ookisa = 140
        }
    }


    fun tekiUgki(x:Int):Int{
        val tekiSpeed = 10
        var xx = x
        if(xx<800){ xx += tekiSpeed }
        if(xx >= 800) { xx = -100 }
        return xx
    }




    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isFirstMove = true
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            isFirstMove = true
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            isFirstMove = true
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }
}

