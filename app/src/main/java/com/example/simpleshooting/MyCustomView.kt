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

class myTama(var x:Int,var y:Int,jikiOokisa:Int,val tamaOokisa:Int,var alive:Boolean){
    var left = x  - tamaOokisa / 2
    var right = x  + tamaOokisa / 2
    var top = y  - (tamaOokisa)
    var bottom = y
    val tamaIro = Paint()

    fun tamaRect(left:Int, top:Int, right:Int,bottom:Int): Rect {
        return  Rect(left, top, right,bottom)
    }

    fun mTRectXY(x:Int,y:Int,Ookisa:Int):Rect{
        left = x  - Ookisa / 2
        right = x  + Ookisa / 2
        top = y  - Ookisa
        bottom = y
        val m = Rect(left, top, right,bottom)
        return m
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

//ここにｘｙ渡すとＲｅｃｔ返す関数でも作ればいいんじゃねーの
    fun eTRect(left:Int, top:Int, right:Int,bottom:Int): Rect {
        return  Rect(left, top, right,bottom)
    }


    fun eTRectXY(x:Int,y:Int,Ookisa:Int):Rect{
         left = x  - Ookisa / 2
         right = x  + Ookisa / 2
         top = y  - Ookisa
         bottom = y
        val m = Rect(left, top, right,bottom)
        return m
    }

}

class myUgoki(var x:Int,var y:Int,val Ookisa:Int,){
    //クリックしたポイントを中心に自機ができる
    val left = x - Ookisa / 2
    val right = x + Ookisa / 2
    val top = y - Ookisa / 2
    val bottom = y + Ookisa / 2
    val jikiIchi = Rect(left, top, right,bottom)
    val jikiIro = Paint()

    fun myRect(x:Int,y:Int,Ookisa:Int):Rect{
        val left = x - Ookisa / 2
        val right = x + Ookisa / 2
        val top = y - Ookisa / 2
        val bottom = y + Ookisa / 2
        val jikiIchi = Rect(left, top, right,bottom)
        return jikiIchi
    }

}



class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var tamaFrameIchi = 0
    var frame = 0
    var tamaOokisa = 10
    var enemyTamaSpeed = 7.5
    var jikiX = 550 //初期位置
    var jikiY = 1500 //初期位置
    var clickX = jikiX  //自機の位置は覚えておかないといけないので必要
    var clickY = jikiY  //自機の位置は覚えておかないといけないので必要
    var jikiOokisa = 100
    var myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
    var myEnemy = enemyUgoki(150,150,100)
    var myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
    var eTama = enemyTama(150,150,100,10,enemyTamaSpeed,false)


    override fun onDraw(canvas: Canvas) {
        //まず、座標と大きさを指定して描画する
        myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
        //jikiX,jikiYをセットしなおしているので、自機オブジェクトのｘｙはここで更新されている。

        myJiki.jikiIro.style = Paint.Style.FILL
        myJiki.jikiIro.color = Color.WHITE
        canvas.drawRect(myJiki.myRect(myJiki.x,myJiki.y,myJiki.Ookisa), myJiki.jikiIro)
        clickShitaBshoNiIdou()

        //まずは動いているというところで成功か
        //敵の弾関連でどういう動きになるのかなぁ？

        myTama.tamaIro.style = Paint.Style.FILL
        myTama.tamaIro.color = Color.GREEN

        //自機の弾　処理
        canvas.drawRect(myTama.mTRectXY(myTama.x,myTama.y,myTama.tamaOokisa), myTama.tamaIro)
        tamaSyori()


        //敵の処理
        myEnemy.enemyIro.style = Paint.Style.FILL
        myEnemy.enemyIro.color = Color.BLUE
        canvas.drawRect(myEnemy.enemyIchi(myEnemy.x,myEnemy.y,myEnemy.enemyOokisa), myEnemy.enemyIro)
        tekiUgokasu()

        //敵の弾　処理
        eTama.enemyTamaIro.style = Paint.Style.FILL
        eTama.enemyTamaIro.color = Color.WHITE
        canvas.drawRect(eTama.eTRectXY(eTama.x,eTama.y,eTama.enemyTamaOokisa), eTama.enemyTamaIro)
        eTamaIdoSyori()





        //敵の弾が自機の近くにあったらリセット
        enemyTamaAtatta()
    }



    fun enemyTamaAtatta(){
        val ex = eTama.left
        val ey = eTama.top

        val jx = myJiki.x
        val jy = myJiki.y

        val saX = jx - ex
        val saY = jy - ey
        if (saX > -20 && saX < 20){
            if (saY > -20 && saY < 20) {
                //ここはあたった、ということでいいのかな？
                eTama.x = myEnemy.x
                eTama.y = myEnemy.y
                eTama.left = myEnemy.x  - eTama.enemyTamaOokisa / 2
                eTama.right = myEnemy.x  + eTama.enemyTamaOokisa / 2
                eTama.top = myEnemy.y  - (eTama.enemyTamaOokisa)
                eTama.bottom = myEnemy.y
                //enemyTama = enemyTama(myEnemy.x,myEnemy.y,100,10,enemyTamaSpeed,false)
            }
        }
    }

    fun eTamaIdoSyori(){
        val ex = eTama.x
        val ey = eTama.y
        val jx = myJiki.x
        val jy = myJiki.y
        val vx = jx - ex
        val vy = jy - ey
        val vv = (vx * vx) + (vy * vy) .toDouble()
        val vvv = Math.sqrt(vv)
        val vvx = (vx / vvv)*10 * eTama.enemyTamaSpeed
        val vvy = (vy / vvv)*10 * eTama.enemyTamaSpeed
        //なんかよくわからんけど、ここでベクトルっぽい動きになっているっぽい
        eTama.x += vvx.toInt()
        eTama.y += vvx.toInt()
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


        if(myTama.y < 5){
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
            myTama.y-= tamaPlus
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

