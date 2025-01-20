package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiKen(jiki:Jiki) {
    var x = jiki.x
    var y = jiki.y
    val iro = Paint()
    var ookisa = 30
    var nobiruhanni = 10
    var nagasa : Int
    var timeCount = 0
    var hit = false
    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    //長さって考えを捨てないとダメなのかもしれない。
    //単純に増えていく仕組みは、自機から１個、２個、３個が絶対いい。
    //時間経過とともに１個づつふえていく、という風に変更する。


    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.WHITE
        x = jiki.x
        y = jiki.y
        ookisa = 30
        nagasa = 150
        status = TAMA_NASI_STATE
    }

    fun timer(){
        timeCount += 1
        if (timeCount > 30){
            timeCount = 0
        }
    }

    fun kengaNobiru(jiki:Jiki) {
        if (timeCount == 0){ nagasa = 150 }
        if (timeCount > 10 && timeCount < 20) {
            nagasa += 10
        }
    }


    fun attaterukaKurikaesiCheck(jiki:Jiki,teki:Teki,kyori:Int):Boolean{
        val jx = jiki.x
        val jy = jiki.y - kyori

        var isXInside = false
        val tleft = teki.x -teki.ookisa / 2    //左肩
        val tright = teki.x +teki.ookisa / 2    //右肩
        isXInside =  (jx >= tleft && jx <= tright)

        var isYInside = false
        val ttop = teki.y -teki.ookisa / 2    //上辺
        val tbuttom = teki.y +teki.ookisa / 2    //下辺
        isYInside =  (jy >= ttop && jy <= tbuttom)

        return (isXInside && isYInside)
    }

    //丸の先端が当たった時、当たってない。
    //当たり判定の小さい四角をひとつ、おいてはどうか？

    fun attaterukaCheck(jiki:Jiki,teki:Teki):Boolean{
        var flag = false

        //ここは当たり判定
        var kyori = nagasa - ookisa/2
        for(a in 0..<5) {
            kyori =  nagasa - (ookisa) * a
            if(flag){}else{
                flag = attaterukaKurikaesiCheck(jiki,teki,kyori)
            }
        }
        return flag
    }

    fun draw(canvas: Canvas,jiki: Jiki){
        //一個目が最後尾なのかー、じゃぁ自分の前まで後から補完するとか？

        //ここは描画
        var kyori :Int
        for(a in 0..<5) {
            kyori =  nagasa - (ookisa) * a
            canvas.drawCircle(jiki.x.toFloat(),jiki.y-kyori.toFloat(),(ookisa/2).toFloat(),iro)
        }
    }



    fun nextFrame(jiki:Jiki,teki:Teki,isFirstMove:Boolean) {
        if(isFirstMove) {
            when (status) {
                TAMA_NASI_STATE -> {
                    jikiKaraStart()         // 現在の自機の場所に移動してリセット
                }
                NORMAL_STATE -> {
                    timer()
                    kengaNobiru(jiki)                //ひとつ上に弾を移動
                    if (attaterukaCheck(jiki,teki)) {                     //当たっているかチェック
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

    fun jikiKaraStart(){
        nobiruhanni = 10
        nagasa = 150
        timeCount = 0
        hit = false
        status =  NORMAL_STATE
        iro.color = Color.WHITE
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


}




