package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int
    var tamaFrameFFF : Int
    init {
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
        tamaFrameFFF = 0
    }



    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun tamaSyori(jikiX:Int,jikiY:Int){
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()
        tamaFrameFFF += 1
        y -= tamaPlus
        if(y<5){
            tamaFrameFFF=0
        }        //画面の上部で消える
        if(tamaFrameFFF==20){
            tamaFrameFFF=0
        }        //20フレームでリセット
      if(tamaFrameFFF==0){
            x=jikiX
            y=jikiY
        }
    }

    fun tamaJikiSyori(jikiX:Int,jikiY:Int,ex:Int,ey:Int,atariKyori:Int):Boolean{
        if(ookisa == 30){
            x=jikiX
            y=jikiY
            tamaFrameFFF=0
        }

        var hit = false
        val vx = x - ex
        val vy = y - ey


        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            iro.color = Color.WHITE
            ookisa = 30
            hit = true
        }
        return hit
    }

}