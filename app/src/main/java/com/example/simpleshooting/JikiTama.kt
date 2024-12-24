package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int
    var tamaFrame : Int

    init {
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
        tamaFrame = 0
    }



    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }


    fun reset(jikiX:Int,jikiY:Int){
        x = jikiX
        y = jikiY
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
        tamaFrame = 0
    }

    fun tamaSyori(atariKyori:Int,jiki:JikiJoho,e:IchiJoho):Boolean {
        var hit = false
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()
        tamaFrame += 1
        y -= tamaPlus
        if(y<5){
            tamaFrame=0
        }        //画面の上部で消える

        if(tamaFrame==20){
            tamaFrame=0
        }        //20フレームでリセット

        if(tamaFrame==0){
            reset(jiki.x,jiki.y)
        }
        //で次に、ここで何をやってるのか？「自分の弾」と「敵の位置」を計算して、近かったら消滅、リセット。
        if(ookisa == 30){
            reset(jiki.x,jiki.y)
        }


        val vx = x - e.x
        val vy = y - e.y

        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            iro.color = Color.WHITE
            ookisa = 30
            hit = true
        }
        return hit
    }
}