package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiKen(jiki:Jiki) {
    var x = jiki.x
    var y = jiki.y
    val iro = Paint()
    val ookisa = 10
    var nobiruhanni = 10
    var timeCount = 0

    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.WHITE
    }

    fun tatenagashikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - nobiruhanni
        val bottom = y + nobiruhanni
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun draw(canvas: Canvas){
        if(timeCount>10) {
            canvas.drawRect(tatenagashikakuRectXY(), iro)  //自機
        }
    }


    fun jikiKaraStart(jiki:Jiki){
        x = jiki.x
        y = jiki.y
        nobiruhanni = 10
        timeCount = 0
        status =  NORMAL_STATE
    }

    fun taiki(jiki:Jiki){
        timeCount += 1
        if(timeCount==10){
            x = jiki.x
            y = jiki.y
        }

    }

    fun nobiru(){
        if(timeCount>10){
            y -= 5*timeCount
            nobiruhanni += 5*timeCount
        }

    }

    fun attaterukaCheck(teki:Teki):Boolean{return true}
    fun gotoHitState(){}
    fun hitCountSyori(){}
    fun motoniModosu(){}

    fun nextFrame(jiki:Jiki,teki:Teki,isFirstMove:Boolean) {
        if(isFirstMove) {
            when (status) {
                TAMA_NASI_STATE -> {
                    jikiKaraStart(jiki)         // 現在の自機の場所に移動してリセット
                }

                NORMAL_STATE -> {
                    taiki(jiki)
                    nobiru()                //ひとつ上に弾を移動

                    if (attaterukaCheck(teki)) {                     //当たっているかチェック
                        gotoHitState()
                    }
                    if (y < 5) {
                        status = TAMA_NASI_STATE
                    } // 画面外に出たら無しの状態に一旦遷移
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

}




