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

        nobiruhanni=250
        val top = y  - nobiruhanni
        val bottom = y
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun tatenagashikakuRectXY2(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        if (nobiruhanni<1){nobiruhanni=0}
        if (nobiruhanni>250){nobiruhanni=250}
        val top = y  - nobiruhanni
        val bottom = y
        val m = Rect(left, top, right,bottom)
        return m
    }
    fun draw(canvas: Canvas){
            canvas.drawRect(tatenagashikakuRectXY(), iro)  //自機
    }

    fun draw2(canvas: Canvas){
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
        iro.color = Color.WHITE
    }

    fun taiki(jiki:Jiki){
        timeCount += 1
        if(timeCount==10){
            x = jiki.x
            y = jiki.y
        }

    }

    //なんかわかんないからデバックモードとしてだしっぱにしたいな
    fun nobiru(jiki:Jiki) {
        x = jiki.x
        y = jiki.y
        if (timeCount == 30) { status = TAMA_NASI_STATE }
    }

    fun nobiru2(jiki:Jiki) {
        //なかなかイメージ通りだけど、剣なんだから行って帰ってこないとなぁ

        if (timeCount > 10) {
            x = jiki.x
            y = jiki.y
            if (timeCount < 20) { nobiruhanni += 5 * timeCount }
            if (timeCount >= 20 && timeCount < 30) { nobiruhanni -= 3 * timeCount }
            if (timeCount == 30) { status = TAMA_NASI_STATE }
        }
    }
    fun attaterukaCheck(teki:Teki):Boolean{
        val x1 = teki.x -teki.ookisa / 2
        val y1 = teki.y -teki.ookisa / 2
        val x2 = teki.x +teki.ookisa / 2
        val y2 = teki.y +teki.ookisa / 2
        val isXInside = (x >= x1 && x <= x2)
        if(isXInside){        iro.color = Color.GREEN }
        // (y  - nobiruhanni) 剣の先っぽ
        //全部を判定すると面倒だから、大体で真ん中二点くらいで適当に判定するとか？
        // 敵大きさの半分づつあがると、判定がトラック型になるんじゃないかな？

        var isYInside : Boolean
        isYInside = ((y  - nobiruhanni) >= y1 && (y  - nobiruhanni) <= y2)

        return isXInside && isYInside

    }
    fun gotoHitState(){        iro.color = Color.RED }
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
                    nobiru(jiki)                //ひとつ上に弾を移動

                    if (attaterukaCheck(teki)) {                     //当たっているかチェック
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

}




