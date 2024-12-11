package com.example.simpleshooting

import android.graphics.Color
import android.graphics.Paint

class JikiJoho(var jikiOokisa:Int,var jikiX:Int,var jikiY:Int,var m :IchiJoho ) {

    var iro = Paint()

    // var jikiOokisa
    // var jikiX
    // var jikiY
    // var m :IchiJoho //(IchiJouhouのjiki())
    var x :Int
    var y : Int
    //ということは、いうことは、、、全部作り直し？

    //なんとなくわかった、最初の数値をinitで決めれるのか。それがオブジェクトに入る。それをプロパティで取り出して使う、と。
    //あとから変更も可能、と。
    init {
        iro.style = Paint.Style.FILL
        iro.color = Color.RED

        val m = IchiJoho(0,0,50,10)
         x  =250
         y  =250
        jikiOokisa = 50


        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
        //ここで、書いたものは、いったいどういう動きをするんだろうか？
        //return とか使えないよな。どこにどう格納されんだよ。
        //        println("最初の初期化ブロックによる $jikiOokisa のプリント")

    }


    fun jikiSet(initialJikiX:Int,initialJikiY:Int,jikiOokisa:Int,tamaOkisa:Int):IchiJoho{
        val m = IchiJoho(initialJikiX,initialJikiY,jikiOokisa,tamaOkisa)
        m.iro.style = Paint.Style.FILL
        m.iro.color = Color.RED
        return m
    }

}