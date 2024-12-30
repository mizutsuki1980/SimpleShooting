package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint

class TekiTama {
    var iro = Paint()
    var ookisa:Int


    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.MAGENTA

    }


    fun eTama(teki:TekiJoho):IchiJoho{
        val m = IchiJoho(teki.x,teki.y,10,10)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.MAGENTA
        return m
    }

}