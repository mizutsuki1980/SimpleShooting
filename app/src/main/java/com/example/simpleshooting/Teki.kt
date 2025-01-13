package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect

class Teki {

    var x :Int
    var y :Int
    var ookisa =70
    var kakudo = 0
    var hp = 3

    var tekiSpeed = 10
    val iro = Paint()
    val irosub = Paint()

    init{
        x = 20
        y = 100

        iro.style = Paint.Style.FILL
        iro.color = Color.CYAN

        irosub.style = Paint.Style.FILL
        irosub.color = argb(170, 0, 0, 255)

    }
    fun tamaHassha():TekiTama{
        return TekiTama(x,y)
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
        canvas.drawRect(shikakuRectXY(), iro)   //敵の移動　処理
        drawSubKi(canvas)
    }

    fun drawSubKi(canvas:Canvas){
        //描画されるたびに＋されるだけでいいなら、ここに入れとけばいっか、frame+＝1
        kakudo += 1
        val kyori = ookisa*6
        val xx = kyori * Math.cos(kakudo.toDouble())/10
        val yy = kyori * Math.sin(kakudo.toDouble())/10

        canvas.drawRect(shikakuRectXYSub(x+xx.toInt() ,y+yy.toInt(),ookisa/3), irosub)   //敵の移動　処理
        canvas.drawRect(shikakuRectXYSub(x-xx.toInt() ,y-yy.toInt(),ookisa/3), irosub)   //敵の移動　処理

        if (kakudo >= 360 ){kakudo = 0}
    }

}