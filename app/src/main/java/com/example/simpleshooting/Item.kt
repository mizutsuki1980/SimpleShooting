package com.example.simpleshooting

import android.graphics.Paint

class Item {

    var x = 500
    var y = 500
    val initialOokisa = 50 //ここで大きさを初期設定
    var ookisa = initialOokisa
    var timecount = 0
    var hit = false

    val iro = Paint()
    val irosub = Paint()

    val ITEM_NASI_STATE = 1
    val ITEM_SYUTUGEN_STATE = 2
    val ITEM_GET_STATE = 3
    val ITEM_OWARI_STATE = 4

    var status = ITEM_NASI_STATE // 最初は玉が画面内に無い状態

}