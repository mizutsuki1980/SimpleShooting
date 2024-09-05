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




class myTama(jikiX:Int,jikiY:Int,jikiOokisa:Int,tamaOokisa:Int,var alive:Boolean){
    var left = jikiX  - tamaOokisa / 2
    var right = jikiX  + tamaOokisa / 2
    var top = jikiY  - (tamaOokisa)
    var bottom = jikiY
    val tamaIro = Paint()
    fun tamaRect(left:Int, top:Int, right:Int,bottom:Int): Rect {
        return  Rect(left, top, right,bottom)
    }
}



class enemyUgoki(var x:Int,var y: Int,val enemyOokisa:Int,) {
    val enemyIro = Paint()
    fun enemyIchi(x:Int,y:Int,enemyOokisa:Int): Rect {
        val left = x - enemyOokisa / 2
        val right = x + enemyOokisa / 2
        val top = y - enemyOokisa / 2
        val bottom = y + enemyOokisa / 2
        val enemyIchi = Rect(left, top, right, bottom)
        return enemyIchi
    }
}

class enemyTama(var x:Int,var y:Int,enemyOokisa:Int,var enemyTamaOokisa:Int,var enemyTamaSpeed:Double,var alive:Boolean){
    var left = x  - enemyTamaOokisa / 2
    var right = x  + enemyTamaOokisa / 2
    var top = y  - (enemyTamaOokisa)
    var bottom = y
    val enemyTamaIro = Paint()
    fun enemyTamaRect(left:Int, top:Int, right:Int,bottom:Int): Rect {
        return  Rect(left, top, right,bottom)
    }
}

class myUgoki(jikiX:Int,jikiY:Int,val jikiOokisa:Int,){
    //クリックしたポイントを中心に自機ができる
    val left = jikiX - jikiOokisa / 2
    val right = jikiX + jikiOokisa / 2
    val top = jikiY - jikiOokisa / 2
    val bottom = jikiY + jikiOokisa / 2
    val jikiIchi = Rect(left, top, right,bottom)
    val jikiIro = Paint()
}

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaFrameIchi = 0
    var frame = 0
    var tamaOokisa = 10
    var enemyTamaSpeed = 7.5
    var jikiX = 550 //初期位置
    var jikiY = 1500 //初期位置
    var clickX = jikiX
    var clickY = jikiY
    var jikiOokisa = 100
    var myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
    var myEnemy = enemyUgoki(150,150,100)
    var myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
    var enemyTama = enemyTama(150,150,100,10,enemyTamaSpeed,false)
    override fun onDraw(canvas: Canvas) {
        //まず、座標と大きさを指定して描画する
        myJiki = myUgoki(jikiX,jikiY,jikiOokisa)

        myJiki.jikiIro.style = Paint.Style.FILL
        myJiki.jikiIro.color = Color.WHITE
        myTama.tamaIro.style = Paint.Style.FILL
        myTama.tamaIro.color = Color.GREEN
        myEnemy.enemyIro.style = Paint.Style.FILL
        myEnemy.enemyIro.color = Color.BLUE
        enemyTama.enemyTamaIro.style = Paint.Style.FILL
        enemyTama.enemyTamaIro.color = Color.WHITE


        canvas.drawRect(myEnemy.enemyIchi(myEnemy.x,myEnemy.y,myEnemy.enemyOokisa), myEnemy.enemyIro)
        tekiUgokasu()

        canvas.drawRect(enemyTama.enemyTamaRect(enemyTama.left,enemyTama.top,enemyTama.right,enemyTama.bottom), enemyTama.enemyTamaIro)
        enemyTamaSyori()

        canvas.drawRect(myJiki.jikiIchi, myJiki.jikiIro)
        clickShitaBshoNiIdou()

        canvas.drawRect(myTama.tamaRect(myTama.left,myTama.top,myTama.right,myTama.bottom), myTama.tamaIro)
        tamaSyori()

        //敵の弾が自機の近くにあったらリセット
        enemyTamaAtatta()
    }

    fun enemyTamaAtatta(){
        val ex = enemyTama.left
        val ey = enemyTama.top

        val jx = myJiki.left
        val jy = myJiki.top

        val saX = jx - ex
        val saY = jy - ey
        if (saX > -20 && saX < 20){
            if (saY > -20 && saY < 20) {
                enemyTama.x = myEnemy.x
                enemyTama.y = myEnemy.y
                enemyTama.left = myEnemy.x  - enemyTama.enemyTamaOokisa / 2
                enemyTama.right = myEnemy.x  + enemyTama.enemyTamaOokisa / 2
                enemyTama.top = myEnemy.y  - (enemyTama.enemyTamaOokisa)
                enemyTama.bottom = myEnemy.y
                //enemyTama = enemyTama(myEnemy.x,myEnemy.y,100,10,enemyTamaSpeed,false)

            }
        }

    }
    fun enemyTamaSyori(){
        val ex = enemyTama.left
        val ey = enemyTama.top
        val jx = myJiki.left
        val jy = myJiki.top
        val vx = jx - ex
        val vy = jy - ey
        val vv = (vx * vx) + (vy * vy) .toDouble()
        val vvv = Math.sqrt(vv)
        val vvx = (vx / vvv)*10 * enemyTama.enemyTamaSpeed
        val vvy = (vy / vvv)*10 * enemyTama.enemyTamaSpeed
        //ここにベクトルをいれたい
        enemyTama.left += vvx.toInt()
        enemyTama.right += vvx.toInt()
        enemyTama.top += vvy.toInt()
        enemyTama.bottom += vvy.toInt()
    }


    fun vectorKeisan() {
        val ex = enemyTama.left
        val ey = enemyTama.top
        val jx = myJiki.left
        val jy = myJiki.top
        val vx = ex - jx
        val vy = ey - jy
        val vv = (vx * vx) + (vy * vy) .toDouble()
        val vvv = Math.sqrt(vv)
        //(vx,vy)がベクトルの方向
        //vvvが力

        val vvx = vx / vvv
        val vvy = vy / vvv
        //(vvx,vvy)がベクトルの正規化した結果　（0.6，0.8）とかになっているはず

        enemyTama.left += (vvx*10).toInt()
        enemyTama.right += (vvx*10).toInt()
        enemyTama.top += (vvy*10).toInt()
        enemyTama.bottom += (vvy*10).toInt()

    }




    fun tekiUgokasu(){
        if(myEnemy.x<1100){
            myEnemy.x += 50
        }
        if(myEnemy.x >= 1100) {
            myEnemy.x = -100
        }
    }


    fun tamaSyori(){
        //tamaSpeedはDouble型
        val tamaSpeed = 9.0
        val tamaPlus = 10 * tamaSpeed .toInt()


        if(myTama.top < 5){
            tamaFrameIchi = 0
            myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
        }

        if (tamaFrameIchi == 100){
            tamaFrameIchi = 0
            myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
        }


        if (tamaFrameIchi == 0) {
            tamaFrameIchi = 1
        }

        if (tamaFrameIchi > 0) {
            myTama.top -= tamaPlus
            myTama.bottom -= tamaPlus
        }

        tamaFrameIchi += 1
    }

    fun clickShitaBshoNiIdou(){
        //ななめ移動のとき、ｘもｙも移動してると２倍のスピードで動いてるように見えるなぁ。

        val saX = jikiX - clickX
        val saY = jikiY - clickY
        var x = jikiX
        var y = jikiY
        val mySpeed = 9.0
        val myPlus = 10 * mySpeed .toInt()

        if (saX >= -(myPlus) && saX <= myPlus){
            x = clickX
        }else {
            if (saX > 0) {
                x -= myPlus
            }
            if (saX < 0) {
                x += myPlus
            }
        }
        if (saY >= -myPlus && saY <= myPlus){
            y = clickY
        }else {
            if (saY > 0) {
                y -= myPlus
            }
            if (saY < 0) {
                y += myPlus
            }
        }
        jikiX = x
        jikiY = y
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (tamaFrameIchi==0){tamaFrameIchi=1}
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }
}

