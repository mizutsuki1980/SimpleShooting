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
    var homing :Boolean
    var hit :Boolean
    var zenkaix : Int
    var zenkaiy : Int
    var speed : Double

    val irosubMae = Paint()
    val irosubAto = Paint()


    var kisekiMae_x : Int
    var kisekiMae_y : Int
    var kisekiAto_x : Int
    var kisekiAto_y : Int

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE
        homing = true
        hit = false
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

    fun move(jiki:Jiki){
        kisekiAto_x = kisekiMae_x
        kisekiAto_y = kisekiMae_y
        kisekiMae_x = x
        kisekiMae_y = y



        val xhanai =650
        val yHani = 900
        var vx = jiki.x - x
        var vy = jiki.y - y

        val resetKyori = 500 //よけ始める距離
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
        canvas.drawLine(kisekiMae_x.toFloat(),kisekiMae_y.toFloat(),x.toFloat(),y.toFloat(),irosubMae) //軌跡１の表示
        canvas.drawLine(kisekiAto_x.toFloat(),kisekiAto_y.toFloat(),kisekiMae_x.toFloat(),kisekiMae_y.toFloat(),irosubAto)  //軌跡２の表示
    }

}