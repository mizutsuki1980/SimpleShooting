package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint

class Jiki(var x:Int, var y:Int) {

    val iro = Paint()
    val irosub = Paint()
    val irokao = Paint()
    var ookisa = 30
    var kakudo = 0
    var hp = 6

    init {
        ookisa = 50
        iro.style = Paint.Style.FILL
        iro.color = argb(255, 255, 255, 150)
        irosub.style = Paint.Style.FILL
        irosub.color = argb(170, 255, 140, 0)
        irokao.style = Paint.Style.FILL
        irokao.color = argb(255, 0, 0, 0)

    }




    fun hyperPowerUp(){
        if (ookisa==200){
            ookisa = 50
        }else{
            ookisa = 200
        }
    }
    fun hyperShotPowerUp(){
        if (ookisa==200){
            ookisa = 50
        }else{
            ookisa = 200
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
        canvas.drawCircle(x.toFloat(),y.toFloat(),(ookisa/2).toFloat(),iro) //自機の描画
        drawKao(canvas)
        drawSubKi(canvas)
    }
    fun drawKao(canvas:Canvas){
        canvas.drawCircle(x-18.toFloat(),y-10.toFloat(),5.toFloat(),irokao) //自機の描画
        canvas.drawCircle(x+18.toFloat(),y-10.toFloat(),5.toFloat(),irokao) //自機の描画
        canvas.drawCircle(x.toFloat(),y+15.toFloat(),10.toFloat(),irokao) //自機の描画
    }

    fun tamaHassha(jikiIchiTyousei:Int):JikiTama{
        return JikiTama(x,y-jikiIchiTyousei)
    }
    fun drawSubKi(canvas:Canvas){
        kakudo += 1
        val kyori = ookisa*7
        val xx = kyori * Math.cos(kakudo.toDouble())/10
        val yy = kyori * Math.sin(kakudo.toDouble())/10

        //なんか一定の角度で色変えたり、消えたりしたいなー

        canvas.drawCircle(x+xx.toFloat(),y+yy.toFloat(),(ookisa/2-12).toFloat(),irosub)    //サブ機の描画
        canvas.drawCircle(x-xx.toFloat(),y-yy.toFloat(),(ookisa/2-12).toFloat(),irosub)    //サブ機の描画


        if (kakudo >= 360 ){kakudo = 0}
    }
}
