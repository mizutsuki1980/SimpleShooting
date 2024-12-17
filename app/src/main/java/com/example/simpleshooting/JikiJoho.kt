package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint

class JikiJoho(var jikiOokisa:Int,var jikiX:Int,var jikiY:Int,var tamaOkisa:Int) {
    //色情報はJikiJohoに入れることにする　iro
    //m.ってやつを調べていたら、eTamaというのを修正しなければいけないのでは？と思った。
    //IchiJohoで作っちゃったのは、今全部m.に色が入ってるのか。面倒くさいっぽい。
    //まずは自機の色を使ってないか。調べる。
    var x:Int
    var y:Int
    var iro = Paint()

    init {
        x=jikiX
        y=jikiY

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

}