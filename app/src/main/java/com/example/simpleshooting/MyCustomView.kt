package com.example.simpleshooting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyCustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var frame = 0
    var dgCount = 0
    var scoreCount = 0
    var isFirstMove = false //動きだしたら弾も出るようにする
    val jikiIchiTyousei = 120 //クリックした位置よりちょっと上にくる。そうしないと指に隠れて見えない。

    val initialJikiX = 300 //初期位置
    val initialJikiY = 800 //初期位置

    var clickX = initialJikiX  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置
    var clickY = initialJikiY  //自機の位置は覚えておかないといけないので必要 最初だけ初期位置

    var jiki =Jiki(initialJikiX, initialJikiY)
    var jikiTama = jiki.tamaHassha(jikiIchiTyousei)    //弾の発射位置もjikiIchiTyouseiの影響を受ける。
    var jikiKen = JikiKen(jiki)


    //こいつはだす、こいつはださない、的なオンオフスイッチをつくりたい。
    //当たり判定とかもオンオフする
    //isAppearance　だろうか？　出現を英訳　こいつをtrueとかfalseとかしてみるか

    var teki = Teki()

    var tekiTama = teki.tamaHassha()    //TekiTamaのオブジェクトを作るのは、TekiTama内でなくてもよい。へー

    var tekiTamaRef = TekiTamaRef(jiki,teki)

    var tekiTamaYama = TekiTamaYama(jiki,teki)

    var item = Item()

    var houdaiTama = HoudaiTama()
    var hpCounter = HPCounter()
    var scCounter = ScoreCounter()

    init{

    }


    fun jikiTamaAtattaSyori(){
       //攻撃方法が増えるたびに、ここにつかしなきゃなんないのかなー
        if (jikiTama.hit) {
            teki.hp -= 1
            if(teki.hp==0){
                scoreCount += 1
                teki = Teki()
            }
        }
        if (jikiKen.hit) {
            teki.hp = 0
            if(teki.hp==0){
                scoreCount += 1
                teki = Teki()
            }
        }
    }


    fun tekiTamaAtattaSyori(){
        dgCount += 1
        jiki.hp -= 1
    }

    fun beginAnimation() {
        tsugiNoSyori()  //最初に一回だけ呼ばれる
    }
    fun tekiKougekiHueru(){
        if(scoreCount==1){tekiTama.isAppearance=true}
        if(scoreCount==2){tekiTamaRef.isAppearance=true}
        if(scoreCount==3){houdaiTama.isAppearance=true}

        if(frame==200){tekiTama.isAppearance=true}
        if(frame==400){tekiTamaRef.isAppearance=true}
        if(frame==600){houdaiTama.isAppearance=true}
    }



    fun tekiTamaNextFrame(){

        if (tekiTama.isAppearance) {
            tekiTama.nextFrame(jiki, teki)
            if (tekiTama.hit) { tekiTamaAtattaSyori() }
         }

        if(tekiTamaRef.isAppearance){
            tekiTamaRef.nextFrame(jiki, teki)
            if (tekiTamaRef.hit) { tekiTamaAtattaSyori() }
        }
        if(houdaiTama.isAppearance) {
            houdaiTama.nextFrame(jiki, teki)
            if (houdaiTama.hit) { tekiTamaAtattaSyori() }
        }

        if(tekiTamaYama.isAppearance){
            tekiTamaYama.nextFrame(jiki)
            if(tekiTamaYama.hit){tekiTamaAtattaSyori()}
        }


    }


    fun tsugiNoSyori() {
        frame += 1  //繰り返し処理はここでやってる
        tekiKougekiHueru()
        invalidate()
        jiki.move(clickX, clickY - jikiIchiTyousei) //クリックした場所から上に170の場所に移動する。指にかかって見えない為。
        teki.yokoIdo()  //敵の移動　処理。
        teki.nextFrame(scoreCount)
        jikiTama.nextFrame(jiki, teki, isFirstMove)
        if (jikiTama.hit) { jikiTamaAtattaSyori() }
        jikiKen.nextFrame(jiki, teki, isFirstMove)
        if (jikiKen.hit) { jikiTamaAtattaSyori() }
        tekiTamaNextFrame() //敵の弾の処理は全部ここにまとめる
        item.nextFrame(jiki,jikiTama) //いったんここで作る、あとでtekiTamaNextFrame() に入れる
        //        if(item.hit){jikiTama.hit = true}//自機弾のリセット（Itemに当たったら、自機弾は消さなければならない。）
        //こういう風に書くと、敵に当たった、とも処理されてしまっている。なんか一瞬で敵がパワーアップしてしまう。
        //if(item.hit){jikiTama = jiki.tamaHassha(jikiIchiTyousei)}   //もしアイテムに弾が当たっていたら、弾のリセット処理をする
        //んー、なんかこの方法もダメだな。弾が透けないとダメなのかも。通過してくれないと、跳ね返ったアイテムがまた当たってしまう。
        //ツインビーはボタン押すまで次の弾がでなかったからか。

        if(item.hit){   //もしアイテムに弾が当たっていたら、弾のリセット処理をする、そしてワンフレーム分だけ動かす
            jikiTama = jiki.tamaHassha(jikiIchiTyousei)
            jikiTama.nextFrame(jiki, teki, isFirstMove)
        }

        if(jiki.hp == 0){
        }else{
            handler.postDelayed({ tsugiNoSyori() }, 100)
        }
    }




    override fun onDraw(canvas: Canvas) {
        //なんかこう、色合いが全体的にダメ。自機、敵、弾ははっきりわかんないとなー

        jiki.draw(canvas)   //自機の処理
        teki.draw(canvas) //敵jikiTamaの移動　処理
        item.draw(canvas) //アイテムの処理
        if(tekiTama.isAppearance){ tekiTama.draw(canvas)} //敵の追尾弾の移動　処理
        if(tekiTamaRef.isAppearance){ tekiTamaRef.draw(canvas)} //敵の反射弾の移動　処理
        if(houdaiTama.isAppearance){houdaiTama.draw(canvas)} //砲台の弾
        tekiTamaYama.draw(canvas) //山なりの弾
        if(isFirstMove){ jikiTama.draw(canvas)}     //自機の弾の処理
        //if(isFirstMove){ jikiKen.draw(canvas,jiki)}     //自機の剣の処理 　//剣は見ずらいからいらない、せっかく作ったけど。
        hpCounter.draw(canvas,jiki)
        scCounter.draw(canvas,scoreCount,frame)
        hantoumeinotamaDraw(canvas)
        if(jiki.hp == 0){
            gameover(canvas)

        }
    }

    fun gameover(canvas:Canvas){
        val hyoujiIro =  Paint()
        hyoujiIro.style = Paint.Style.FILL
        hyoujiIro.color = Color.BLUE
        hyoujiIro.textSize = 100.toFloat()

        canvas.drawText("Game Over",(120).toFloat(),(400).toFloat(),hyoujiIro)

    }
    fun hantoumeinotamaDraw(canvas:Canvas){
        val irohantoumei = Paint()
        irohantoumei.style = Paint.Style.FILL
        irohantoumei.color = argb(50, 255, 255, 255)
        canvas.drawCircle(clickX.toFloat(),clickY.toFloat(),(100).toFloat(),irohantoumei)    //半透明の円の表示

    }

    fun startSetUp(){
        frame = 0
        dgCount = 0
        scoreCount = 0
        isFirstMove = false //動きだしたら弾も出るようにする

        jiki =Jiki(initialJikiX, initialJikiY)
        jikiTama = jiki.tamaHassha(jikiIchiTyousei)    //弾の発射位置もjikiIchiTyouseiの影響を受ける。
        jikiKen = JikiKen(jiki)
        teki = Teki()
        tekiTama = teki.tamaHassha()    //TekiTamaのオブジェクトを作るのは、TekiTama内でなくてもよい。へー
        tekiTamaRef = TekiTamaRef(jiki,teki)
        houdaiTama = HoudaiTama()
        hpCounter = HPCounter()
        scCounter = ScoreCounter()

        tsugiNoSyori()


    }



    fun tekiHyperPowerUp(){
        if (teki.ookisa==140){
            teki.ookisa = 70
        }else{
            teki.ookisa = 140
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isFirstMove = true
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }



        if (event.action == MotionEvent.ACTION_UP) {
            isFirstMove = true
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            isFirstMove = true
            clickX = event.x.toInt()
            clickY = event.y.toInt()
            return true // 処理した場合はtrueを返す約束
        }
        return super.onTouchEvent(event)
    }
}

