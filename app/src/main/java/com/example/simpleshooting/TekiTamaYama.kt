package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect


//やまなりに左右からでてくる弾を作りたい
//とりあえずTekiTamaRefをコピペ。


class TekiTamaYama(jiki:Jiki, teki:Teki) {
    //一定時間で適当にでてくるようにする。

    var x = teki.x
    var y = teki.y
    var ookisa:Int
    var isfirst :Boolean
    var hit :Boolean
    val speed = 3.0

    val iro = Paint()
    val irosub = Paint()





    //状態遷移関連
    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態


    init{
        x = teki.x
        y = teki.y
        ookisa = 10
        isfirst = true
        hit = false

        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE   //argb(255, 255, 255, 200)

        //線を設定
        irosub.style = Paint.Style.STROKE
        irosub.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosub.strokeWidth = 4.0f
    }

    fun moveOne(jiki:Jiki){

    }

    fun moveHitedOne(jiki:Jiki){

    }


    fun attaterukaCheck(jiki:Jiki):Boolean {
        val vx = x - jiki.x
        val vy = y - jiki.y
        val kyori = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        val atarikyori = (jiki.ookisa).toDouble()
        if (kyori < atarikyori){
            return true
        }else{
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
        canvas.drawRect(shikakuRectXY(), iro)
    }

    fun tekiKaraStart(teki:Teki){


        status = NORMAL_STATE
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
            }
            TAMA_HIT_STATE -> {
                moveHitedOne(jiki)   //めりこむ感じ
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
                moveHitedOne(jiki)   //めりこむ感じ
                motoniModosu()  // もとに戻す
            }
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

}
