package com.example.simpleshooting

import android.annotation.SuppressLint
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    val handler = android.os.Handler()
    var hyper = 50

    lateinit var xxx : MyCustomView
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display: Display = this.windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        findViewById<TextView>(R.id.textLabelGamenX).text="画面サイズ横 ${(point.x)}".toString()
        findViewById<TextView>(R.id.textLabelGamenY).text="画面サイズ縦 ${(point.y)}".toString()



        val custom = findViewById<MyCustomView>(R.id.mycustom)
        custom.post { custom.beginAnimation() }
        xxx = custom
        //あ、なんかカスタムもコピーされてきた変数みたいなもんなのか。ここの値をいじっても意味ないのかな？
        //        custom.jikiOkisa = hyper
        findViewById<MyCustomView>(R.id.mycustom).jikiOkisa = hyper



        findViewById<Button>(R.id.HyperButton).setOnClickListener {
            hyper = 180
            //これもだめ？だめかー

        }
        findViewById<Button>(R.id.setButton).setOnClickListener {
            mainTsugiNoSyori()
        }
        mainTsugiNoSyori()
    }



    fun mainTsugiNoSyori() {
        //あーこれはCustomViewとは関係なる、MainActivityでずっと動き続けて、その都度Customの値をセットしなおしてるわけだ。
        //だから当然、ここになんか書いてもCustomの値はいじれない、だろう

        findViewById<TextView>(R.id.textLabelX).text=xxx.clickX.toString()
        findViewById<TextView>(R.id.textLabelY).text=xxx.clickY.toString()

        //ここに書けば、CustomView内で設定したプロパティは取り出せる。じゃぁ逆はどーすんの？となる。
        //ここに追加すればcustomから値を取り出せる。でも本当にｘｘｘを経由しないとダメなんか？なんかできないのかなぁ。
        //まぁでも出来る。数値は取り出せる。
        findViewById<TextView>(R.id.textLabelTokuten).text="倒した敵の数 ${(xxx.scoreCount)}".toString()
        findViewById<TextView>(R.id.textLabelDamage).text="被弾した数 ${(xxx.dgCount)}".toString()
        findViewById<TextView>(R.id.textLabelTimer).text="フレーム数 ${(xxx.frame)}".toString()

        handler.postDelayed( { mainTsugiNoSyori() }, 100)
    }

}