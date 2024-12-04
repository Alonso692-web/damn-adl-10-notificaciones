package com.example.adl_10

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {

    lateinit var btnNotiBasica: Button
    lateinit var btnNotiExtendida: Button
    lateinit var btnNotiIntent: Button
    lateinit var btnNotiAccion: Button
    lateinit var btnSalir: Button

    private val canalId = "001"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnNotiBasica = findViewById(R.id.btnNotiBasica)
        btnNotiExtendida = findViewById(R.id.btnNotiExtendida)
        btnNotiIntent = findViewById(R.id.btnNotiIntent)
        btnNotiAccion = findViewById(R.id.btnNotiAccion)
        btnSalir = findViewById(R.id.btnSalir)

        crearCanalNotificaciones(canalId)

        btnNotiBasica.setOnClickListener {
            val titulo = "UPIIZ- IPN"
            val beca = "En breve le depositará su beca institucional \nMonto: $ 18,000"
            mostrarNotificacionBasica(this@MainActivity, titulo, beca, canalId)
        }

        btnNotiExtendida.setOnClickListener {
            val titulo = "Raúl Cardoso"
            val mensaje = "Buenas noches"
            val mensajeExtendido = "Alguien me puede ayudar con mi ejercicio 11 de aplicaciones móviles?"
            mostrarNotificacionExtendida(
                this@MainActivity,
                titulo,
                mensaje,
                mensajeExtendido,
                canalId
            )
        }

        btnNotiIntent.setOnClickListener {
            val titulo = "Mercado Libre"
            val mensaje = "Buenos dias, su paquete está por llegar, pendiente"
            mostrarNotificacionIntent(this@MainActivity, titulo, mensaje, canalId)
        }

        btnNotiAccion.setOnClickListener {
            startActivity(Intent(this@MainActivity, SumadorActivity::class.java))
        }

        btnSalir.setOnClickListener {
            finishAffinity()
        }


    }

    private fun mostrarNotificacionBasica(
        cotext: Context,
        titulo: String,
        mensaje: String,
        idCanal: String
    ) {

        val notificationId = (1..3).random()
        val notificacionBasica = NotificationCompat.Builder(cotext, idCanal)
            .setSmallIcon(R.drawable.ic_logo_ipn_svg) //icono
            .setContentTitle(titulo) //titulo
            .setContentText(mensaje) //texto
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //prioridad

        with(NotificationManagerCompat.from(cotext)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                solicitarPermisoNotificacion()
                return
            }
            notify(notificationId, notificacionBasica.build())
        }
    }

    private fun crearCanalNotificaciones(channelId: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nombreCanal = getString(R.string.channel_name)
            val descripcionCanal = getString(R.string.channel_description)
            val importanciaCanal = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel(channelId, nombreCanal, importanciaCanal).apply {
                description = descripcionCanal
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

    private fun solicitarPermisoNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }

    private fun mostrarNotificacionExtendida(
        contexto: Context,
        titulo: String,
        mensaje: String,
        mensajeExtendido: String,
        idCanal: String
    ) {
        val notificationId = 2
        val notificacionExtendida = NotificationCompat.Builder(contexto, idCanal)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(titulo) // titulo
            .setContentText(mensaje) //mensaje
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensajeExtendido))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // mostrar notificación
        with(NotificationManagerCompat.from(contexto)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                solicitarPermisoNotificacion()
                return
            }
            notify(notificationId, notificacionExtendida.build())
        }
    }

    private fun mostrarNotificacionIntent(
        contexto: Context,
        titulo: String,
        mensaje: String,
        canalId: String
    ) {
        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent:
                PendingIntent =
            PendingIntent.getActivity(this@MainActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificacionId = (1..3).random()

        val builder = NotificationCompat.Builder(contexto, canalId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Prioridad

        // Mostrar la notificación
        with(NotificationManagerCompat.from(contexto)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                solicitarPermisoNotificacion()
                return
            }

            notify(notificacionId, builder.build())
        }
    }

}