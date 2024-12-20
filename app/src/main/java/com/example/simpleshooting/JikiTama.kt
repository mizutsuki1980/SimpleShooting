package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int
    init {
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
    }



    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun tamaSyori(){
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()

//        tamaFrameIchi += 1

  //      jt.y -= tamaPlus

    //    if(jt.y<5){
      //      tamaFrameIchi=0
        //}        //画面の上部で消える

//        if(tamaFrameIchi==20){
  //          tamaFrameIchi=0
    //    }        //20フレームでリセット

      //  if(tamaFrameIchi==0){
        //    jt = JikiTama(jiki.x,jiki.y)
//        }

}