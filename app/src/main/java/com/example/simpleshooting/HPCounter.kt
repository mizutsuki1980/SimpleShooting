package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.ColorSpace.Rgb
import android.graphics.Paint
import android.graphics.Rect

class HPCounter {

    //shikakuRect1~5をなんか変数にまとめて一気に動かせるような感じにしたい。
    //ベースとなる座標を決めて、動かせるようにしたい。たぶんできるだろう。

    val iro = Paint()
    val irosub = Paint()

    //まず元となる四角を作って、そこにＨＰの四角をおいていく。
    //それが190×30の四角
    //それをＭａｘＨＰで割る。今回は6
    //それぞれに枠をつけたいから、6+1となる
    //枠は幅10とする。
    //なんで190-（10*(6+1)）となる
    //今回は120を6で割ることになる。20だ。
    //10+20+10+20+10+20+10+20+10+20+10+20+10という感じか。

    //んー、なんかよくわからんくなってきた。
    //表示するときにはCanavasがいるから、Drawの中で繰り返さなきゃならんのか。
    //for(a in 1..<6) {　とかで描画しながら増やすとかやっていきたかったが
    //最初にリスト作るほうがいいのか？


    var yokohaba = 190
    var tatehaba = 50
    var waku = 10
    var x = 500
    var y = 0

    init {
        iro.style = Paint.Style.FILL
        iro.color = argb(255, 255, 200, 200)
        irosub.style = Paint.Style.FILL
        irosub.color = argb(255, 255, 255, 200)
        //線を設定
        //irosub.style = Paint.Style.STROKE
        //irosub.strokeWidth = 5.0f

    }
    fun shikakuRect(): Rect {
        //元になる四角をつくる、ここはOK
        val left = x
        val right = x+yokohaba//690
        val top = y
        val bottom = y+50 //50
        val m = Rect(left, top, right,bottom)
        return m
    }

    fun hpShikakuRect(hp:Int): Rect {
        //1なら、、、？   10              と   10+20
        //2なら、、、、   10+20+10    　   と　10+20+10+20　
        //3なら、、、、   10+20+10+20+10　 と　10+20+10+20+10+20
        val hidarikata = x + waku*hp + 20*(hp-1)
        val migikata =  hidarikata+20
        val left = hidarikata
        val right = migikata
        val top = y + waku
        val bottom = y+tatehaba - waku //50
        val m = Rect(left, top, right,bottom)
        return m
    }




    fun draw(canvas: Canvas,jiki:Jiki){
        canvas.drawRect(shikakuRect(), iro)
        for(a in 0..<jiki.hp) {
            //なんかマイナスになっても動き続けるんだな。へー
            canvas.drawRect(hpShikakuRect(a+1), irosub)
        }
    }

}