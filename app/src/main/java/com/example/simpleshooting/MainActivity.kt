package com.example.simpleshooting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val custom = findViewById<MyCustomView>(R.id.mycustom)
        custom.post { custom.beginAnimation() }
        //なんで        custom.post { custom.beginAnimation() }　って書くんですかね？
        //custom.beginAnimation()　じゃないんだ。
        //custom.post{ } って何？
    }
}