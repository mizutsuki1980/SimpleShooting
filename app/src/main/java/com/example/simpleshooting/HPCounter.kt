package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.ColorSpace.Rgb
import android.graphics.Paint
import android.graphics.Rect
import kotlin.io.path.Path
import kotlin.io.path.moveTo

class HPCounter {


    val iro = Paint()
    val irosub = Paint()
    val iroHeart = Paint()

    var yokohaba = 190
    var tatehaba = 50
    var waku = 10
    var x = 500
    var y = 0

    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.RED
        iroHeart.style = Paint.Style.FILL
        iroHeart.color = Color.RED
        iroHeart.textSize = 60.toFloat()

        irosub.style = Paint.Style.FILL
        irosub.color = argb(255, 255, 255, 200)

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

    fun hpShikakuRect(hp:Int): Rect {
        //1なら、、、？   10              と   10+20
        //2なら、、、、   10+20+10    　   と　10+20+10+20　
        //3なら、、、、   10+20+10+20+10　 と　10+20+10+20+10+20
        val hidarikata = x + waku*hp + 20*(hp-1)
        val migikata =  hidarikata+20
        val left = hidarikata
        val right = migikata
        val top = y + waku
        val bottom = y+tatehaba - waku //50
        val m = Rect(left, top, right,bottom)
        return m
    }


    fun drawHaert(canvas: Canvas, xx:Float, yy:Float){
        val paintHaert = Paint()

        paintHaert.color = Color.RED
        paintHaert.style = Paint.Style.FILL

        val b = 0.2f
        val pathHaert = android.graphics.Path()
        //        val z = 850f //ハートの最下部
        //        val w = 300f //ハートのｘ軸

        pathHaert.moveTo(xx, yy) // 下のとがった部分
        // 左側のカーブ
        pathHaert.cubicTo(xx-150f*b, yy-100f*b, xx-150f*b, yy-250f*b, xx, yy-200f*b)
        // 右側のカーブ
        pathHaert.cubicTo(xx+150f*b, yy-250f*b, xx+150f*b, yy-100f*b, xx, yy)
        pathHaert.close()
        canvas.drawPath(pathHaert,paintHaert)
    }

    fun drawBell(canvas: Canvas, xx:Float, yy:Float){
        val b = 0.21f

        val pathBell = android.graphics.Path().apply {
            moveTo(xx, yy)
            moveTo(xx, yy)
            cubicTo(xx-100f*b, yy+20f*b, xx-120f*b, yy+150f*b, xx-100f*b, yy+200f*b)
            quadTo(xx, yy+250f*b, xx+100f*b, yy+200f*b)
            cubicTo(xx+120f*b, yy+150f*b, xx+100f*b, yy+20f*b, xx, yy)
            close()
        }

        val pathHole = android.graphics.Path().apply {
            moveTo(xx-80f*b, yy+200f*b)
            quadTo(xx, yy+230f*b, xx+80f*b, yy+200f*b)
            quadTo(xx, yy+220f*b, xx-80f*b, yy+200f*b) // 下側の弧
            close()
        }

        val ballX = xx
        val ballY = yy+240f*b
        val ballRadius = 10f*b

        val paintBell = Paint().apply {
            color = Color.YELLOW
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        val paintHole = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        canvas.drawPath(pathBell, paintBell)  // ベル本体（黄色）
        canvas.drawPath(pathHole, paintHole)  // 空洞部分（黒）
        canvas.drawCircle(ballX, ballY, ballRadius, paintBell) // 鈴の玉

    }
    fun draw(canvas: Canvas,jiki:Jiki){
        canvas.drawRect(shikakuRect(), iro)
        //canvas.drawText("♡",(435).toFloat(),(50).toFloat(),iroHeart)
        drawHaert(canvas,465f,50f)
        drawBell(canvas,500f,150f)

        for(a in 0..<jiki.hp) {
            canvas.drawRect(hpShikakuRect(a+1), irosub)
        }
    }

}