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
        //これよる上は関係ない
        val tamaOokisa = 10
        //弾の大きさは固定
        val tamaPaint = Paint()
        tamaPaint.color = Color.YELLOW
        tamaPaint.style = Paint.Style.FILL
        //弾の塗りもとりあえず固定


        if (tamaX == 0){
            tamaX = posX
            tamaY = posY
            tamaFrame = 1
        }
        //もしtamax==0 初期値なら、弾が発射される。

        susumu = tamaFrame*90
        //進んだ距離は弾により違う。が、一定間隔で一緒の値

        val xx = tamaX-tamaOokisa/2
        val xxx =xx+tamaOokisa
        val yy = tamaY-susumu-(tamaOokisa/2)
        val yyy =yy+tamaOokisa

        val tamaIchi = Rect(xx, yy, xxx, yyy)
        //弾①の位置を決める

        canvas.drawRect(tamaIchi, tamaPaint)
        //弾を描画


        tamaFrame += 1
        //弾が進んだ処理をする。

        if (tamaY-susumu  < 1){
            tamaX = 0
        }
        //ここで画面外にいたら、tamaXを初期値に戻す。

        //ひとまず２個出すのは出来た。次は２発目はｘの軸を変えて出てくるようにしよう。

    }

    var tamaFrame = 0
    var susumu = 0
    var frame = 0


    fun ippatuTama(){
        tamaX = posX
        tamaY = posY

    }






    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
    }

    fun beginAnimation() {
        tsugiNoSyori()
    }

    var posX = 500
    var posY = 1500
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}