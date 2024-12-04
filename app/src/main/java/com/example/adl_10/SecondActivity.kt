package com.example.adl_10

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date

class SecondActivity : AppCompatActivity() {

    lateinit var tvHora: TextView
    lateinit var btnSalir: Button
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvHora = findViewById(R.id.tvHora)
        btnSalir = findViewById(R.id.btnRegresarSecondActivity)

        startTimer()

        btnSalir.setOnClickListener {
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
        }
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                // Obtener la hora actual en el formato deseado
                val sdf = SimpleDateFormat("HH:mm:ss")
                val currentTime = sdf.format(Date())

                // Actualizar el TextView
                tvHora.text = "Hora: " + currentTime

                // Ejecutar de nuevo cada segundo
                handler.postDelayed(this, 1000)
            }
        })
    }

}