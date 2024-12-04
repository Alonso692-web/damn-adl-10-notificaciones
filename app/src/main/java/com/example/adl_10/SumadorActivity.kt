package com.example.adl_10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SumadorActivity : AppCompatActivity() {

    lateinit var tvContador: TextView
    lateinit var btnSumar: Button
    lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sumador)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvContador = findViewById(R.id.tvContador)
        btnSumar = findViewById(R.id.btnSumar)
        btnRegresar = findViewById(R.id.btnRegresarContador)

        tvContador.text = "Contador: 0"
        var contador = 0
        btnSumar.setOnClickListener {
            contador++
            tvContador.text = "Contador: $contador"
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}