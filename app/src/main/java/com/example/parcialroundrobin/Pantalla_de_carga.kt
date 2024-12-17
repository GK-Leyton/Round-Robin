package com.example.parcialroundrobin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import objetos.Proceso


class PantallaDeCarga : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_de_carga)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val proxima = intent.getStringExtra("proximaPagina")


        //Toast.makeText(this,"Valor recivido en pantalla de carga" + cuenta, Toast.LENGTH_SHORT).show()
        val imagen = findViewById<ImageView>(R.id.gifCarga)
        Glide.with(this)
            .load(R.drawable.icono_pantalla_carga)
            .into(imagen);

        //*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/
        //*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/
        if (proxima.equals("main")) {


            val itemListIntent: List<Proceso>? = intent.getParcelableArrayListExtra("itemList")
            val ruta = MainActivity::class.java
            Thread {
                val intent = Intent(this, ruta).apply {
                    putExtra("itemList", ArrayList(itemListIntent))

                }
                Thread.sleep(100)
                startActivity(intent)
            }.start()
        }


        if (proxima.equals("mainVacio")) {
            val ruta = MainActivity::class.java
            Thread {
                val intent = Intent(this, ruta).apply {
                }
                Thread.sleep(200)
                startActivity(intent)
            }.start()
        }

        if (proxima.equals("ejecutar")) {

            val itemListIntent: List<Proceso>? = intent.getParcelableArrayListExtra("itemList")
            val quantum = intent.getStringExtra("quantum").toString().toInt()

            val ruta = if ((!itemListIntent.isNullOrEmpty()) && (quantum != null) && (quantum > 0)) {
                Ejecutar_procesos::class.java
            } else {
                MainActivity::class.java
            }

            if ((itemListIntent.isNullOrEmpty()) || (quantum == null) || (quantum <= 0)) {
                Toast.makeText(this,"Asegurate de tener minimo un proceso y un quantum mayor a 0", Toast.LENGTH_SHORT).show()
            }

            Thread {
                val intent = Intent(this, ruta).apply {
                    putExtra("itemList", ArrayList(itemListIntent))
                    putExtra("quantum", quantum.toString())
                }
                Thread.sleep(500)
                startActivity(intent)
            }.start()
        }

    }
        override fun onBackPressed() {
        }

    }



