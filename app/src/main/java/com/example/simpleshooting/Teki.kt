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
    val irokao = Paint()

    val TEKI_YOWAYOWA_STATE = 1
    val TEKI_NORMAL_STATE = 2
    val TEKI_TSUYO_STATE = 3
    val TEKI_ONITSUYO_STATE = 4

    var status = TEKI_YOWAYOWA_STATE // 最初は玉が画面内に無い状態

    init{
        x = 20
        y = 100

        iro.style = Paint.Style.FILL
        iro.color = argb(255, 0, 255, 255)
        //alphaってのは明度？
        irosub.style = Paint.Style.FILL
        irosub.color = argb(170, 0, 0, 255)

        irokao.style = Paint.Style.STROKE
        irokao.color = Color.BLUE   //argb(255, 255, 255, 200)
        irokao.strokeWidth = 5.0f

    }

    fun nextFrame(scoreCount:Int) {
        val ss = scoreCount % 4
        if(ss==0){status=TEKI_YOWAYOWA_STATE}
        if(ss==1){status=TEKI_NORMAL_STATE}
        if(ss==2){status=TEKI_TSUYO_STATE}
        if(ss==3){status=TEKI_ONITSUYO_STATE}
        when(status) {
            TEKI_YOWAYOWA_STATE -> {
                tekiSpeed = 15
                iro.color = argb(255, 0, 255, 255)
            }
            TEKI_NORMAL_STATE -> {
                iro.color = argb(255, 0, 220, 220)
            }
            TEKI_TSUYO_STATE -> {
                tekiSpeed = 20
                iro.color = argb(255, 0, 180, 180)
            }
            TEKI_ONITSUYO_STATE -> {
                tekiSpeed = 40
                iro.color = argb(255, 0, 150, 150)
            }
        }
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


    fun drawKao(canvas: Canvas){
        when(status) {
            TEKI_YOWAYOWA_STATE -> {
                canvas.drawLine(x-35.toFloat(),y-10.toFloat(),x-10.toFloat(),y-20.toFloat(),irokao)
                canvas.drawLine(x+35.toFloat(),y-10.toFloat(),x+10.toFloat(),y-20.toFloat(),irokao)
            }
            TEKI_NORMAL_STATE -> {
                canvas.drawLine(x-35.toFloat(),y-15.toFloat(),x-10.toFloat(),y-15.toFloat(),irokao)
                canvas.drawLine(x+35.toFloat(),y-15.toFloat(),x+10.toFloat(),y-15.toFloat(),irokao)
            }
            TEKI_TSUYO_STATE -> {
                canvas.drawLine(x-35.toFloat(),y-20.toFloat(),x-10.toFloat(),y-10.toFloat(),irokao)
                canvas.drawLine(x+35.toFloat(),y-20.toFloat(),x+10.toFloat(),y-10.toFloat(),irokao)
            }
            TEKI_ONITSUYO_STATE -> {
                canvas.drawLine(x-35.toFloat(),y-20.toFloat(),x-10.toFloat(),y-10.toFloat(),irokao)
                canvas.drawLine(x+35.toFloat(),y-20.toFloat(),x+10.toFloat(),y-10.toFloat(),irokao)
                canvas.drawLine(x-15.toFloat(),y-25.toFloat(),x-5.toFloat(),y-25.toFloat(),irokao)
                canvas.drawLine(x+15.toFloat(),y-25.toFloat(),x+5.toFloat(),y-25.toFloat(),irokao)
            }
        }

    }

    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRectXY(), iro)   //敵の移動　処理
        drawSubKi(canvas)
        drawKao(canvas)
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