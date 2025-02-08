package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Item {
    var isAppearance = true

    val xlist = listOf<Int>(100,150,200,250,300,350,400,450,500,550,600)
    var x = xlist.random()
    var y = 20

    val initialOokisa = 50 //ここで大きさを初期設定
    var ookisa = initialOokisa
    var timecount = 0
    var hit = false

    val iro = Paint()
    val irosub = Paint()

    val ITEM_NASI_STATE = 1
    val ITEM_SYUTUGEN_STATE = 2
    val ITEM_GET_STATE = 3
    val ITEM_OWARI_STATE = 4

    var status = ITEM_NASI_STATE // 最初は玉が画面内に無い状態
    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.YELLOW
        irosub.style = Paint.Style.FILL
        irosub.color = Color.LTGRAY
    }



    fun syokika(){
         x = xlist.random()
         y = 20
         ookisa = initialOokisa
        status = ITEM_SYUTUGEN_STATE
    }
    fun moveOne(){
        y+= 10
    }

    fun nextFrame(jiki:Jiki) {
        if (isAppearance) {
            when (status) {
                ITEM_NASI_STATE -> {
                    syokika()
                }

                ITEM_SYUTUGEN_STATE -> {
                    moveOne()
                    if (attaterukaCheck(jiki)) {
                        ookisa = 25
                        status = ITEM_GET_STATE
                    }
                }
                ITEM_GET_STATE -> {
                    ookisa /= 2
                    status = ITEM_OWARI_STATE

                }
                ITEM_OWARI_STATE -> {
                    ookisa /= 2
                    status =  ITEM_NASI_STATE
                }

            }
        }
    }

    fun attaterukaCheck(jiki:Jiki):Boolean {
        //これちゃんと当たってるのかな？なんか自分の大きさが考慮されてない気がする、、、
        //と思ったので、自分（Item）の大きさを２で割って加えてみた
        //それっぽく動いてるように見える。

        val x1 = jiki.x -jiki.ookisa / 2 - ookisa / 2
        val y1 = jiki.y -jiki.ookisa / 2 - ookisa / 2
        val x2 = jiki.x +jiki.ookisa / 2 + ookisa / 2
        val y2 = jiki.y +jiki.ookisa / 2 + ookisa / 2

        val isXInside = (x >= x1 && x <= x2)
        val isYInside = (y >= y1 && y <= y2)

        return isXInside && isYInside

    }

    fun shikakuRectXY(): Rect {
        val left = x  - ookisa / 2
        val right = x  + ookisa / 2
        val top = y  - ookisa / 2
        val bottom = y + ookisa / 2
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRectXY(), iro)  //自機
        if (status>2){drawyosumi(canvas)}
    }

    fun drawyosumi(canvas: Canvas) {
        //左上
        val left1 = (x-25)  - ookisa / 2
        val right1 = (x-25)  + ookisa / 2
        val top1 = (y-25)  - ookisa / 2
        val bottom1 = (y-25) + ookisa / 2
        val m1 = Rect(left1, top1, right1,bottom1)
        canvas.drawRect(m1, iro)  //自機

        //左下
        val left2 = (x-25)  - ookisa / 2
        val right2 = (x-25)  + ookisa / 2
        val top2 = (y+25)  - ookisa / 2
        val bottom2 = (y+25) + ookisa / 2
        val m2 = Rect(left2, top2, right2,bottom2)
        canvas.drawRect(m2, iro)  //自機


        //右下
        val left3 = (x+25)  - ookisa / 2
        val right3 = (x+25)  + ookisa / 2
        val top3 = (y+25)  - ookisa / 2
        val bottom3 = (y+25) + ookisa / 2
        val m3 = Rect(left3, top3, right3,bottom3)
        canvas.drawRect(m3, iro)  //自機


        //右上
        val left4 = (x+25)  - ookisa / 2
        val right4 = (x+25)  + ookisa / 2
        val top4 = (y-25)  - ookisa / 2
        val bottom4 = (y-25) + ookisa / 2
        val m4 = Rect(left4, top4, right4,bottom4)
        canvas.drawRect(m4, iro)  //自機


    }


}