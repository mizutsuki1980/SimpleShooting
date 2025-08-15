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

    fun drawBell(canvas: Canvas, z:Float, w:Float){
        // --- ベル本体 ---


        val pathBell = android.graphics.Path().apply {
            moveTo(300f, 200f)

            // 左側カーブ
            cubicTo(200f, 220f, 180f, 350f, 200f, 400f)

            // 下部アーチ（底部分）
            quadTo(300f, 450f, 400f, 400f)

            // 右側カーブ
            cubicTo(420f, 350f, 400f, 220f, 300f, 200f)

            close()
        }

        // --- 空洞（黒い部分） ---
        val pathHole = android.graphics.Path().apply {
            // 下部の内側だけの弧を描く
            moveTo(220f, 400f)
            quadTo(300f, 430f, 380f, 400f)
            quadTo(300f, 420f, 220f, 400f) // 下側の弧

            close()
        }

        // --- 鈴の玉（下の丸） ---
        val ballX = 300f
        val ballY = 440f
        val ballRadius = 10f

        // --- Paint定義 ---
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

        val paintBall = Paint().apply {
            color = Color.DKGRAY
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        // --- 描画 ---
        canvas.drawPath(pathBell, paintBell)  // ベル本体（黄色）
        canvas.drawPath(pathHole, paintHole)  // 空洞部分（黒）
        canvas.drawCircle(ballX, ballY, ballRadius, paintBall) // 鈴の玉


    }
    fun draw(canvas: Canvas,jiki:Jiki){
        canvas.drawRect(shikakuRect(), iro)
        //canvas.drawText("♡",(435).toFloat(),(50).toFloat(),iroHeart)
        drawHaert(canvas,465f,50f)
        drawBell(canvas,10f,20f)

        for(a in 0..<jiki.hp) {
            canvas.drawRect(hpShikakuRect(a+1), irosub)
        }
    }

}