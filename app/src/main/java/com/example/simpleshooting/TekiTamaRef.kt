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
    var isfirst :Boolean
    var hit :Boolean
    var spx : Int
    var spy : Int
    var speed : Double

    val irosubMae = Paint()
    val irosubAto = Paint()

    //軌跡の計算に使う
    var x2 : Int
    var y2 : Int
    var x3 : Int
    var y3 : Int

    //反射の計算に使う
    var vx = 0
    var vy = 0

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE


        isfirst = true

        hit = false
        spx = teki.x
        spy = teki.y

        x2 = spx
        y2 = spy

        x3 = x2
        y3 = y2

        speed = 3.0

        //線を設定
        irosubMae.style = Paint.Style.STROKE
        irosubMae.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubMae.strokeWidth = 4.0f

        irosubAto.style = Paint.Style.STROKE
        irosubAto.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubAto.strokeWidth = 1.5f
    }

    fun move(jiki:Jiki){
        x3 = x2
        y3 = y2
        x2 = x
        y2 = y

        val xhanai =650
        val yHani = 900

        if (isfirst) {
             vx = jiki.x - x
             vy = jiki.y - y
            isfirst = false
        }else {
             vx = spx
             vy = spy
        }

        spx = vx
        spy = vy

        val kyori = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        x += ((spx / kyori)*10 * speed).toInt()
        y += ((spy / kyori)*10 * speed).toInt()

        if (x > xhanai || x < 0){spx = -vx }
        if (y > yHani || y < 0){spy = -vy}
    }

    fun atariCheck(jiki:Jiki){
        val vx = x - jiki.x
        val vy = y - jiki.y
        if(ookisa == 30){
            hit = true
        }else{
            val atariKyori = jiki.atariKyori()
            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                iro.color = Color.DKGRAY
                ookisa = 30
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
        canvas.drawLine(x2.toFloat(),y2.toFloat(),x.toFloat(),y.toFloat(),irosubMae) //軌跡１の表示
        canvas.drawLine(x3.toFloat(),y3.toFloat(),x2.toFloat(),y2.toFloat(),irosubAto)  //軌跡２の表示
    }

}