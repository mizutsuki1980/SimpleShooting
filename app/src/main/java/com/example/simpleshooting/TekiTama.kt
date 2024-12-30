package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class TekiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int
    var homing :Boolean
    var zenkaix : Int
    var zenkaiy : Int

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.MAGENTA
        homing = true
        zenkaix = x
        zenkaiy = y
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
        canvas.drawRect(shikakuRectXY(), iro)  //自機
    }


    fun eTama(teki:Teki):IchiJoho{
        val m = IchiJoho(teki.x,teki.y,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.MAGENTA
        return m
    }

}