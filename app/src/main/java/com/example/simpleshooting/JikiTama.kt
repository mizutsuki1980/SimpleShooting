package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int

    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    init {
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
    }

//こういう感じにしていく
    fun nextFrame() {
        when(status) {
            TAMA_NASI_STATE -> {
                // 現在の自機の場所に移動して
//                move(jikiX jikiY)
                // 移動状態に
                status = NORMAL_STATE
            }
            NORMAL_STATE -> {
//                moveOne() // 一つ上に移動
                if (atariCheck()) {
                    status = TAMA_HIT_STATE
                    ookisa = 30
                    iro. ....
                }
//                if (gamenGaiCheck()) {
                    // 画面外に出たら無しの状態に一旦遷移

//                    status = TAMA_NASI_STATE
//                }
            }
            TAMA_HIT_STATE -> {
                // ここで何フレームかカウントしてからでもいいが、とりあえず1フレームで次に行く
                status = TAMA_HIT_END_STATE
            }
            TAMA_HIT_END_STATE -> {
                // もとに戻す
                ookisa = 10
                status = TAMA_NASI_STATE
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


    fun reset(jikiX:Int,jikiY:Int){
        x = jikiX
        y = jikiY
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.GREEN
    }

    fun move(jiki:Jiki, teki:Teki) {
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed.toInt()
        y -= tamaPlus //自機の弾を上方向に動かす
        if (y < 5) {
            reset(jiki.x, jiki.y)
        }//画面の上部で消える

        if (ookisa == 30) {
            reset(jiki.x, jiki.y)
        }//最後に消える前に表示してから消える
    }

    fun atariCheck(jiki:Jiki, teki:Teki):Boolean{
        //「自分の弾」と「敵の位置」を計算して、近かったら敵は消滅、hitで確認してリセット。
        var hit = false

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


    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRectXY(), iro)  //自機
    }

}