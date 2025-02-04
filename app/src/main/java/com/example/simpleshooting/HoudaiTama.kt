package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class HoudaiTama {
    var isAppearance = false

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
    val irosikaku = Paint()


    val TAMA_NASI_STATE = 1
    val TAMA_SYUTUGEN_ATARANAI_STATE = 2
    val NORMAL_STATE = 3
    val TAMA_HIT_STATE = 4
    val TAMA_HIT_END_STATE = 5

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    fun syokika(){
        val xlist = listOf<Int>(100,150,200,250,300,350,400,450,500,550,600)
        val ylist = listOf<Int>(150,200,250,300,350,400,450,500,550,600,650,700,750,800,850)
        x = xlist.random()
        y = ylist.random()
        ookisa = initialOokisa
        timecount = 0

        hit = false
        zenkaix = x
        zenkaiy = y
        speed = 2.0

        iro.style = Paint.Style.STROKE


        irogray.style = Paint.Style.STROKE
        irogray.color = Color.DKGRAY
        irogray.strokeWidth = 12.0F


        irosikaku.style = Paint.Style.STROKE
        irosikaku.color = Color.LTGRAY
        irosikaku.strokeWidth = 3.0F

        status = TAMA_SYUTUGEN_ATARANAI_STATE
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

    fun tenmetuOsoi(){
        if (timecount % 5 == 0) {
            iro.style = Paint.Style.FILL
        }else{
            iro.style = Paint.Style.STROKE
        }
    }

    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                syokika()
            }
            TAMA_SYUTUGEN_ATARANAI_STATE -> {
                iro.color = Color.argb(100, 255, 200, 255)
                timecount()
                tenmetuOsoi()
                if(timecount>20){
                    status=NORMAL_STATE
                    iro.color = Color.RED
                }
            }

            NORMAL_STATE -> {
                ookisa -= 1
                timecount()
                tenmetuTamahenka()

                //んー、ここでヒットステイツで消えちゃうとだめなのかなー
                if(attaterukaCheck(jiki)){
                    gotoHitState()
                }else {
                    if (timecount == 40) {
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
        canvas.drawCircle(x.toFloat(),y.toFloat(),ookisa.toFloat(),iro)
        if(status==TAMA_SYUTUGEN_ATARANAI_STATE){drawSikaku(canvas)}
        if(status>3){ drawAtatteru(canvas)}
    }

    fun drawSikaku(canvas: Canvas) {

        canvas.drawCircle(x.toFloat(),y.toFloat(),(50-timecount % 5).toFloat(),irosikaku)
        canvas.drawCircle(x.toFloat(),y.toFloat(),(30-timecount % 5).toFloat(),irosikaku)
    }

    fun drawAtatteru(canvas: Canvas) {
         canvas.drawCircle(x.toFloat(),y.toFloat(),(ookisa*2).toFloat(),irogray)
         canvas.drawCircle(x.toFloat(),y.toFloat(),(ookisa*2-50).toFloat(),irogray)
         canvas.drawCircle(x.toFloat(),y.toFloat(),(ookisa*2-100).toFloat(),irogray)

    }

}