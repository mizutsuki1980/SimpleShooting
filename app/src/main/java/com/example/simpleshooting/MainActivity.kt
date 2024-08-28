package com.example.simpleshooting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    val handler = android.os.Handler()
    lateinit var xxx : MyCustomView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val custom = findViewById<MyCustomView>(R.id.mycustom)
        custom.post { custom.beginAnimation() }
        xxx = custom
        findViewById<Button>(R.id.setButton).setOnClickListener {
            findViewById<TextView>(R.id.textLabelX).text=custom.clickX.toString()
            findViewById<TextView>(R.id.textLabelY).text=custom.clickY.toString()
        }
        mainTsugiNoSyori()


    }

    fun mainTsugiNoSyori() {
        findViewById<TextView>(R.id.textLabelX).text=xxx.clickX.toString()
        findViewById<TextView>(R.id.textLabelY).text=xxx.clickY.toString()
        handler.postDelayed( { mainTsugiNoSyori() }, 100)
    }

}