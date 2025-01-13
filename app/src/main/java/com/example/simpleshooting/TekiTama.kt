package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

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


    fun nextFrame(jiki:Jiki,teki:Teki) {
        when(status) {
            TAMA_NASI_STATE -> {
                tekiKaraStart(teki)         //最初のリセット処理
            }
            NORMAL_STATE -> {
                moveOne(jiki)                //自機にひとつ近づくように弾を移動
                if(attaterukaCheck(jiki)) {                     //自機に当たっているかチェック
                    gotoHitState()
                }
                //画面外なら、最初へ状態遷移
                if (x > 690 || x < 0 || y > 1050 || y < 0){ status = TAMA_NASI_STATE }
            }
            TAMA_HIT_STATE -> {
                moveOne(jiki)   //めりこむ感じ
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                moveOne(jiki)   //めりこむ感じ
                motoniModosu()  // もとに戻す
            }
        }
    }



    fun tekiKaraStart(teki:Teki){
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.YELLOW
        homing = true
        hit = false
        x = teki.x
        y = teki.y
        zenkaix = x
        zenkaiy = y
        kisekix = x
        kisekiy = y

        irosubMae.strokeWidth = 2.0f

        status = NORMAL_STATE
    }

    fun moveOne(jiki:Jiki){
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
    }
    fun attaterukaCheck(jiki:Jiki):Boolean {
            val vx = x - jiki.x
            val vy = y - jiki.y
            val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
            val atarikyori = (jiki.ookisa).toDouble()
            if (v < atarikyori){
                return true
            }else{
                return false
            }
    }

    fun gotoHitState(){
        iro.color = Color.DKGRAY
        ookisa = 30
        status = TAMA_HIT_STATE
    }
    fun hitCountSyori(){
        hit = true
        status = TAMA_HIT_END_STATE
    }
    fun motoniModosu(){
        hit = false //hitだけ先に戻さないと二回カウントされてしまう。
        status = TAMA_NASI_STATE
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