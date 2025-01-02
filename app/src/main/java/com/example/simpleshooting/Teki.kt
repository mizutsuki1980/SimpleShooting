package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Teki {

    var x :Int
    var y :Int
    var ookisa =70

    var tekiSpeed = 10
    var iro = Paint()

    init{
        x = 20
        y = 100
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

    fun yokoIdo(){
        x += tekiSpeed
        if(x >= 800) { x = -50 } //端っこだったら元の位置に戻る
    }
    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRectXY(), iro)   //敵の移動　処理
    }
}