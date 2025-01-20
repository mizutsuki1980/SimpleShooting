package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect

class ScoreCounter {
    val iro = Paint()
    val irosub = Paint()
    var yokohaba = 190
    var x = 20
    var y = 0

    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE
        irosub.style = Paint.Style.FILL
        irosub.color = argb(255, 255, 255, 255)
        irosub.textSize = 40.toFloat()
    }
    fun shikakuRect(): Rect {
        //元になる四角をつくる、ここはOK
        val left = x
        val right = x+yokohaba//690
        val top = y
        val bottom = y+50 //50
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun draw(canvas: Canvas,scoreCount:Int){
        canvas.drawRect(shikakuRect(), iro)
        val z = "0000000"+ scoreCount.toString()
        canvas.drawText(z,(x+9).toFloat(),(y+40).toFloat(),irosub)

    }

}