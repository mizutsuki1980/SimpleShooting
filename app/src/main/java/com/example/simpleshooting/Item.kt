package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Item {
    var isAppearance = false

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
        status = ITEM_SYUTUGEN_STATE
         x = xlist.random()
         y = 20
    }
    fun moveOne(){
        y+= 10
    }

    fun nextFrame(jiki:Jiki,teki:Teki) {
        if (isAppearance) {
            when (status) {
                ITEM_NASI_STATE -> {
                    syokika()
                }

                ITEM_SYUTUGEN_STATE -> {
                    moveOne()
                    if (attaterukaCheck(jiki)) {
                        status = ITEM_GET_STATE
                    }
                }
                ITEM_GET_STATE -> {
                    status = ITEM_OWARI_STATE

                }
                ITEM_OWARI_STATE -> {
                    status =  ITEM_NASI_STATE
                }

            }
        }
    }

    fun attaterukaCheck(jiki:Jiki):Boolean {
        val x1 = jiki.x -jiki.ookisa / 2
        val y1 = jiki.y -jiki.ookisa / 2
        val x2 = jiki.x +jiki.ookisa / 2
        val y2 = jiki.y +jiki.ookisa / 2

        //これちゃんと当たってるのかな？なんか自分の大きさが考慮されてない気がする、、、
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
    }

}