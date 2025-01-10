package com.example.simpleshooting

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class TekiTama(var x:Int,var y:Int) {
    var iro = Paint()
    var ookisa:Int
    var homing :Boolean
    var hit :Boolean
    var zenkaix : Int
    var zenkaiy : Int
    var speed : Double
    var kakudo = 0
    var irosubMae = Paint()
    var irosubAto = Paint()
    var kisekix : Int
    var kisekiy : Int


    //なんかサインコサイン的なもので進行方向とは逆、みたいな指定ができそうだなー
    //    zenkaix = vx
    //    zenkaiy = vy
    //これとｘ、ｙがあればなんかできそうだな

    //なんかzenkaixとか使うと、にょーんとなってしまう。
    //前回の位置がはいってるんじゃねーのか？
    //zennkaixなら前回のｘだよなぁ、入ってるの。こういうことか。名前と挙動を一致させないと困るのは。


    //kakudo += 1
    //val kyori = jikiOokisa*7
    //var xx = kyori * Math.cos(kakudo.toDouble())/10
    //var yy = kyori * Math.sin(kakudo.toDouble())/10


    //ホーミング弾はどうしようかなぁ。後ろから炎でもだそうかなぁ。
    init{
        ookisa = 10
        iro.style = Paint.Style.FILL
        iro.color = Color.CYAN
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

//        irosubAto.style = Paint.Style.STROKE
  //      irosubAto.color = Color.YELLOW   //argb(255, 255, 255, 200)
    //    irosubAto.strokeWidth = 1.5f
    }

    fun move(jiki:Jiki, teki:Teki){
        var vx = jiki.x - x
        var vy = jiki.y - y
        var resetKyori = 90 //よけ始める距離
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
            //弾のリセットどうすんだろう？これでいっか
        }    //画面外で敵の弾のリセット
    }
    fun atariCheck(jiki:Jiki){
    //カウント処理がはいってるなー。そとにだしたほうがいいのか。ならBooleanであたった、とかつけるのかな？
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