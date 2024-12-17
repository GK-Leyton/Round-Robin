package com.example.parcialroundrobin

import DialogHelperExplicacionParte1
import DialogHelperVideoTutorial
import PopupHelperCreador
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import objetos.Proceso
import popups_y_adicionales.ItemDragHelperCallback
import popups_y_adicionales.PopupHelperCrearRobin
import popups_y_adicionales.PopupHelperPreguntaInformacion
import popups_y_adicionales.ProcesosAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProcesosAdapter
    private var itemsList = mutableListOf<Proceso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCrearRobin = findViewById<Button>(R.id.btnCrearRobin)
        val creadorRobin = findViewById<ImageButton>(R.id.btnCreadorRobin)
        val btnVideoTutorialRobin = findViewById<ImageButton>(R.id.btnVideoTutorialRobin)
        val btnListo = findViewById<Button>(R.id.btnListo)
        val btnRecargar = findViewById<ImageButton>(R.id.btnRecargar)
        val btnPreguntaInformacion = findViewById<ImageButton>(R.id.btnPregunta)
        val btnOrdenar = findViewById<ImageButton>(R.id.btnOrdenar)
        val btnPasosRobin = findViewById<ImageButton>(R.id.btnPasosRobin)
        val btnSumarQuantum = findViewById<ImageButton>(R.id.btnSumarQuantum)
        val btnRestarQuantum = findViewById<ImageButton>(R.id.btnRestarQuantum)
        val txtQuantum = findViewById<TextView>(R.id.txtValorCuantum)
        // Configurar RecyclerView y Adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProcesosAdapter(this, itemsList) // Pasar el contexto aquí
        recyclerView.adapter = adapter

        // Verificar si se pasaron items de otra Activity
        val intent = intent
        if (intent.hasExtra("itemList")) {
            val itemListIntent: List<Proceso>? = intent.getParcelableArrayListExtra("itemList")
            itemsList.clear()
            itemsList.addAll(itemListIntent ?: emptyList())
            adapter.notifyDataSetChanged()
        }

        // Configurar ItemTouchHelper para mover elementos
        val itemTouchHelper = ItemTouchHelper(ItemDragHelperCallback(adapter, itemsList))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Configurar listeners para los botones
        btnVideoTutorialRobin.setOnClickListener {
            val dialogHelper = DialogHelperVideoTutorial(this)
            dialogHelper.showDialog()
        }

        creadorRobin.setOnClickListener {
            val popupHelper = PopupHelperCreador(this)
            popupHelper.showPopup(creadorRobin)
        }

        btnCrearRobin.setOnClickListener {
            val popupHelper = PopupHelperCrearRobin(this)
            popupHelper.show(btnCrearRobin, itemsList)
        }

        // Botón para ordenar y mostrar el orden actual
        btnOrdenar.setOnClickListener {
            ordenarListaPorTiempoDeLlegada()

        }
        btnRecargar.setOnClickListener{
            val intent = Intent(this, PantallaDeCarga::class.java).apply {
                putExtra("proximaPagina" , "mainVacio")
            }
            startActivity(intent)
        }
        btnPreguntaInformacion.setOnClickListener{
            val popupHelper = PopupHelperPreguntaInformacion(this)
            popupHelper.show(btnPreguntaInformacion, itemsList)
        }
        btnPasosRobin.setOnClickListener{
            val dialogHelper = DialogHelperExplicacionParte1(this)
            dialogHelper.showDialog()
        }
        btnListo.setOnClickListener{
            ordenarListaPorTiempoDeLlegada()
            val quantum = txtQuantum.text.toString().toIntOrNull() ?: 0

            val intent = Intent(this, PantallaDeCarga::class.java).apply {
                putExtra("itemList", ArrayList(itemsList))
                putExtra("proximaPagina", "ejecutar")
                putExtra("quantum", quantum.toString())
            }

            startActivity(intent)
        }
        btnSumarQuantum.setOnClickListener {
            val quantumActual = txtQuantum.text.toString().toIntOrNull() ?: 0
            val nuevoQuantum = quantumActual + 1
            txtQuantum.text = nuevoQuantum.toString()
        }
        btnRestarQuantum.setOnClickListener {
            val quantumActual = txtQuantum.text.toString().toIntOrNull() ?: 0
            val nuevoQuantum = quantumActual - 1
            if (nuevoQuantum >= 0) {
                txtQuantum.text = nuevoQuantum.toString()
            }
        }
    }

    // Función para ordenar la lista por orden de llegada
    fun ordenarListaPorTiempoDeLlegada() {
        // Ordenar la lista por el campo "tiempoLlegada"
        itemsList.sortBy { it.getTiempoLlegada() }
        adapter.notifyDataSetChanged() // Actualizar el adaptador
    }

    // Función para mostrar el orden actual
    fun mostrarOrdenActual() {
        val currentOrder = adapter.getCurrentOrder()

        // Mostrar cada proceso con un Toast
        for (proceso in currentOrder) {
            Toast.makeText(
                this,
                "${proceso.getNombre()} (Ráfaga: ${proceso.getRafaga()}, Llegada: ${proceso.getTiempoLlegada()})",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onBackPressed() {

    }




}
