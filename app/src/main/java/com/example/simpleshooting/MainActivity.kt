package com.example.simpleshooting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val custom = findViewById<MyCustomView>(R.id.mycustom)
        custom.post { custom.beginAnimation() }
        //なんで        custom.post { custom.beginAnimation() }　って書くんですかね？
        //custom.beginAnimation()　じゃないんだ。
        //custom.post{ } って何？

//        findViewById<TextView>(R.id.textLabelX).text=custom.post {custom.posX}.toString()
        findViewById<TextView>(R.id.textLabelY).text=custom.posY.toString()
        //ここは更新されてない気がする。だから最初の値のままなんじゃないかな。
        //なんでここもhandlerを使えばいいんじゃないかな。



    }
}