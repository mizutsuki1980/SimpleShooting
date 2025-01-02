package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class TekiTamaRef(jiki:JikiJoho,teki:Teki) {
    //reflection
    var x = teki.x
    var y = teki.y
    var iro = Paint()
    var ookisa:Int
    var homing :Boolean
    var hit :Boolean
    var zenkaix : Int
    var zenkaiy : Int
    var speed : Double

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE
        homing = true
        hit = false
        zenkaix = jiki.x
        zenkaiy = jiki.y
        speed = 3.0
    }

    fun move(jiki:JikiJoho){
        var xhanai =650
        var yHani = 900
        var vx = jiki.x - x
        var vy = jiki.y - y

        var resetKyori = 500 //よけ始める距離
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

    fun atariCheck(jiki:JikiJoho){
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
    }

}