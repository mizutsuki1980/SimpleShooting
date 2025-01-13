package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

//TekiTamaとTekiTamaRefにも状態遷移を入れる
//めんどー
//if(vx<resetKyori && vx > -resetKyori && vy<resetKyori && vy > -resetKyori){ homing = false }
// 的なやつもifを見やすくする。

//いっこいっこやらないと、わからなくなる


class TekiTama(var x:Int,var y:Int) {
    val iro = Paint()
    var ookisa:Int
    var homing :Boolean
    var hit :Boolean
    var zenkaix : Int
    var zenkaiy : Int
    var speed : Double
    var kakudo = 0
    val irosubMae = Paint()
    val irosubAto = Paint()
    var kisekix : Int
    var kisekiy : Int

    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.YELLOW
        homing = true
        hit = false
        zenkaix = x
        zenkaiy = y

        kisekix = x
        kisekiy = y

        speed = 2.0

        irosubMae.style = Paint.Style.STROKE
        irosubMae.color = Color.RED   //argb(255, 255, 255, 200)
        irosubMae.strokeWidth = 2.0f
    }

    fun tekiKaraStart(teki:Teki){}
    fun moveOne(jiki:Jiki){}
    fun attaterukaCheck(teki:Teki):Boolean{return true}
    fun gotoHitState(){}
    fun hitCountSyori(){}
    fun motoniModosu(){}

    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                tekiKaraStart(teki)         // 現在の自機の場所に移動してリセット
            }
            NORMAL_STATE -> {
                moveOne(jiki)                //ひとつ上に弾を移動
                if(attaterukaCheck(teki)) {                     //当たっているかチェック
                    gotoHitState()
                }
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

    fun move(jiki:Jiki, teki:Teki){
        var vx = jiki.x - x
        var vy = jiki.y - y
        val resetKyori = 90 //よけ始める距離
        if(vx<resetKyori && vx > -resetKyori && vy<resetKyori && vy > -resetKyori){ homing = false }
        if (homing == false) {

            vx = zenkaix
            vy = zenkaiy
        }
        zenkaix = vx
        zenkaiy = vy
        //敵の弾の移動
        val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        x += ((vx / v)*10 * speed).toInt()
        y += ((vy / v)*10 * speed).toInt()

        //弾は＋だけど、軌跡はマイナスにする
        kisekix = x-((vx / v)*10 * speed).toInt()
        kisekiy = y-((vy / v)*10 * speed).toInt()
        irosubMae.strokeWidth += 0.5f


        if (x > 690 || x < 0 || y > 1050 || y < 0){
            x = teki.x
            y = teki.y
            zenkaix = x
            zenkaiy = y

            kisekix = x
            kisekiy = y
            irosubMae.strokeWidth = 2.0f

            homing = true
        }
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
        canvas.drawLine(kisekix.toFloat(),kisekiy.toFloat(),x.toFloat(),y.toFloat(),irosubMae) //軌跡１の表示
        canvas.drawRect(shikakuRectXY(), iro)

    }

}