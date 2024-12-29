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
    var tekiOkisa = 70
    var tamaOkisa = 30
    var enemyTamaSpeed = 2.0    //デフォはこれにしといて、変えれるようにしよう
    var isFirstMove = false //動きだしたら弾も出るようにする


    val initialJikiX = 300 //初期位置
    val initialJikiY = 800 //初期位置

    var clickX = initialJikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = initialJikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置

    var jiki =JikiJoho(initialJikiX, initialJikiY,tamaOkisa)
    var jt = JikiTama(jiki.x,jiki.y)


    var e = teki()
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
        jiki.clickShitaBshoNiIdou(clickX,clickY)
        e.x = tekiUgki(e.x)        //敵の移動　処理

        // 自機の弾が当たったら、相手が消える処理をする
        if(isFirstMove){
            if (jt.tamaSyori(jiki.atariKyori(), jiki, e)) {
                e = teki()
                scoreCount += 1
            }
        }
        enemyTama()        //敵の弾　処理
        enemyTamaAtatta()        //敵の弾が当たったら、敵の弾は消滅する
        et2.iro.color = Color.BLUE
        enemyTama2()        //敵の弾　処理
        enemyTamaAtatta2()
        handler.postDelayed({ tsugiNoSyori() }, 100)
    }




    override fun onDraw(canvas: Canvas) {
        jiki.draw(canvas)
        jt.draw(canvas)
        //canvas.drawRect(jt.shikakuRectXY(), jt.iro)  //自機の弾　処理   //自機の弾が当て相手が消え処理
//123
        //main123を変更
        //main123というブランチを作って、ここで作業することは、たぶんできる。
        //これでいいんだろうか？
        canvas.drawRect(e.shikakuRectXY(e.x,e.y,e.ookisa), e.iro)   //敵の移動　処理

        canvas.drawRect(et.shikakuRectXY(et.x,et.y,et.ookisa), et.iro)  //敵の弾　処理    //敵の弾が当たったら、敵の弾は消滅する
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
            et2.zenkaiVect[0] = e.x - jiki.x //- et2.x
            et2.zenkaiVect[1] = e.y - jiki.y //- et2.y
        }else{
            val atariKyori = jiki.atariKyori()
            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                et2.iro.color = Color.DKGRAY
                et2.ookisa = 30
            }
        }
    }



    fun enemyTamaAtatta(){
        val vx = et.x - jiki.x
        val vy = et.y - jiki.y
        if(et.ookisa == 30){
            dgCount += 1
            et = eTama()
            }else{
            val atariKyori = jiki.atariKyori()
            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                et.iro.color = Color.DKGRAY
                et.ookisa = 30
            }
        }
    }

    fun enemyTama(){
        var vx = jiki.x - et.x
        var vy = jiki.y - et.y

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


    fun startSetUp(){

        jiki =JikiJoho(initialJikiX, initialJikiY,tamaOkisa)
        clickX = initialJikiX
        clickY = initialJikiY

        e = teki()
        jt = JikiTama(jiki.x,jiki.y)
        et = eTama()
        et2 = eTama()
        frame = 0
        dgCount = 0
        scoreCount = 0

    }



    fun tekiHyperPowerUp(){
        if (e.ookisa==140){
            e.ookisa = 70
        }else{
            e.ookisa = 140
        }
    }

    fun teki():IchiJoho{
        val e = IchiJoho(20,100,tekiOkisa,10)
        e.iro.style = Paint.Style.FILL
        e.iro.color = Color.CYAN
        return e
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

