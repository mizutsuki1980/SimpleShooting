package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class TekiTama(var x:Int,var y:Int) {
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
        iro.color = Color.MAGENTA
        homing = true
        hit = false
        zenkaix = x
        zenkaiy = y
        speed = 2.0
    }

    fun move(jiki:JikiJoho,teki:Teki){
        var vx = jiki.x - x
        var vy = jiki.y - y

        var resetKyori = 90 //よけ始める距離
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

        if (x > 690 || x < 0 || y > 1050 || y < 0){
            x = teki.x
            y = teki.y
            zenkaix = x
            zenkaiy = y
            homing = true
            //弾のリセットどうすんだろう？これでいっか
        }    //画面外で敵の弾のリセット
    }
    fun atariCheck(jiki:JikiJoho){
    //カウント処理がはいってるなー。そとにだしたほうがいいのか。ならBooleanであたった、とかつけるのかな？
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