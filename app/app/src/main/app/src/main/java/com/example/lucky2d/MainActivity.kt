package com.example.lucky2d

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isMyanmar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#1a0000")) // Dark Red/Maroon theme background
            setPadding(50, 50, 50, 50)
        }

        val titleTextView = TextView(this).apply {
            textSize = 24f
            setTextColor(Color.parseColor("#FFD700")) // Gold color
        }

        val toggleButton = Button(this).apply {
            setBackgroundColor(Color.parseColor("#FFD700"))
            setTextColor(Color.parseColor("#1a0000"))
        }

        fun updateText() {
            if (isMyanmar) {
                titleTextView.text = "ကြိုဆိုပါတယ် - LUCKY 2D Demo"
                toggleButton.text = "ဘာသာစကားပြောင်းရန် (မြန်မာ/ไทย)"
            } else {
                titleTextView.text = "ยินดีต้อนรับ - LUCKY 2D Demo"
                toggleButton.text = "Change Language (ไทย/မြန်မာ)"
            }
        }

        toggleButton.setOnClickListener {
            isMyanmar = !isMyanmar
            updateText()
        }

        updateText()

        layout.addView(titleTextView)
        layout.addView(toggleButton)

        setContentView(layout)
    }
}

