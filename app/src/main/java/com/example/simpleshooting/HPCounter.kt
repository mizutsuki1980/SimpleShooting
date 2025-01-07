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

    init {
        iro.style = Paint.Style.FILL
        iro.color = argb(150, 255, 255, 255)
        //線を設定
        irosub.style = Paint.Style.STROKE
        irosub.color = argb(125, 0, 0, 255)
        irosub.strokeWidth = 5.0f
        //canvas.drawColor(Color.argb(125, 0, 0, 255));

        //ctx.fillStyle = "rgba(" + [0, 0, 255, 1] + ")";

        //canvas.drawBitmap(bmp, 100, 100, mPaint);
        //ビットマップも出せそうだけど、まだ置いておくか。

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