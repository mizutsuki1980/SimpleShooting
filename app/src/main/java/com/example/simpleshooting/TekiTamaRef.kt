package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect

class TekiTamaRef(jiki:Jiki, teki:Teki) {
    //reflection
    var x = teki.x
    var y = teki.y
    val iro = Paint()
    var ookisa:Int
    var hit :Boolean
    var isFirst :Boolean
    var zenkaix : Int
    var zenkaiy : Int
    var speed : Double

    val irosubMae = Paint()
    val irosubAto = Paint()


    var kisekiMae_x : Int
    var kisekiMae_y : Int
    var kisekiAto_x : Int
    var kisekiAto_y : Int



    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    var kakudo = 0.0

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE
        hit = false
        isFirst = true
        zenkaix = teki.x
        zenkaiy = teki.y

        kisekiMae_x = zenkaix
        kisekiMae_y = zenkaiy

        kisekiAto_x = kisekiMae_x
        kisekiAto_y = kisekiMae_y

        speed = 3.0

        //線を設定
        irosubMae.style = Paint.Style.STROKE
        irosubMae.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubMae.strokeWidth = 4.0f

        irosubAto.style = Paint.Style.STROKE
        irosubAto.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubAto.strokeWidth = 1.5f
    }


    fun tekiKaraStart(teki:Teki){
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE
        hit = false
        isFirst = true
        zenkaix = teki.x
        zenkaiy = teki.y

        kisekiMae_x = zenkaix
        kisekiMae_y = zenkaiy

        kisekiAto_x = kisekiMae_x
        kisekiAto_y = kisekiMae_y

        speed = 3.0

        status = NORMAL_STATE
    }
    fun kisekiWoTukuru(){
        kisekiAto_x = kisekiMae_x
        kisekiAto_y = kisekiMae_y
        kisekiMae_x = x
        kisekiMae_y = y

    }
    fun kakudoTukuru(vx:Int,vy:Int){
        if (isFirst) {
            kakudo = Math.sqrt((vx * vx) + (vy * vy).toDouble())
            isFirst = false
        }
    }

    fun moveOne(jiki:Jiki){
        kisekiAto_x = kisekiMae_x
        kisekiAto_y = kisekiMae_y
        kisekiMae_x = x
        kisekiMae_y = y



        val xhanai =650
        val yHani = 900
        var vx = jiki.x - x
        var vy = jiki.y - y

        val resetKyori = 500 //よけ始める距離
        var homing = true //とりあえずtrueを入れる
        if(vx<resetKyori && vx > -resetKyori && vy<resetKyori && vy > -resetKyori){ homing = false }
        if (homing == false) {
            vx = zenkaix
            vy = zenkaiy
        }
        zenkaix = vx
        zenkaiy = vy
        //敵の弾の移動
        val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        x += ((vx / v)*10 * speed).toInt()
        y += ((vy / v)*10 * speed).toInt()
        if (x > xhanai || x < 0){zenkaix = -vx }
        if (y > yHani || y < 0){zenkaiy = -vy}
    }


    fun moveOne2(jiki:Jiki){
        //ｘ、ｙが変化する前に、これらに数値を入れることで軌跡のデータになる。
        kisekiWoTukuru()
        var vx = jiki.x - x
        var vy = jiki.y - y
        kakudoTukuru(vx,vy)
        val v = kakudo
        x += ((vx / v)*10 * speed).toInt()
        y += ((vy / v)*10 * speed).toInt()

        //ここ謎、なんでzennkaiにマイナスで入れてんの？でもこれないとダメ。
        if (x > 650 || x < 0){zenkaix = -vx }
        if (y > 900 || y < 0){zenkaiy = -vy}

    }

    fun moveOne1(jiki:Jiki){
        // なんだかよくわからなくなってる
        //まず最初だけ角度が決まる。
        //敵位置から自機位置に向かう。
        //そのあとは角度は変わらない。ずっと同じ角度で動き続ける。
        //kakudoはDouble
        // んーなんか意味わからんくなってきた。作り直すか。

        kisekiAto_x = kisekiMae_x
        kisekiAto_y = kisekiMae_y
        kisekiMae_x = x
        kisekiMae_y = y

        val xhanai =650
        val yHani = 900

        var vx = jiki.x - x
        var vy = jiki.y - y

        if (isFirst) {
            kakudo = Math.sqrt((vx * vx) + (vy * vy).toDouble())
            isFirst = false
        }
        val v = kakudo

        vx = zenkaix
        vy = zenkaiy

        zenkaix = vx
        zenkaiy = vy

        //最初だけ角度が決める
        x += ((vx / v)*10 * speed).toInt()
        y += ((vy / v)*10 * speed).toInt()
        if (x > xhanai || x < 0){zenkaix = -vx }
        if (y > yHani || y < 0){zenkaiy = -vy}
    }

    fun attaterukaCheck(jiki:Jiki):Boolean {
        val vx = x - jiki.x
        val vy = y - jiki.y
        val kyori = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        val atarikyori = (jiki.ookisa).toDouble()
        if (kyori < atarikyori){
            return true
        }else{
            return false
        }
    }


    fun gotoHitState(){
        iro.color = Color.DKGRAY
        ookisa = 30
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


    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                tekiKaraStart(teki)         //最初のリセット処理
            }
            NORMAL_STATE -> {
                moveOne(jiki)                //自機にひとつ近づくように弾を移動
                if(attaterukaCheck(jiki)) {                     //自機に当たっているかチェック
                    gotoHitState()
                }
                //画面外なら、最初へ状態遷移
                if (x > 690 || x < 0 || y > 1050 || y < 0){ status = TAMA_NASI_STATE }
            }
            TAMA_HIT_STATE -> {
                moveOne(jiki)   //めりこむ感じ
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                moveOne(jiki)   //めりこむ感じ
                motoniModosu()  // もとに戻す
            }
        }
    }

    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRectXY(), iro)
        canvas.drawLine(kisekiMae_x.toFloat(),kisekiMae_y.toFloat(),x.toFloat(),y.toFloat(),irosubMae) //軌跡１の表示
        canvas.drawLine(kisekiAto_x.toFloat(),kisekiAto_y.toFloat(),kisekiMae_x.toFloat(),kisekiMae_y.toFloat(),irosubAto)  //軌跡２の表示
    }

}