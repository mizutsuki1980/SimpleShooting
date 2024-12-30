package com.example.simpleshooting

import android.graphics.Canvas
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


    fun reset(jikiX:Int,jikiY:Int){
        x = jikiX
        y = jikiY
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
    }

//        fun tamaSyori(atariKyori:Int,jiki:JikiJoho,teki:TekiJoho):Boolean {


    fun atariKyoriTama():Int{
        var atariKyori = 5 +ookisa/2 //当たり判定の距離
        if(ookisa==200){
            atariKyori -= 5
        }//大きいときは、ちょっと当たり判定をマイナスする
        return atariKyori
    }

    //    fun tamaSyoriTekiJoho(jiki:JikiJoho,teki:TekiJoho):Boolean{
    fun tamaSyoriTekiJoho(jiki:JikiJoho,teki:Teki):Boolean{
        var hit = false
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()

        y -= tamaPlus //自機の弾を上方向に動かす

        if(y<5){
            reset(jiki.x,jiki.y)
        }//画面の上部で消える

        if(ookisa == 30){
            reset(jiki.x,jiki.y)
        }//最後に消える前に表示してから消える


        //「自分の弾」と「敵の位置」を計算して、近かったら敵は消滅、hitで確認してリセット。
        //というかatariKyoriなんてべつに関数にしなくてもいいんじゃね？その場で計算すれば。
        //自機、弾、敵の情報はわかってんだし。

        val vx = x - teki.x
        val vy = y - teki.y
        val atariKyori = 5 + teki.ookisa/2 //当たり判定の距離

        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            iro.color = Color.WHITE
            ookisa = 30
            hit = true
        }
        return hit
    }

    fun tamaSyori(atariKyori:Int,jiki:JikiJoho,e:IchiJoho):Boolean {
        var hit = false
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed .toInt()
        y -= tamaPlus
        if(y<5){
            reset(jiki.x,jiki.y)
        }        //画面の上部で消える

        //で次に、ここで何をやってるのか？「自分の弾」と「敵の位置」を計算して、近かったら消滅、リセット。
        if(ookisa == 30){
            reset(jiki.x,jiki.y)
        }


        val vx = x - e.x
        val vy = y - e.y

        if(vx<atariKyori && vx > -atariKyori && vy<atariKyori && vy > -atariKyori){
            iro.color = Color.WHITE
            ookisa = 30
            hit = true
        }
        return hit
    }

    fun draw(canvas: Canvas){

        canvas.drawRect(shikakuRectXY(), iro)  //自機
    }

}