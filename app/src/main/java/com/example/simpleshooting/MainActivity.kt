package com.example.simpleshooting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val custom = findViewById<MyCustomView>(R.id.mycustom)
        custom.post { custom.beginAnimation() }
        findViewById<Button>(R.id.setButton).setOnClickListener {
            findViewById<TextView>(R.id.textLabelX).text=custom.posX.toString()
            findViewById<TextView>(R.id.textLabelY).text=custom.posY.toString()
        }
    }
}