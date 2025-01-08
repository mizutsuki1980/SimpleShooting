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
                jikiKaraStart(jiki)         // 現在の自機の場所に移動してリセット
            }
            NORMAL_STATE -> {
                moveOne()                //ひとつ上に弾を移動
                attaterukaCheck(teki)    //当たっているかチェック
                if (y < 5) { status = TAMA_NASI_STATE } // 画面外に出たら無しの状態に一旦遷移
            }
            TAMA_HIT_STATE -> {
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                motoniModosu()  // もとに戻す
            }
        }
    }
    fun moveOne(){
        val tamaSpeed = 8.0
        val tamaPlus = 10 * tamaSpeed.toInt()
        y -= tamaPlus //自機の弾を上方向に動かす
    }
    fun jikiKaraStart(jiki:Jiki){
        x = jiki.x
        y = jiki.y
        iro.color = Color.GREEN
        hit = false
        status = NORMAL_STATE
    }
    fun motoniModosu(){
        hit = false //ヒットは１回のみカウントするので、すぐにfalseに
        ookisa = 10
        status = TAMA_NASI_STATE

    }
    fun hitCountSyori(){
        hit = true //ヒットは１回のみカウントするので、すぐにfalseに
        status = TAMA_HIT_END_STATE
    }

    fun attaterukaCheck(teki:Teki):Boolean {
        val vx = x - teki.x
        val vy = y - teki.y
        val atariKyori = 5 + teki.ookisa / 2 //当たり判定の距離

        if (vx < atariKyori && vx > -atariKyori && vy < atariKyori && vy > -atariKyori) {
            status = TAMA_HIT_STATE
            iro.color = Color.WHITE
            ookisa = 30
            return true
        }else {
            return false
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