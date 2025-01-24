package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class HoudaiTama {

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
    val irogray = Paint()

    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    //ランダムに出現させたい
    fun syokika(){
        val xlist = listOf<Int>(10,50,150,200,250,300,350,400,450,500,550,600,650)
        val ylist = listOf<Int>(50,150,200,250,300,350,400,450,500,550,600,650,700,750,800,850,900,950)
        x = xlist.random()
        y = ylist.random()
        ookisa = initialOokisa
        timecount = 0

        hit = false
        zenkaix = x
        zenkaiy = y
        speed = 2.0

        iro.style = Paint.Style.STROKE
        iro.color = Color.RED
        status = NORMAL_STATE

        irogray.style = Paint.Style.STROKE
        irogray.color = Color.DKGRAY

    }

    init{
        syokika()
    }

    fun attaterukaCheck(jiki:Jiki):Boolean {
        val vx = x - jiki.x
        val vy = y - jiki.y
        val kyori = Math.sqrt((vx * vx) + (vy * vy) .toDouble()) + ookisa/2.toDouble()
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
    }

    //もうさ、あたったら新しい〇つくっちゃえばよくね？
    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                syokika()
            }

            NORMAL_STATE -> {
                ookisa -= 3
                timecount()
                tenmetuTamahenka()
                if(attaterukaCheck(jiki)){
                    gotoHitState()
                }else {
                    if (timecount == 10) {
                        status = TAMA_NASI_STATE
                    }
                }
            }
            TAMA_HIT_STATE -> {
                tenmetuTamahenka()
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                tenmetuTamahenka()
                motoniModosu()  // もとに戻す
            }
        }
    }
    fun gotoHitState(){
        iro.color = Color.DKGRAY
        ookisa = initialOokisa
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

    //まぁいっか。わかるし。
    fun draw(canvas: Canvas) {
        if(status>2){ canvas.drawCircle(x.toFloat(),y.toFloat(),100.toFloat(),irogray)}
        canvas.drawCircle(x.toFloat(),y.toFloat(),ookisa.toFloat(),iro)
    }


}