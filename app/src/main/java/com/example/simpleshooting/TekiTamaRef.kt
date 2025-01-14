package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.graphics.Rect

class TekiTamaRef(jiki:Jiki, teki:Teki) {
    //reflection
    var x = teki.x
    var y = teki.y
    val iro = Paint()
    var ookisa:Int
    var homing :Boolean
    var hit :Boolean
    var spx : Int
    var spy : Int
    var speed : Double

    val irosubMae = Paint()
    val irosubAto = Paint()


    var x2 : Int
    var y2 : Int
    var x3 : Int
    var y3 : Int

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE


        homing = false

        hit = false
        spx = teki.x
        spy = teki.y

        x2 = spx
        y2 = spy

        x3 = x2
        y3 = y2

        speed = 3.0

        //線を設定
        irosubMae.style = Paint.Style.STROKE
        irosubMae.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubMae.strokeWidth = 4.0f

        irosubAto.style = Paint.Style.STROKE
        irosubAto.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosubAto.strokeWidth = 1.5f
    }

    fun move(jiki:Jiki){
        x3 = x2
        y3 = y2
        x2 = x
        y2 = y

        val xhanai =650
        val yHani = 900

        var vx = jiki.x - x
        var vy = jiki.y - y

        vx = spx
        vy = spy

        spx = vx
        spy = vy


        //zennkaiｘをｓｐｘに変更。スペシャルなｘ。わかりずれーかな？
        //多分、前回のＸっていう情報はなんかで必要なんじゃなかったかな？
        //反射ならいらないのかな？

        //ここで一回目だけの処理になっているのか、、、
        //ｖｘに自機と弾の差が入ります
        //ｖｘにzenkaiｘが入ります
        //ｚｅｎｎｋａｉｘにｖｘが入ります。
        //なんだこの構造は、、、、？と思ったが、なぜかうまく動いている。

        //たぶん、ここに入る毎にｖｘは元に戻っている。varがついてるから。この中でしか使えない。
        //zennkaixはメンバ変数、その値をずっと持ってる。
        //だから最初に定義されたｖｘ→ｚｅｎｎｋａｉｘはそのあと、ずっとｚｅｎｎｋａｉｘになる。
        //そんな感じか。

        //でｚｅｎｎｋａｉｘで毎回上書きされる。なので実質konkaiｘだ
        //それを下の方でifで書き換えている。これが反射の仕組みだ。
        //だからｚｅｎｎｋａｉｘにマイナスのｖｘを入れると反射するのだ。
        //わかりずらー


        //敵の弾の移動
        val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())

        //ｖｘにはｚｅｎｎｋａｉｘが入っている。なんで実質ｚｅｎｎｋａｉｘが今回の加算に使われている。
        x += ((spx / v)*10 * speed).toInt()
        y += ((spy / v)*10 * speed).toInt()
        if (x > xhanai || x < 0){spx = -vx }
        if (y > yHani || y < 0){spy = -vy}
    }

    fun atariCheck(jiki:Jiki){
        val vx = x - jiki.x
        val vy = y - jiki.y
        if(ookisa == 30){
            hit = true
        }else{
            val atariKyori = jiki.atariKyori()
            if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                iro.color = Color.DKGRAY
                ookisa = 30
            }
        }
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
        canvas.drawRect(shikakuRectXY(), iro)
        canvas.drawLine(x2.toFloat(),y2.toFloat(),x.toFloat(),y.toFloat(),irosubMae) //軌跡１の表示
        canvas.drawLine(x3.toFloat(),y3.toFloat(),x2.toFloat(),y2.toFloat(),irosubAto)  //軌跡２の表示
    }

}