package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint

class Jiki(var x:Int, var y:Int, var tamaOkisa:Int) {

    var iro = Paint()
    var irosub = Paint()
    var jikiOokisa:Int
    var frame = 0

    init {
        jikiOokisa = 50
        iro.style = Paint.Style.FILL
        iro.color = argb(255, 255, 255, 150)
        irosub.style = Paint.Style.FILL
        irosub.color = argb(170, 150, 0, 0)

    }



    fun atariKyori():Int{
        var atariKyori = 5 + jikiOokisa/2 //当たり判定の距離
        if(jikiOokisa==200){
            atariKyori -= 5
        }//大きいときは、ちょっと当たり判定をマイナスする
        return atariKyori
    }

    fun hyperPowerUp(){
        if (jikiOokisa==200){
            jikiOokisa = 50
        }else{
            jikiOokisa = 200
        }
    }
    fun hyperShotPowerUp(){
        if (jikiOokisa==200){
            jikiOokisa = 50
        }else{
            jikiOokisa = 200
        }
    }

    fun move(clickX:Int,clickY:Int){
        val saX = x - clickX
        val saY = y - clickY
        var xxx = x
        var yyy = y
        val speed = 2.5
        val plus = 10 * speed .toInt()

        if (saX >= -(plus) && saX <= plus){
            xxx = clickX
        }else {
            if (saX > 0) {
                xxx -= plus
            }
            if (saX < 0) {
                xxx += plus
            }
        }
        if (saY >= -plus && saY <= plus){
            yyy = clickY
        }else {
            if (saY > 0) {
                yyy -= plus
            }
            if (saY < 0) {
                yyy += plus
            }
        }

        x = xxx
        y = yyy
    }

    fun draw(canvas:Canvas){
        canvas.drawCircle(x.toFloat(),y.toFloat(),(jikiOokisa/2).toFloat(),iro) //自機の描画
        drawSubKi(canvas)
        //なるほど、順番に描画されていくわけか
    }


    //    1 * sin(θ) == y;
    fun drawSubKi(canvas:Canvas){
        //描画されるたびに＋されるだけでいいなら、ここに入れとけばいっか、frame+＝1
        frame += 10
        val kakudo = frame.toDouble()
        var xx = x * Math.cos(kakudo)/10
        var yy = y * Math.sin(kakudo)/10

        canvas.drawCircle(x+xx.toFloat(),y+yy.toFloat(),(jikiOokisa/2-5).toFloat(),irosub)    //サブ機の描画
        canvas.drawCircle(x-xx.toFloat(),y-yy.toFloat(),(jikiOokisa/2-5).toFloat(),irosub)    //サブ機の描画
        if (frame >= 360 ){frame = 0}
    }
}
