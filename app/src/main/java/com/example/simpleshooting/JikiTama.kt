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
    fun gotoHitState(){
        iro.color = Color.WHITE
        ookisa = 30
        status = TAMA_HIT_STATE
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
    fun attaterukaCheckVec(teki:Teki):Boolean {
        val vx = x - teki.x
        val vy = y - teki.y
        val v = Math.sqrt((vx * vx) + (vy * vy) .toDouble())
        val atarikyori = (teki.ookisa-10).toDouble()
        //なんか当たってないのに当たってるように見えるので、－10してる。円だから？
        //となると、ベクトルを使うと、なんかいまいちになるのか。へー
        if (v < atarikyori){
            return true
        }else{
            return false
        }

    }


    fun attaterukaCheck(teki:Teki):Boolean {
        //いろいろ作って思ったが、あれ、これってベクトルじゃね？
        val vx = x - teki.x
        val vy = y - teki.y
        val atariKyori = 5 + teki.ookisa / 2 //当たり判定の距離

        val x1 = teki.x -teki.ookisa / 2
        val y1 = teki.y -teki.ookisa / 2
        val x2 = teki.x +teki.ookisa / 2
        val y2 = teki.y +teki.ookisa / 2

        var xflag = false
        var yflag = false
        if (x >= x1 && x <= x2){xflag=true}
        if (y >= y1 && y <= y2){yflag=true}

        if (xflag && yflag){
            return true
        }else{
            return false
        }

        //ｖｘっていうのは敵ｘと弾ｘの差
        //ｖｙっていうのは敵ｙと弾ｙの差
        //マイナスもありうる

        //ａｔａｒｉ距離ってのは敵の大きさの半分、つまりａｔａｒｉ距離ではない。敵の大きさの半分
        //ａｔａｒｉ範囲ならａｔａｒｉ範囲ｘ１、ｙ１、ｘ２、ｙ２ときめちまいえばいいべ

        //敵はこんな風にきめている
        //        fun shikakuRectXY(): Rect {
        //            val left = x  - ookisa / 2
        //            val right = x  + ookisa / 2
        //            val top = y  - ookisa / 2
        //            val bottom = y + ookisa / 2
        //            val m = Rect(left, top, right,bottom)
        //            return m
        //        }
        //これが敵の表示されている部分。ここに入ったらあたりになる。
        //なんでｘ１（左肩）は敵ｘーｏｏｋｉｓａ/２とかわかってる。



        //んん－これは何をチェックしているんだ。たぶん距離だけど。

        //ｖｘがａｔａｒｉ距離より小さくて
        //ｖｘがマイナスのａｔａｒｉ 距離より小さくて
        //さらに
        //ｖｙがａｔａｒｉ距離より小さくて
        //ｖｙがマイナスのａｔａｒｉ距離より小さい
        //if,ifっていっぱい作った方がいいってことか。まぁそうなのかも。


        // でも全部条件に一致しないとダメってことだよなぁ。flagを二つ使うとか？ｘ軸はオッケー、ｙ軸はオッケーみたいな。
        //あー、なるほど、ＸとかＹがマイナスなんじゃないかってところをカバーしてるのか。でも座標ってプラスしかないよね。
        //ちがうな。ちゃんとマイナスを考えているっぽい。消したら左側が全部あたりになってしまった。

        //この一文に全部はいってるのすげーな。なんなん。
        //たぶん、右上より左下、左上より右下、右下より左上、左下より右上、全部に該当するもの、ってことかな。
        //敵の位置きまってます、ｘｙ　から　ｘ＋？？？ｙ＋？？？
        //弾の位置きまってますｘ、ｙ
        //これを比較しているだけ
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