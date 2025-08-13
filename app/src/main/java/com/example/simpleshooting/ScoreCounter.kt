package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect

class ScoreCounter {
    val iro = Paint()
    val iroMoji = Paint()
    val irosub = Paint()
    val irosubMoji = Paint()
    val irosubMojiTitleTime = Paint()
    val irosubMojiTitleScore = Paint()

    var yokohaba = 190
    var x = 20
    var y = 0

    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE
        iroMoji.style = Paint.Style.FILL
        iroMoji.color = argb(255, 255, 255, 255)
        iroMoji.textSize = 40.toFloat()

        irosub.style = Paint.Style.FILL
        irosub.color = Color.GREEN
        irosubMoji.style = Paint.Style.FILL
        irosubMoji.color = argb(255, 0, 0, 0)
        irosubMoji.textSize = 30.toFloat()

        irosubMojiTitleScore.style = Paint.Style.FILL
        irosubMojiTitleScore.color = argb(255, 255, 255, 255)
        irosubMojiTitleScore.textSize = 20.toFloat()

        irosubMojiTitleTime.style = Paint.Style.FILL
        irosubMojiTitleTime.color = argb(255, 0, 0, 0)
        irosubMojiTitleTime.textSize = 25.toFloat()

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

    fun timeshikakuRect(): Rect {
        //元になる四角をつくる、ここはOK
        val left = x+200
        val right = x+yokohaba+200//690
        val top = y
        val bottom = y+50 //50
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun draw(canvas: Canvas,scoreCount:Int,frame:Int){
        canvas.drawRect(shikakuRect(), iro)
        canvas.drawText(zerohuyasu(scoreCount.toString()),(x+9).toFloat(),(y+44).toFloat(),iroMoji)
        canvas.drawText("score",(20).toFloat(),(16).toFloat(),irosubMojiTitleScore)
        canvas.drawRect(timeshikakuRect(), irosub)
        canvas.drawText(zerohuyasu(frame.toString()),(x+9+200).toFloat(),(y+44).toFloat(),irosubMoji)
        canvas.drawText("time",(20+200).toFloat(),(19).toFloat(),irosubMojiTitleTime)
    }
    fun zerohuyasu(text:String):String{
        val ketasuu = 8 //今回は８桁に固定
        val length = text.length
        var newtext = ""
        for (a in 0..<(ketasuu-length)) {
             newtext = "0" + newtext
        }
        newtext = newtext + text
        return newtext
    }

}