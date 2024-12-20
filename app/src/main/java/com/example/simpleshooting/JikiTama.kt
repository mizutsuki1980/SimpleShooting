package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint

class JikiTama {
    var x:Int
    var y:Int

    fun jTama(jikiX:Int,jikiY:Int,jikiTamaOkisa:Int):IchiJoho{
        //弾をリセットする。最初の状態にする。
        val z = IchiJoho(jikiX,jikiY,10,jikiTamaOkisa)
        z.iro.style = Paint.Style.FILL
        z.iro.color = Color.GREEN
        return z
    }

}