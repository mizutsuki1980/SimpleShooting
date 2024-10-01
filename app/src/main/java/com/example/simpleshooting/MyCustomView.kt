package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
class myTama(var x:Int,var y:Int,jikiOokisa:Int,val tamaOokisa:Int,var alive:Boolean){
    var left = x  - tamaOokisa / 2
    var right = x  + tamaOokisa / 2
    var top = y  - (tamaOokisa)
    var bottom = y
    val tamaIro = Paint()
    fun mTRectXY(x:Int,y:Int,Ookisa:Int):Rect{
        left = x  - Ookisa / 2
        right = x  + Ookisa / 2
        top = y  - Ookisa
        bottom = y
        val m = Rect(left, top, right,bottom)
        return m
    }
}

class enemyUgoki(var x: Int, var y: Int, val enemyOokisa: Int) {
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


class eTama(var x:Int,var y:Int,enemyOokisa:Int,var enemyTamaOokisa:Int,var enemyTamaSpeed:Double,var zenkaiVect:List<Int>,var alive:Boolean,var homing:Boolean){
    var left = x  - enemyTamaOokisa / 2
    var right = x  + enemyTamaOokisa / 2
    var top = y  - (enemyTamaOokisa)
    var bottom = y
    val eTamaIro = Paint()
    fun eTRectXY(x:Int,y:Int,Ookisa:Int):Rect{
        left = x  - Ookisa / 2
        right = x  + Ookisa / 2
        top = y  - Ookisa
        bottom = y
        val m = Rect(left, top, right,bottom)
        return m
    }

}

class myUgoki(var x: Int, var y: Int, val Ookisa: Int){
    val left = x - Ookisa / 2
    val right = x + Ookisa / 2
    val top = y - Ookisa / 2
    val bottom = y + Ookisa / 2
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
    var enemyTamaSpeed = 2.0
    var jikiX = 300 //初期位置
    var jikiY = 800 //初期位置
    var clickX = jikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = jikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var jikiOokisa = 40
    var myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
    var myEnemy = enemyUgoki(150,150,50)
    var myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
    var vec = listOf(0,0)
    var eTama = eTama(150,150,100,10,enemyTamaSpeed,vec,false,true)
    var gameZokkouStats = true

    override fun onDraw(canvas: Canvas) {
        myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
        myJiki.jikiIro.style = Paint.Style.FILL
        myJiki.jikiIro.color = Color.WHITE
        canvas.drawRect(myJiki.myRect(myJiki.x,myJiki.y,myJiki.Ookisa), myJiki.jikiIro)
        clickShitaBshoNiIdou()

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
        eTama.eTamaIro.style = Paint.Style.FILL
        eTama.eTamaIro.color = Color.WHITE
        canvas.drawRect(eTama.eTRectXY(eTama.x,eTama.y,eTama.enemyTamaOokisa), eTama.eTamaIro)

        //ここであんまり近くに来すぎたら、ホーミングをオフにする。じゃないとよけられない。
        //eTamaにはベクトルの情報も保存しておかないといけないのかな？
        eTama.zenkaiVect = eTamaIdoSyori()

        //敵の弾が自機の近くにあったらリセット
        enemyTamaAtatta()
        val paint = Paint()

        //文字サイズを50に設定
        paint.textSize = 50f
        paint.strokeWidth = 5f
        paint.color = Color.WHITE


        //弾をすり抜けることがあった。なんで？一回目だけとか？
        //なるほど、上から下に移動するときに弾が自機の下の方を通るとすり抜けるな。
        
        if (gameZokkouStats) {
        }else{
            //なんか当たった判定できてね？これをlengthでやるのか。
        //敵の弾の座標ｘ、ｙ　と　自機の座標ｘ、ｙ　から　ベクトル座標ｘ、ｙ　を計算する。
            //ベクトル座標ｘを二乗　、　ベクトル座標ｙを二乗して　＋する。　それをルートでだす。
            //それがいわゆるＬｅｎｇｔｈになる。実際の長さ。距離。
            //これが１とかを下回ったら、あたったという判定にする。これでどうだ？

                canvas.drawText("GAME OVER", 200F, 300F, paint)
        }
        //ここで敵の弾に当たったら？判定を行い、当たったら終了
        //enemyTamaAtatta()　//というか、これがまさにそう？
    }

    fun textHyouzi(){

    }


    fun enemyTamaAtatta(){
        val ex = eTama.x
        val ey = eTama.y
        val jx = myJiki.x
        val jy = myJiki.y
        val saX = jx - ex
        val saY = jy - ey

        if (saX > -20 && saX < 20){
            if (saY > -20 && saY < 20) {
                //敵の弾のリセット
                eTama = eTama(myEnemy.x,myEnemy.y,100,10,enemyTamaSpeed,vec,false,true)
                gameZokkouStats = false
            }
        }

        if (ex > 700 || ex < 0){
            //敵の弾のリセット
            eTama = eTama(myEnemy.x,myEnemy.y,100,10,enemyTamaSpeed,vec,false,true)
        }

        if (ey > 1300 || ey < 0){
            //敵の弾のリセット
            eTama = eTama(myEnemy.x,myEnemy.y,100,10,enemyTamaSpeed,vec,false,true)
        }

    }


    fun eTamaIdoSyori():List<Int>{
        val ex = eTama.x
        val ey = eTama.y

        val jx = myJiki.x
        val jy = myJiki.y

        var vx = jx - ex
        var vy = jy - ey

        var resetKyori = 70

        if(vx<resetKyori && vx > -resetKyori){
            if(vy<resetKyori && vy > -resetKyori) {
                eTama.homing = false
            }
        }

        if (eTama.homing) {
        }else{
            vx = eTama.zenkaiVect[0]
            vy = eTama.zenkaiVect[1]
        }


        val vv = (vx * vx) + (vy * vy) .toDouble()
        val vvv = Math.sqrt(vv)
        val vvx = (vx / vvv)*10 * eTama.enemyTamaSpeed
        val vvy = (vy / vvv)*10 * eTama.enemyTamaSpeed

        eTama.x += vvx.toInt()
        eTama.y += vvy.toInt()

        return listOf(vx,vy)

    }




    fun tekiUgokasu(){
        if(myEnemy.x<900){
            myEnemy.x += 50
        }
        if(myEnemy.x >= 900) {
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

        val saX = jikiX - clickX
        val saY = jikiY - clickY
        var x = jikiX
        var y = jikiY
        val mySpeed = 2.0
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


        if (gameZokkouStats) {
            //くらったら止まる
            invalidate()
            handler.postDelayed({ tsugiNoSyori() }, 100)
        }else{

            //勝手に繰り返す
            reStart()
            gameZokkouStats = true
            handler.postDelayed({ tsugiNoSyori() }, 1500)
            //invalidate()を消すことで、いい感じに止まる


        }
    }


    fun reStart(){
        var tamaFrameIchi = 0
        var frame = 0
        var myJiki = myUgoki(jikiX,jikiY,jikiOokisa)
        var myEnemy = enemyUgoki(150,150,50)
        var myTama = myTama(jikiX,jikiY,jikiOokisa,tamaOokisa,false)
        var vec = listOf(0,0)
        var eTama = eTama(150,150,100,10,enemyTamaSpeed,vec,false,true)
        var gameZokkouStats = true

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

