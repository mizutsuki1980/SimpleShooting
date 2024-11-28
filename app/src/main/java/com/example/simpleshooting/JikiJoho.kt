package com.example.simpleshooting

<<<<<<< HEAD
import android.graphics.Color
import android.graphics.Paint

class JikiJoho(var jikiX:Int, var jikiY:Int, var jikiOokisa:Int, var m: IchiJoho?) {
     //自機のクラスを作る。っていったい何をするんだ？
        //jikiOokisa
        //jikiX
        //jikiY
        //m (IchiJouhouのjiki())

        fun jikiSettei():IchiJoho{
            val m = IchiJoho(jikiX,jikiY,50,30)
            m.iro.style = Paint.Style.FILL
            m.iro.color = Color.RED
            return m
        }

    fun clickShitaBshoNiIdou(clickX:Int, clickY:Int, mx:Int, my:Int){
        val saX = mx - clickX
        val saY = my - clickY
        var x = mx
        var y = my
        val speed = 2.5
        val plus = 10 * speed .toInt()

        if (saX >= -(plus) && saX <= plus){
            x = clickX
        }else {
            if (saX > 0) {
                x -= plus
            }
            if (saX < 0) {
                x += plus
            }
        }
        if (saY >= -plus && saY <= plus){
            y = clickY
        }else {
            if (saY > 0) {
                y -= plus
            }
            if (saY < 0) {
                y += plus
            }
        }
        jikiX = x
        jikiY = y

        //なるほどここでｘ、ｙを返したいけど２つ値があるけど、どーすんの？ってことか。
        //あたらしく型をつくるのは面倒だよな。この為だけだし。

        //mx = x
        //my = y
    }
}
=======
class JikiJoho {
}
>>>>>>> FFF
