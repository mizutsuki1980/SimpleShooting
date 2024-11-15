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
    lateinit var custom : MyCustomView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val display: Display = this.windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        findViewById<TextView>(R.id.textLabelGamenX).text="画面サイズ横 ${(point.x)}".toString()
        findViewById<TextView>(R.id.textLabelGamenY).text="画面サイズ縦 ${(point.y)}".toString()

        custom = findViewById<MyCustomView>(R.id.mycustom)
        custom.post { custom.beginAnimation() }

        findViewById<Button>(R.id.tekiHyperButton).setOnClickListener {
            custom.post { custom.tekiHyperPowerUp() }
        }

        findViewById<Button>(R.id.hyperButton).setOnClickListener {
            custom.post { custom.hyperPowerUp() }
        }
        findViewById<Button>(R.id.hyperShotButton).setOnClickListener {
            custom.post { custom.hyperShotPowerUp() }
        }

        findViewById<Button>(R.id.setButton).setOnClickListener {
            custom.post { custom.startSetUp() }
        }


        mainTsugiNoSyori()
    }



    fun mainTsugiNoSyori() {
        findViewById<TextView>(R.id.textLabelX).text=custom.clickX.toString()
        findViewById<TextView>(R.id.textLabelY).text=custom.clickY.toString()

        findViewById<TextView>(R.id.scoreTextLabel).text=custom.scoreCount.toString()
        findViewById<TextView>(R.id.damageTextLabel).text=custom.dgCount.toString()
        findViewById<TextView>(R.id.timeTextLabel).text=custom.frame.toString()

        handler.postDelayed( { mainTsugiNoSyori() }, 100)
    }

}