package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class TekiJoho {

    var x :Int
    var y :Int
    var ookisa =100
    var tekiSpeed = 10
    var iro = Paint()

    init{
        x = -50
        y = 5
        iro.style = Paint.Style.FILL
        iro.color = Color.CYAN

    }

    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun teki():IchiJoho{
        val e = IchiJoho(20,100,ookisa,10)
        e.iro.style = Paint.Style.FILL
        e.iro.color = Color.CYAN
        return e
    }
    fun tekiUgki():Int{
        val tekiSpeed = 10
        var xx = x
        if(xx<800){ xx += tekiSpeed }
        if(xx >= 800) { xx = -100 }
        return xx
    }

    fun tekiYokoIdo(){
        x += tekiSpeed
        if(x >= 800) { x = -50 } //端っこだったら元の位置に戻る
    }
}