package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class JikiJoho(var x:Int,var y:Int,var tamaOkisa:Int) {

    var iro = Paint()
    var jikiOokisa:Int

    init {
        jikiOokisa = 50
        iro.style = Paint.Style.FILL
        iro.color = Color.RED
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

    fun clickShitaBshoNiIdou(clickX:Int,clickY:Int){
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
        canvas.drawCircle(x.toFloat(),y.toFloat(),(jikiOokisa/2).toFloat(),iro) //自機の移動　処理
    }
}