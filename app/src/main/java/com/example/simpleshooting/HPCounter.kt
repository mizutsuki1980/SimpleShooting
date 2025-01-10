package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.ColorSpace.Rgb
import android.graphics.Paint
import android.graphics.Rect

class HPCounter {

    //shikakuRect1~5をなんか変数にまとめて一気に動かせるような感じにしたい。
    //ベースとなる座標を決めて、動かせるようにしたい。たぶんできるだろう。

    var iro = Paint()
    var irosub = Paint()

    //まず元となる四角を作って、そこにＨＰの四角をおいていく。
    //それが190×30の四角
    //それをＭａｘＨＰで割る。今回は6
    //それぞれに枠をつけたいから、6+1となる
    //枠は幅10とする。
    //なんで190-（10*(6+1)）となる
    //今回は120を6で割ることになる。20だ。
    //10+20+10+20+10+20+10+20+10+20+10+20+10という感じか。


    var yokohaba = 190
    var tatehaba = 30
    var waku = 10
    var x = 500
    var y = 0

    init {
        iro.style = Paint.Style.FILL
        iro.color = argb(255, 70, 230, 230)
        irosub.style = Paint.Style.FILL
        irosub.color = argb(255, 125, 130, 230)
        //線を設定
        //irosub.style = Paint.Style.STROKE
        //irosub.strokeWidth = 5.0f

    }
    fun drawOne(canvas: Canvas,rect:Rect,iro:Paint) {
        canvas.drawRect(rect, iro)
    }

    fun shikakuRect(canvas:Canvas,atai:Int){
        val m = Rect(x, y, x+yokohaba,y+tatehaba)
        drawOne(canvas,m, iro)
        //まず元の四角を表示

        //HPの分だけ四角を表示
        for(a in 1..<6) {
            val m = Rect(x+10+20*a, y, x+10+20*a+20,y+tatehaba)
            drawOne(canvas,m, iro)

        }

    }



    fun shikakuRect(): Rect {
        var xxx = 600
        var yyy = 0
        var ooookisa = 200
        val left = 500
        val right = 690
        val top = 0
        val bottom = 50
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun shikakuRect1(): Rect {
        val left = 510
        val right = 530
        val top = 10
        val bottom = 40
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun shikakuRect2(): Rect {
        val left = 540
        val right = 560
        val top = 10
        val bottom = 40
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun shikakuRect3(): Rect {
        val left = 570
        val right = 590
        val top = 10
        val bottom = 40
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun shikakuRect4(): Rect {
        val left = 600
        val right = 620
        val top = 10
        val bottom = 40
        val m = Rect(left, top, right,bottom)
        return m
    }
    fun shikakuRect5(): Rect {
        val left = 630
        val right = 650
        val top = 10
        val bottom = 40
        val m = Rect(left, top, right,bottom)
        return m
    }
    fun shikakuRect6(): Rect {
        val left = 660
        val right = 680
        val top = 10
        val bottom = 40
        val m = Rect(left, top, right,bottom)
        return m
    }



    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRect(), iro)
        canvas.drawRect(shikakuRect1(), irosub)
        canvas.drawRect(shikakuRect2(), irosub)
        canvas.drawRect(shikakuRect3(), irosub)
        canvas.drawRect(shikakuRect4(), irosub)
        canvas.drawRect(shikakuRect5(), irosub)
        canvas.drawRect(shikakuRect6(), irosub)

    }

}