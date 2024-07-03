package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaX = 0
    var tamaY = 0
    override fun onDraw(canvas: Canvas) {
        val r = Rect(100, 100, 200, 200)
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.FILL
        canvas.drawRect(r, p)
        val ookisa = 100
        val jikiIchi = Rect(posX-ookisa/2, posY-ookisa/2, posX+ookisa/2, posY+ookisa/2)
        val jikiIro = Paint()
        if (frame % 2 ==1) {
            jikiIro.color = Color.WHITE
        }else {
            jikiIro.color = Color.GRAY
        }
        jikiIro.style = Paint.Style.FILL
        canvas.drawRect(jikiIchi, jikiIro)
        //これよる上は関係ない


        val tamaOokisa = 10
        //弾の大きさは固定

        val tamaPaint = Paint()
        tamaPaint.color = Color.YELLOW
        tamaPaint.style = Paint.Style.FILL
        //弾の塗りもとりあえず固定


        if (tamaX == 0){
            tamaX = posX
            tamaY = posY
            tamaFrame = 1
        }
        //もしtamax==0 初期値なら、弾が発射される。

        //ちゃんとtamaXを使う意味はあった。PosXにしたら弾が自機について行ってしまった。
        val xx = tamaX-tamaOokisa/2
        val xxx =tamaX+tamaOokisa/2

        kasoku = tamaFrame*90
        //加速は弾により違う。が、一定間隔で一緒の値

        val tamaIchi = Rect(xx, tamaY-kasoku-(tamaOokisa/2), xxx, tamaY-kasoku+tamaOokisa/2)
        //弾の位置を決める

        canvas.drawRect(tamaIchi, tamaPaint)
        //弾を描画


        val tamaNi = Rect(xx, tamaY-kasoku-(tamaOokisa/2)-100, xxx, tamaY-kasoku+tamaOokisa/2-100)
        //弾の位置を決める

        canvas.drawRect(tamaNi, tamaPaint)
        //弾を描画


        tamaFrame += 1
        //弾が進んだ処理をする。

        if (tamaY-kasoku  < 1){
            tamaX = 0
        }
        //ここで画面外にいたら、tamaXを初期値に戻す。



    }

    var tamaFrame = 0
    var kasoku = 0
    var frame = 0


    fun ippatuTama(){
        tamaX = posX
        tamaY = posY

    }

    fun tsugiNoTama() {
        //ｘ、ｙがきます
        //それに対して？Ｒｅｃｔに必要な４つの値を返します
        //経過している時間をしらべます、１、２，３、とか？
        //で表示する時には
        // repeat
        //      kasoku ＝ 経過時間[１]
        //      Ｒｅｃｔ（値A[１],値B[１]ーkasoku,値C[１],値D[１]ーkasoku+１０）
        //      Paint（）
        //      canvas.draw
        //end repeat
        //みたいな感じ？

        val tamaOokisa = 10

        if (tamaX == 0){
            tamaX = posX
            tamaY = posY
            tamaFrame = 1
        }
        //じゃ繰り返し弾が出るようにしてみようか
        kasoku = tamaFrame*90

        //Rectもしない、座標だけ返せばいい
        val tamaIchi = Rect(tamaX-tamaOokisa/2, tamaY-kasoku-(tamaOokisa/2), tamaX+tamaOokisa/2, tamaY-kasoku+tamaOokisa/2)

        //        canvas.drawRect(tamaIchi, tamaPaint)
        //canvsはできない

        //画面外に出たらリセット
        tamaFrame += 1
        if (tamaY-kasoku  < 1){
            tamaX = 0
        }

        //返さなきゃいけないのは、Rectに使う４つの値、加速したぶんのフレーム、posX、posY（最初に打った時の情報、でもこれRectの座標に含まれてない？）

    }





    fun tsugiNoSyori() {
        frame += 1
        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
    }
    //「var frame = 0 // tsugiNoSyoriでこのframeを更新する」 と書いてあるので、tsugiNoSyoriで行う

    fun beginAnimation() {
        tsugiNoSyori()
    }

    var posX = 500
    var posY = 1500
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}