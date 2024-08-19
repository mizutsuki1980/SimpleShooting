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


    class myJikinoUgoki(posXx:Int,posYy:Int,myookisa:Int){
        val left = posXx - myookisa / 2
        val top = posYy - myookisa / 2
        val right = posXx + myookisa / 2
        val bottom = posYy + myookisa / 2

        val jikiIchi = Rect(left, top, right,bottom)

        //色は設定できないっぽいなぁ。
                val jikiIro = Paint()
        //jikiIro.style = Paint.Style.FILL
        //jikiIro.color = Color.WHITE


    }

    class myTamanoUgoki(posXx:Int,posYy:Int,myTamaNoOkisa:Int,myFrame:Int,tamasokudo:Int,val tamaFrame:Int){
        val myX = posXx
        val myY = posYy
        val susumu = tamaFrame * tamasokudo
        val xx = myX - myTamaNoOkisa / 2
        val xxx = xx + myTamaNoOkisa
        val yy = myY - susumu - (myTamaNoOkisa / 2)
        val yyy = yy + myTamaNoOkisa
        val tamaIchi = Rect(xx,yy,xxx,yyy)


        fun hantei():Boolean{
            var seizon :Boolean
            if (myY - susumu < 1) {
                 seizon = true
            }else{
                 seizon = false
            }
            return seizon
        }
    }

    class myEnemy(posXx:Int,posYy:Int,val myEnemyNoOkisa:Int,myFrame:Int,enemySokudo:Int,val enemyFrame:Int){
        //なんかすごく似たようなものを作ることになる。これが継承ってやつを使うポイントなのかも
        val myX = posXx
        val myY = posYy

        val susumu = enemyFrame * enemySokudo

        //ここでsusumuがどこにプラスされるか、マイナスされるか、でどう動くのか決まる
        val xx = myX - (myEnemyNoOkisa / 2 ) + susumu
        val xxx = xx + myEnemyNoOkisa
        val yy = myY - (myEnemyNoOkisa / 2)
        val yyy = yy + myEnemyNoOkisa
        val enemyPosition = Rect(xx,yy,xxx,yyy)

        //消える条件をつくる


        //タイプを別けて動きを変える




        //ちゃんといってるっぽい
        fun enemyhantei():Boolean{
            var seizon :Boolean
            if (myX + susumu > (800)) {
                seizon = true
            }else{
                seizon = false
            }
            return seizon
        }
    }



class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamasokudo = 100
    var tamaFrameIchi = 0

    var enemyFrameIchi = 0

    var tamaFrameNi = 0
    var tamaFrameSan = 0
    var frame = 0
    var tamaOokisa = 10

    var myTamaIchi = myTamanoUgoki(0,0,tamaOokisa,frame,tamasokudo,tamaFrameIchi)
    var myTamaNi = myTamanoUgoki(0,0,tamaOokisa,frame,tamasokudo,0)
    var myTamaSan = myTamanoUgoki(0,0,tamaOokisa,frame,tamasokudo,0)
    var myEnemey = myEnemy(0,0,100,frame,100,0)

        override fun onDraw(canvas: Canvas) {
        val ookisa = 100
        val myJiki = myJikinoUgoki(posX,posY,ookisa)
        //これもクラスに移動できるのか？
            val jikiIro = Paint()
            jikiIro.style = Paint.Style.FILL
            jikiIro.color = Color.WHITE
        canvas.drawRect(myJiki.jikiIchi, jikiIro)


        val tamaIchiPaint = Paint()
        tamaIchiPaint.style = Paint.Style.FILL
        tamaIchiPaint.color = Color.RED

        val tamaNiPaint = Paint()
        tamaNiPaint.style = Paint.Style.FILL
        tamaNiPaint.color = Color.BLUE

        val enemyPaint = Paint()
        enemyPaint.style = Paint.Style.FILL
        enemyPaint.color = Color.YELLOW


        //敵処理
        myEnemey = myEnemy(0, 0, 100, frame, 50, enemyFrameIchi)
        canvas.drawRect(myEnemey.enemyPosition, enemyPaint)
        enemyFrameIchi += 1

        if (myEnemey.enemyhantei()){
            enemyFrameIchi = 0
        }

        //弾①処理
        if (tamaFrameIchi>=1) {
            if (tamaFrameIchi== 1){
                myTamaIchi = myTamanoUgoki(posX,posY,tamaOokisa,frame,tamasokudo,tamaFrameIchi)
            }else {
                myTamaIchi = myTamanoUgoki(myTamaIchi.myX,myTamaIchi.myY,tamaOokisa,frame,tamasokudo,tamaFrameIchi)
            }
            canvas.drawRect(myTamaIchi.tamaIchi, tamaIchiPaint)
            tamaFrameIchi += 1
            if (myTamaIchi.hantei()){
                tamaFrameIchi = 1
            }
        }


        //弾②処理
        if (tamaFrameNi==0) {
            if (tamaFrameIchi == 5) {
                tamaFrameNi = 1
            }
        }
        if (tamaFrameNi>=1) {
            if (tamaFrameNi== 1){
               myTamaNi = myTamanoUgoki(posX,posY,tamaOokisa,frame,tamasokudo,tamaFrameNi)
            }else {
                myTamaNi = myTamanoUgoki(myTamaNi.myX,myTamaNi.myY,tamaOokisa,frame,tamasokudo,tamaFrameNi)
            }
            canvas.drawRect(myTamaNi.tamaIchi, tamaNiPaint)
            tamaFrameNi += 1
            if (myTamaNi.hantei()){
                tamaFrameNi = 0
            }
        }
    }

    fun pointVec(x:Int,y:Int,xx:Int,yy:Int):List<Int>{
        val qx = x - xx
        val qy = y - yy
        val q = listOf(qx,qy)
        return q
    }





    fun tsugiNoSyori() {
        frame += 1


        val ccFrame = frame % 100
        if(ccFrame <= 25) {
            tamaOokisa = 20
        }
        if(ccFrame > 25) {
            tamaOokisa = 30
        }
        if(ccFrame > 50) {
            tamaOokisa = 40
        }
        if(ccFrame > 75) {
            tamaOokisa = 60
        }



        invalidate()
        handler.postDelayed( { tsugiNoSyori() }, 100)
    }

    fun beginAnimation() {
        tsugiNoSyori()
    }

    var posX = 300
    var posY = 300
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            posX = event.x.toInt()
            posY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }


}