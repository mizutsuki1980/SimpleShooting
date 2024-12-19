package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class JikiJoho(var jikiOokisa:Int,var jikiX:Int,var jikiY:Int,var tamaOkisa:Int) {

    var iro = Paint()
    var x :Int
    var y :Int

    init {
        x = jikiX
        y = jikiY
        iro.style = Paint.Style.FILL
        iro.color = Color.RED
        val m = IchiJoho(0,0,50,10)
        jikiOokisa = 50
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
    }


    fun jiki(initialJikiX:Int,initialJikiY:Int,jikiOokisa:Int,tamaOkisa:Int):IchiJoho{
        val m = IchiJoho(initialJikiX,initialJikiY,jikiOokisa,tamaOkisa)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
        return m
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
        val saX = jikiX - clickX
        val saY = jikiY - clickY
        var xxx = jikiX
        var yyy = jikiY
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

        jikiX = xxx
        jikiY = yyy
    }

    fun drawJiki(canvas:Canvas){
        canvas.drawCircle(jikiX.toFloat(),jikiY.toFloat(),(jikiOokisa/2).toFloat(),iro) //自機の移動　処理
    }
}