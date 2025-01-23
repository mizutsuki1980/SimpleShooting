package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class HoudaiTama {
    // 弾も自機も敵も、全部〇の方が当たり判定にベクトルが使えて便利じゃね？と思った。
    //とりあえず砲台の弾は〇にする。

    //固定の場所から打ち出される砲台みたいなイメージ
    //山なりな軌道を作ってみたい。

    var x = 500
    var y = 500
    val initialOokisa = 50 //ここで大きさを初期設定

    var ookisa = initialOokisa
    var timecount = 0
    var hit = false
    var zenkaix = 500
    var zenkaiy = 500
    var speed = 2.0
    val iro = Paint()

    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    fun syokika(){
        x = 500
        y = 500
        ookisa = initialOokisa
        timecount = 0

        hit = false
        zenkaix = 500
        zenkaiy = 500
        speed = 2.0

        iro.style = Paint.Style.STROKE
        iro.color = Color.RED
        status = NORMAL_STATE
    }

    init{
        syokika()
    }



    fun moveOne(){
        x -= 1
        y -= 1
        ookisa -= 3
    }

    fun attaterukaCheck(jiki:Jiki):Boolean {
        val vx = x - jiki.x
        val vy = y - jiki.y
        val kyori = Math.sqrt((vx * vx) + (vy * vy) .toDouble()) + ookisa/2.toDouble()
        //ホーミング避けるのきつくね？ということで、すこし小さくします
        val atarikyori = (jiki.ookisa-5).toDouble()
        if (kyori < atarikyori){
            return true
        }else{
            return false
        }
    }


    fun timecount(){
        timecount += 1
    }

    fun tenmetuTamahenka(){
        if (timecount % 2 == 0) {
            iro.style = Paint.Style.FILL
        }else{
            iro.style = Paint.Style.STROKE
        }
        ookisa -= 3

    }


    fun irokae(){
        iro.style = Paint.Style.FILL
        iro.color = Color.RED
        status = NORMAL_STATE
    }
    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                syokika()
                status = NORMAL_STATE
            }

            NORMAL_STATE -> {
                timecount()
                tenmetuTamahenka()
                if(attaterukaCheck(jiki)){
                    gotoHitState()
                }else {
                    if (timecount == 10) {
                        status = TAMA_HIT_STATE
                    }
                }
            }
            TAMA_HIT_STATE -> {
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                motoniModosu()  // もとに戻す
            }
        }
    }

    fun gotoHitState(){
        iro.color = Color.DKGRAY
        status = TAMA_HIT_STATE
    }
    fun hitCountSyori(){
        hit = true
        status = TAMA_HIT_END_STATE
    }
    fun motoniModosu(){
        hit = false //hitだけ先に戻さないと二回カウントされてしまう。
        status = TAMA_NASI_STATE
    }

    fun draw(canvas: Canvas) {
            canvas.drawCircle(x.toFloat(),y.toFloat(),ookisa.toFloat(),iro)
        }


}