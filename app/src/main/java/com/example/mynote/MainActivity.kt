package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    val add = findViewById<Button>(R.id.button)

    add.setOnClickListener{
        val intent = Intent(this@MainActivity, AddNote::class.java)
        startActivity(intent)
    }
    }
}