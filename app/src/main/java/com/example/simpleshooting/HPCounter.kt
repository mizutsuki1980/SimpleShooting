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


    fun drawHaert(canvas: Canvas, z:Float, w:Float){
        val paintHaert = Paint()

        paintHaert.color = Color.RED
        paintHaert.style = Paint.Style.FILL

        val b = 0.2f
        val pathHaert = android.graphics.Path()
        //        val z = 850f //ハートの最下部
        //        val w = 300f //ハートのｘ軸
        pathHaert.moveTo(w, z) // 下のとがった部分
        // 左側のカーブ
        pathHaert.cubicTo(w-150f*b, z-100f*b, w-150f*b, z-250f*b, w, z-200f*b)
        // 右側のカーブ
        pathHaert.cubicTo(w+150f*b, z-250f*b, w+150f*b, z-100f*b, w, z)
        pathHaert.close()
        canvas.drawPath(pathHaert,paintHaert)
    }

    fun drawBell(canvas: Canvas, z:Float, w:Float){

        val pathBell = android.graphics.Path()
            // スタート地点：上部の中央（ベルのてっぺん）
        pathBell.moveTo(300f, 200f)

            // 左側のカーブ（上から下に広がる）
        pathBell.cubicTo(200f, 220f, 180f, 350f, 200f, 400f)

            // 下部（底）のアーチ
        pathBell.quadTo(300f, 450f, 400f, 400f)

            // 右側のカーブ（下から上に狭まる）
        pathBell.cubicTo(420f, 350f, 400f, 220f, 300f, 200f)

        pathBell.close()


        val paint = Paint().apply {
            color = Color.YELLOW
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        canvas.drawPath(pathBell, paint)

// 鈴の玉（オプション）
        val ballPaint = Paint().apply {
            color = Color.DKGRAY
            style = Paint.Style.FILL
        }

        canvas.drawCircle(300f, 450f, 10f, ballPaint)


        val ballX = 300f
        val ballY = 450f
        val ballRadius = 10f

        canvas.drawCircle(ballX, ballY, ballRadius, paint)

    }
    fun draw(canvas: Canvas,jiki:Jiki){
        canvas.drawRect(shikakuRect(), iro)
        //canvas.drawText("♡",(435).toFloat(),(50).toFloat(),iroHeart)
        drawHaert(canvas,50f,465f)
        drawBell(canvas,10f,20f)

        for(a in 0..<jiki.hp) {
            canvas.drawRect(hpShikakuRect(a+1), irosub)
        }
    }

}