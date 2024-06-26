package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaX = 0
    var tamaY = 0
    override fun onDraw(canvas: Canvas) {
        val r = Rect(100, 100, 200, 200)
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.FILL
        canvas.drawRect(r, p)
        val ookisa = 100
        val jikiIchi = Rect(posX-ookisa/2, posY-ookisa/2, posX+ookisa/2, posY+ookisa/2)
        val jikiIro = Paint()

        if (frame % 2 ==1) {
            jikiIro.color = Color.WHITE
        }else {
            jikiIro.color = Color.GRAY
        }

        jikiIro.style = Paint.Style.FILL
        canvas.drawRect(jikiIchi, jikiIro)


        val tamaOokisa = 40
        val tamaPaint = Paint()
        tamaPaint.color = Color.WHITE
        tamaPaint.style = Paint.Style.FILL

        if (tamaX == 0){
            tamaX = posX
            tamaY = posY
            tamaFrame = 2
        }
        //じゃ繰り返し弾が出るようにしてみようか
         kasoku = tamaFrame*70
        val tamaIchi = Rect(tamaX+tamaOokisa, tamaY-kasoku-(tamaOokisa+20), tamaX+tamaOokisa+20, tamaY-kasoku-tamaOokisa)
        canvas.drawRect(tamaIchi, tamaPaint)

        tamaFrame += 1
        if (tamaY-kasoku  < 1){
            tamaX = 0
        }

     }

    var tamaFrame = 0
    var kasoku = 0
    var frame = 0

    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
    }
    //「var frame = 0 // tsugiNoSyoriでこのframeを更新する」 と書いてあるので、tsugiNoSyoriで行う

    fun beginAnimation() {
        tsugiNoSyori()
    }

    var posX = 150
    var posY = 150
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            tamaX = 0
            tamaY = 0
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            tamaX = 0
            tamaY = 0
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            tamaX = 0
            tamaY = 0
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}