package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect




class TekiTamaYama(jiki:Jiki, teki:Teki) {
    //一定時間で適当にでてくるようにする。

    var x = 100
    var y = 100
    var xfloat = 0f
    var yfloat = 0f
    var ookisa:Int
    var hit :Boolean
    val speed = 10
    var frame = 0
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
        val a = 0.004f  // 放物線の開き具合　//増やすと角度がきつくなる。減らすほど平行になる。0.01～0.001くらいの間で調整する

        val b = 1.1f     // 線形項（傾きのようなもの）　//0～1.5くらい　よくわからんがYが増える

        val c = 700 / 2f // 放物線の頂点の高さを中央に調整 //よくわからん　何で割る２なん？
        xfloat = frame-250.toFloat() // フレーム数をxとして利用
        //ここの数式を変えると－２５０なんてしなくてもいいのかなー？
        yfloat = a * xfloat * xfloat + b * xfloat + c
        xfloat = xfloat + 250f
        x = xfloat.toInt()
        y = yfloat.toInt()
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
        canvas.drawCircle(xfloat,yfloat,ookisa.toFloat(),iro)
    }

    fun syokika(){

        x = 100
        y = 100
        xfloat = 0f
        yfloat = 0f
        ookisa=10
        hit = false
        frame = 0
        status = NORMAL_STATE
        iro.style = Paint.Style.FILL
        iro.color = Color.BLUE   //argb(255, 255, 255, 200)
    }
    fun gamengaiCheck(){
        //画面外なら、最初へ状態遷移
        if (x > 690 || x < 0 || y > 1050 || y < 0){ status = TAMA_NASI_STATE }
    }


    fun nextFrame(jiki:Jiki) {
        frame += speed
        when(status) {
            TAMA_NASI_STATE -> {
                syokika()         //最初のリセット処理
            }
            NORMAL_STATE -> {
                moveOne(jiki)                //自機にひとつ近づくように弾を移動
                if(attaterukaCheck(jiki)) {                     //自機に当たっているかチェック
                    gotoHitState()
                }
                gamengaiCheck() //画面外

            // なら消す
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
