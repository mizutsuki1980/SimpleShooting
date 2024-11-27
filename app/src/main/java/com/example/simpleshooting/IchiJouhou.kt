package com.example.simpleshooting
import android.graphics.Paint
import android.graphics.Rect

class IchiJouhou(var x:Int, var y:Int, var Ookisa:Int, val tamaOokisa:Int){
    var alive = true    //念のため　使うのかわからないけど
    var homing = true   //敵の弾が自機を捕まえに来るのに使う
    var zenkaiVect = mutableListOf<Int>(0,0)    //敵の弾が自機を捕まえに来るのに使う

    var left = x  - Ookisa / 2
    var right = x  + Ookisa / 2
    var top = y  - (Ookisa) /2
    var bottom = y + (Ookisa) /2
    var iro = Paint()


    fun shikakuRectXY(x:Int,y:Int,Ookisa:Int): Rect {
        left = x  - Ookisa / 2
        right = x  + Ookisa / 2
        top = y  - Ookisa / 2
        bottom = y + Ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }
}
