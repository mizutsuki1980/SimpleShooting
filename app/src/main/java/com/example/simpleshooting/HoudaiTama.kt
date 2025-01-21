package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint

class HoudaiTama {
    var x :Int
    var y :Int
    var ookisa : Int
    var hit :Boolean
    var zenkaix : Int
    var zenkaiy : Int
    var speed : Double
    val iro = Paint()

    val TAMA_NASI_STATE = 1
    val NORMAL_STATE = 2
    val TAMA_HIT_STATE = 3
    val TAMA_HIT_END_STATE = 4

    var status = TAMA_NASI_STATE // 最初は玉が画面内に無い状態

    init{
        x = 0
        y = 0
        ookisa = 10
        hit = false
        zenkaix = 0
        zenkaiy = 0
        speed = 2.0
        iro.style = Paint.Style.STROKE
        iro.color = Color.RED
    }
    //固定の場所から打ち出される砲台みたいなイメージ
    //山なりな軌道を作ってみたい。
    fun tekiKaraStart(teki:Teki) {
    }
    fun moveOne(jiki:Jiki){}
    fun attaterukaCheck(jiki:Jiki):Boolean{return false}
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