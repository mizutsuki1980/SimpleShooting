package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class JikiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int
    var hit = false

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


    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                // 現在の自機の場所に移動してリセット
                x = jiki.x
                y = jiki.y
                iro.color = Color.GREEN
                hit = false

                // 移動状態に
                status = NORMAL_STATE
            }

            NORMAL_STATE -> {
                //ひとつ上に弾を移動
                val tamaSpeed = 8.0
                val tamaPlus = 10 * tamaSpeed.toInt()
                y -= tamaPlus //自機の弾を上方向に動かす

                //当たっているかチェック
                val vx = x - teki.x
                val vy = y - teki.y
                val atariKyori = 5 + teki.ookisa / 2 //当たり判定の距離

                if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
                    //当たっていたら状態遷移
                    hit = true
                    status = TAMA_HIT_STATE
                    iro.color = Color.WHITE
                    ookisa = 30
                }

                // 画面外に出たら無しの状態に一旦遷移
                if (y < 5) {
                    status = TAMA_NASI_STATE
                }
            }
            TAMA_HIT_STATE -> {
                // ここで何フレームかカウントしてからでもいいが、とりあえず1フレームで次に行く
                hit = false //ヒットは１回のみカウントするので、すぐにfalseに
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

    fun draw(canvas: Canvas){
        canvas.drawRect(shikakuRectXY(), iro)  //自機
    }

}