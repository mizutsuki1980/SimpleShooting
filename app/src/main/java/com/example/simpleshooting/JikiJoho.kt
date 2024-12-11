package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint

class JikiJoho(var jikiOokisa:Int,var jikiX:Int,var jikiY:Int,var m :IchiJoho ) {

    // var jikiOokisa
    // var jikiX
    // var jikiY
    // var m :IchiJoho //(IchiJouhouのjiki())

    init {
        println("最初の初期化ブロックによる $jikiOokisa のプリント")
    }


    fun jikiSet(initialJikiX:Int,initialJikiY:Int,jikiOokisa:Int,tamaOkisa:Int):IchiJoho{
        val m = IchiJoho(initialJikiX,initialJikiY,jikiOokisa,tamaOkisa)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
        return m
    }

}