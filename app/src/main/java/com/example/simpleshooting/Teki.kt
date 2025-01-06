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
    var irosub = Paint()

    init{
        x = 20
        y = 100
        iro.style = Paint.Style.FILL
        iro.color = Color.CYAN

        irosub.style = Paint.Style.FILL
        irosub.color = Color.BLUE

    }

    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun shikakuRectXYSub(xxx:Int,yyy:Int,ooookisa:Int): Rect {
        val left = xxx  - ooookisa / 2
        val right = xxx  + ooookisa / 2
        val top = yyy  - ooookisa / 2
        val bottom = yyy + ooookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }
    fun yokoIdo(){
        x += tekiSpeed
        if(x >= 800) { x = -50 } //端っこだったら元の位置に戻る
    }
    fun draw(canvas: Canvas){
         //なるほどー、shikakuRectXY()こういう風に書いちゃうと位置が固定されてしまうのか。
        canvas.drawRect(shikakuRectXY(), iro)   //敵の移動　処理
        canvas.drawRect(shikakuRectXYSub(x-20-ookisa/2,y,30), irosub)   //敵の移動　処理
        canvas.drawRect(shikakuRectXYSub(x+20+ookisa/2,y,30), irosub)   //敵の移動　処理


    }
}