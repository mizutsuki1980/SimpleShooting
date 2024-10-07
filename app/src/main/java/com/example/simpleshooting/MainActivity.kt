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
        findViewById<Button>(R.id.setButton).setOnClickListener {
            findViewById<TextView>(R.id.textLabelX).text=custom.clickX.toString()
            findViewById<TextView>(R.id.textLabelY).text=custom.clickY.toString()
        }

        findViewById<Button>(R.id.setButton).setOnClickListener {
            mainTsugiNoSyori()
        }
        mainTsugiNoSyori()
    }



    fun mainTsugiNoSyori() {
        findViewById<TextView>(R.id.textLabelX).text=xxx.clickX.toString()
        findViewById<TextView>(R.id.textLabelY).text=xxx.clickY.toString()
        handler.postDelayed( { mainTsugiNoSyori() }, 100)
    }

}