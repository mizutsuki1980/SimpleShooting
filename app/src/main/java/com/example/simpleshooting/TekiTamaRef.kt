package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect

class TekiTamaRef(jiki:Jiki, teki:Teki) {
    //reflection
    var isAppearance = false
    var x = teki.x
    var y = teki.y
    var ookisa:Int
    var isfirst :Boolean
    var hit :Boolean
    var spx : Int
    var spy : Int
    val speed = 3.0

    val iro = Paint()
    val irosubMae = Paint()
    val irosubAto = Paint()

    //軌跡の計算に使う
    var x1 = x
    var y1 = y
    var x2 = x
    var y2 = y
    var x3 = x
    var y3 = y

    //反射の計算に使う
    var holdx = 0
    var holdy = 0


    //状態遷移関連
    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態


    init{
        x = teki.x
        y = teki.y
        ookisa = 10
        isfirst = true
        hit = false
        spx = x
        spy = y

        iro.style = Paint.Style.FILL
        iro.color = Color.argb(255, 255, 115, 115)

        //線を設定
        irosubMae.style = Paint.Style.STROKE
        irosubMae.color = Color.argb(255, 255, 115, 115)   //argb(255, 255, 255, 200)
        irosubMae.strokeWidth = 4.0f
        irosubAto.style = Paint.Style.STROKE
        irosubAto.color = Color.argb(255, 255, 115, 115)   //argb(255, 255, 255, 200)
        irosubAto.strokeWidth = 1.5f
    }

    fun moveOne(jiki:Jiki){
        kakudoKeisan(jiki)  //移動する角度を決める
        kisekiKeisan()  //先に軌跡の計算をしないとダメ、ｘｙが動いてしまう。
        tamaIdo()   //弾が移動する
        reflectKeisan() //画面端についたら反転する
    }

    fun moveHitedOne(jiki:Jiki){
        kakudoKeisan(jiki)  //移動する角度を決める
        kisekiKeisan()  //先に軌跡の計算をしないとダメ、ｘｙが動いてしまう。
        reflectKeisan() //画面端についたら反転する
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

    fun kakudoKeisan(jiki:Jiki){
        if (isfirst) {
            holdx = jiki.x - x
            holdy = jiki.y - y
            isfirst = false
        }else {
            //ここに入るともうｖｘは動かない。反転するときにだけマイナス１の掛け算になる。
            holdx = spx
            holdy = spy
        }
        spx = holdx
        spy = holdy
    }

    fun kisekiKeisan(){
        // 逆順にいれていかないとダメ
        x3 = x2
        y3 = y2
        x2 = x1
        y2 = y1
        x1 = x
        y1 = y

    }

    fun tamaIdo(){
        //TekiTamaRefの弾の移動する加算
        val kyori = Math.sqrt((holdx * holdx) + (holdy * holdy) .toDouble())
        x += ((spx / kyori)*10 * speed).toInt()
        y += ((spy / kyori)*10 * speed).toInt()
    }
    fun reflectKeisan(){
        val xhanai =650
        val yHani = 900
        //右端でも左端でも対応している優れモノ
        if (x > xhanai || x < 0){spx = -holdx }
        //上端でも下端でも対応している優れモノ
        if (y > yHani || y < 0){spy = -holdy}

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
        canvas.drawLine(x2.toFloat(),y2.toFloat(),x.toFloat(),y.toFloat(),irosubMae) //軌跡１の表示
        canvas.drawLine(x3.toFloat(),y3.toFloat(),x2.toFloat(),y2.toFloat(),irosubAto)  //軌跡２の表示
    }

    fun tekiKaraStart(teki:Teki){
        x = teki.x
        y = teki.y

        x1 = x
        y1 = y
        x2 = x
        y2 = y
        x3 = x
        y3 = y

        ookisa = 10
        isfirst = true
        hit = false
        spx = x
        spy = y

        iro.style = Paint.Style.FILL
        iro.color = Color.argb(255, 255, 115, 115)   //argb(255, 255, 255, 200)
        status = NORMAL_STATE
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
            }
            TAMA_HIT_STATE -> {
                moveHitedOne(jiki)   //めりこむ感じ
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                moveHitedOne(jiki)   //めりこむ感じ
                motoniModosu()  // もとに戻す
            }
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

}