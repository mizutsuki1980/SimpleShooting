package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    //ピンクの敵の弾がまったく動かなくなる場合がある。
    //青い弾もいなくなることがある

    var frame = 0
    var dgCount = 0
    var scoreCount = 0
    var tamaOkisa = 30
    var isFirstMove = false //動きだしたら弾も出るようにする


    val initialJikiX = 300 //初期位置
    val initialJikiY = 800 //初期位置

    var clickX = initialJikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = initialJikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置

    var jiki =Jiki(initialJikiX, initialJikiY,tamaOkisa)
    var jikiTama = JikiTama(jiki.x,jiki.y-170)
    //ここでも-170してるのは、なんか面倒だしわかりにくいなー

    var teki = Teki()
    var tekiTama = TekiTama(teki.x,teki.y)
    var tekiTamaRef = TekiTamaRef(jiki,teki)
    var hpCounter = HPCounter()

    init{

    }

    fun beginAnimation() {
        tsugiNoSyori()  //最初に一回だけ呼ばれる
    }

    fun tsugiNoSyori() {
        frame += 1  //繰り返し処理はここでやってる
        invalidate()

        jiki.move(clickX,clickY-170) //クリックした場所から上に170の場所に移動する。指にかかって見えない為。

        teki.yokoIdo()  //敵の移動　処理

        // 自機の弾が当たったら、カウントを増やして相手が消える処理をする

        if(isFirstMove){
            jikiTama.nextFrame(jiki,teki)
            if (jikiTama.hit) {
                teki.hp -= 1
                if(teki.hp==0){
                    scoreCount += 1
                    teki = Teki()
                }

            }
        }

        tekiTama.move(jiki,teki)  //敵の弾　処理
        tekiTama.atariCheck(jiki) //敵の弾が当たっていたらカウントを増やして消える

        if (tekiTama.hit){
            dgCount += 1
            jiki.hp -= 1
            tekiTama = TekiTama(teki.x,teki.y)
        }

        tekiTamaRef.move(jiki)
        tekiTamaRef.atariCheck(jiki)
        if (tekiTamaRef.hit){
            dgCount += 1
            jiki.hp -= 1
            tekiTamaRef = TekiTamaRef(jiki,teki)
        }

        handler.postDelayed({ tsugiNoSyori() }, 100)
    }




    override fun onDraw(canvas: Canvas) {
        jiki.draw(canvas)   //自機の処理
        teki.draw(canvas) //敵jikiTamaの移動　処理
        tekiTama.draw(canvas) //敵の追尾弾の移動　処理
        tekiTamaRef.draw(canvas) //敵の反射弾の移動　処理
        // 自機の弾を最後に描画した方がそれっぽく見える
        if(isFirstMove){ jikiTama.draw(canvas)}     //自機の弾の処理
        hpCounter.draw(canvas,jiki)
    }


    fun startSetUp(){

        jiki =Jiki(initialJikiX, initialJikiY,tamaOkisa)
        jikiTama = JikiTama(jiki.x,jiki.y)
        teki = Teki()
        tekiTama = TekiTama(teki.x,teki.y)
        tekiTamaRef = TekiTamaRef(jiki,teki)

        clickX = initialJikiX
        clickY = initialJikiY

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

