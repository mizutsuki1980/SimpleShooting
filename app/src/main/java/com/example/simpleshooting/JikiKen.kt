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
    var hit = false
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
        hit = false
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
        var isXInside = false
        val tleft = teki.x -teki.ookisa / 2    //左肩
        val tright = teki.x +teki.ookisa / 2    //右肩
        isXInside =  (x >= tleft && x <= tright)
        return isXInside
    }


    fun gotoHitState(){
        iro.color = Color.RED
        status = TAMA_HIT_STATE
    }

    fun hitCountSyori(){
        hit = true //ヒットは１回のみカウントするので、すぐにfalseに
        status = TAMA_HIT_END_STATE
    }
    fun motoniModosu(){
        hit = false //ヒットは１回のみカウントするので、すぐにfalseに
        status = TAMA_NASI_STATE
    }

    //こういう感じに作ると３フレーム判定につかうので、３フレームで１ダメージみたいになる。
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




