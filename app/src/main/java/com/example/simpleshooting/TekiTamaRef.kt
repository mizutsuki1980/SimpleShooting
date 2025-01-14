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
    var x2 = x
    var y2 = y
    var x3 = x
    var y3 = y

    //反射の計算に使う
    var holdx = 0
    var holdy = 0

    init{
        x = teki.x
        y = teki.y
        ookisa = 10
        isfirst = true
        hit = false
        spx = x
        spy = y

        //線を設定
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubMae.style = Paint.Style.STROKE
        irosubMae.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubMae.strokeWidth = 4.0f
        irosubAto.style = Paint.Style.STROKE
        irosubAto.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubAto.strokeWidth = 1.5f
    }


    fun move(jiki:Jiki){
        kakudoKeisan(jiki)  //移動する角度を決める
        kisekiKeisan()  //先に軌跡の計算をしないとダメ、ｘｙが動いてしまう。
        tamaIdo()   //弾が移動する
        reflectKeisan() //画面端についたら反転する

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
        x3 = x2
        y3 = y2
        x2 = x
        y2 = y
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

}