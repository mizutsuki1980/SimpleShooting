package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiKen(jiki:Jiki) {
    var x = jiki.x
    var y = jiki.y
    val iro = Paint()
    var ookisa : Int
    var bunsin = 0
    val bunsinMax = 20
    var hit = false
    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.WHITE
        x = jiki.x
        y = jiki.y
        ookisa = 10
        bunsin = 0
        status = TAMA_NASI_STATE
    }

    fun bunsinHueru() {
        bunsin += 1
        if (bunsin==bunsinMax){bunsin=0}
    }
    fun draw(canvas: Canvas, jiki: Jiki) {
        for (a in 0..<bunsin) {
            var kyori = ookisa * a + ookisa + (ookisa/2)
            canvas.drawCircle(jiki.x.toFloat(),jiki.y-kyori.toFloat(),(ookisa/2).toFloat(),iro)
       }
    }

    fun checkOne(jiki:Jiki,teki:Teki,kyori:Int):Boolean{
        val jx = jiki.x
        val jy = jiki.y - kyori
        val tleft = teki.x -teki.ookisa / 2    //左肩
        val tright = teki.x +teki.ookisa / 2    //右肩
        val isXInside =  (jx >= tleft && jx <= tright)
        val ttop = teki.y -teki.ookisa / 2    //上辺
        val tbuttom = teki.y +teki.ookisa / 2    //下辺
        val isYInside =  (jy >= ttop && jy <= tbuttom)
        return (isXInside && isYInside)
    }

    fun attaterukaCheck(jiki:Jiki,teki:Teki):Boolean{
        var flag = false
        for(a in 0..<bunsin) {
            val kyori = ookisa * a + ookisa + (ookisa/2)
            if(flag){}else{
                flag = checkOne(jiki,teki,kyori)
            }
        }
        return flag
    }


    fun nextFrame(jiki: Jiki, teki: Teki, isFirstMove: Boolean) {
        if (isFirstMove) {
            when (status) {
                TAMA_NASI_STATE -> {
                    jikiKaraStart()         // 現在の自機の場所に移動してリセット
                }

                NORMAL_STATE -> {
                    bunsinHueru()
                    if (attaterukaCheck(jiki, teki)) {                     //当たっているかチェック
                        gotoHitState()
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
    }

    fun jikiKaraStart() {
        bunsin = 0
        hit = false
        status = NORMAL_STATE
        iro.color = Color.WHITE
    }

    fun gotoHitState() {
        iro.color = Color.RED
        status = TAMA_HIT_STATE
    }

    fun hitCountSyori() {
        hit = true //ヒットは１回のみカウントするので、すぐにfalseに
        status = TAMA_HIT_END_STATE
    }

    fun motoniModosu() {
        hit = false //ヒットは１回のみカウントするので、すぐにfalseに
        status = TAMA_NASI_STATE
    }
}




