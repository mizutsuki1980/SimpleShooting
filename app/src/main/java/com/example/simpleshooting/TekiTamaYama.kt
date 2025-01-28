package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect


//やまなりに左右からでてくる弾を作りたい
//とりあえずTekiTamaRefをコピペ。


class TekiTamaYama(jiki:Jiki, teki:Teki) {
    //一定時間で適当にでてくるようにする。

    val initialFrame = -250 //ここでフレーム数を初期設定

    var x = 0f
    var y = 0f


    var frame = initialFrame

    var ookisa:Int
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
        ookisa = 10
        hit = false

        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE   //argb(255, 255, 255, 200)

        //線を設定 //いるかな？
        irosub.style = Paint.Style.STROKE
        irosub.color = Color.BLUE   //argb(255, 255, 255, 200)
        irosub.strokeWidth = 4.0f
    }

    fun moveOne(jiki:Jiki){
        val a = 0.01f  // 放物線の開き具合
        val b = 0f     // 線形項（傾きのようなもの）
        val c = 500 / 2f // 放物線の頂点の高さを中央に調整

         x = frame.toFloat() // フレーム数をxとして利用
         y = a * x * x + b * x + c

    }



    fun attaterukaCheck(jiki:Jiki):Boolean {
        //当たり判定はこれでオッケー
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

    fun draw(canvas: Canvas){
        canvas.drawCircle(x+250,y,ookisa.toFloat(),iro)
    }

    fun syokika(teki:Teki){
         x = 0f
         y = 0f
        frame = initialFrame

        ookisa = 10
        hit = false
        status = NORMAL_STATE
    }

    fun nextFrame(jiki:Jiki,teki:Teki) {
        frame += 1
        when(status) {
            TAMA_NASI_STATE -> {
                syokika(teki)         //最初のリセット処理
            }
            NORMAL_STATE -> {
                moveOne(jiki)                //自機にひとつ近づくように弾を移動
                if(attaterukaCheck(jiki)) {                     //自機に当たっているかチェック
                    gotoHitState()
                }
            }
            TAMA_HIT_STATE -> {
                hitCountSyori() //ヒット処理して次へ
            }
            TAMA_HIT_END_STATE -> {
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
