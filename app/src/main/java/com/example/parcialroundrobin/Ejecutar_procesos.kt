package com.example.parcialroundrobin

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import objetos.Proceso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import popups_y_adicionales.DialogHelperResultados
import kotlin.math.log

class Ejecutar_procesos : AppCompatActivity(), CoroutineScope {

    // Define el scope de la coroutine
    override val coroutineContext = Dispatchers.Main + Job()

    private lateinit var tableLayout: TableLayout
    private var count = 1 // Contador para mostrar el número de tuplas
    private var listaAuxiliarTodosLosProcesos = mutableListOf<Proceso>()
    private var listaEspera = mutableListOf<Proceso>()
    private var listaFinalizados = mutableListOf<Proceso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ejecutar_procesos)

        // Inicializa el TableLayout y el botón
        tableLayout = findViewById(R.id.tableLayout)
        val btnListo: Button = findViewById(R.id.btnListo)
        val btnCasa: ImageView = findViewById(R.id.btnCasa)
        val quantum = intent.getStringExtra("quantum").toString().toInt()
        val txtValorCuantum: TextView = findViewById(R.id.txtValorCuantum)
        val txtOrdenProcesos: TextView = findViewById(R.id.txtOrdenEjecucion)
        var ordenProcesos = ""

        txtValorCuantum.text = quantum.toString()

        val intent = intent
        if (intent.hasExtra("itemList")) {
            val itemListIntent: List<Proceso>? = intent.getParcelableArrayListExtra("itemList")
            listaAuxiliarTodosLosProcesos.addAll(itemListIntent ?: emptyList())

        }

        for(i in 0 until listaAuxiliarTodosLosProcesos.size){
            listaAuxiliarTodosLosProcesos[i].setAuxiliarRafaga(listaAuxiliarTodosLosProcesos[i].getRafaga())
        }

        var rafagaTotal = obtenerRafagaTotal(listaAuxiliarTodosLosProcesos)
        var contador = -1
        var auxiliarProcesos = Proceso()
        btnListo.visibility = View.INVISIBLE
        btnListo.isClickable = false
        btnCasa.visibility = View.INVISIBLE
        btnCasa.isClickable = false
        txtOrdenProcesos.visibility = View.INVISIBLE

        launch {
            while ((obtenerRafagaTotal(listaEspera) > 0) || (listaAuxiliarTodosLosProcesos.isNotEmpty())) {

                var i = 0
                while (i < quantum) {
                    var color = "#000000"
                    auxiliarProcesos = Proceso()
                    contador += 1
                    // Verifica si hay procesos en listaAuxiliar
                    if (listaAuxiliarTodosLosProcesos.isNotEmpty()) {
                        var j = 0
                        while (j < listaAuxiliarTodosLosProcesos.size) {
                            if (contador == listaAuxiliarTodosLosProcesos[j].getTiempoLlegada()) {
                                listaEspera.add(listaAuxiliarTodosLosProcesos[j])
                                listaAuxiliarTodosLosProcesos.removeAt(j)
                            } else {
                                j++
                            }
                        }
                    }

                    for(k in 0 until listaEspera.size){
                        if(listaEspera[k].getAcabaDeEjecutarse()){
                            val auxiliar = listaEspera[k]
                            auxiliar.setAcabaDeEjecutarse(false)
                            listaEspera.removeAt(k)
                            listaEspera.add(auxiliar)
                        }
                    }


                    // Verifica si hay procesos en listaEspera
                    if (listaEspera.isNotEmpty()) {
                        auxiliarProcesos = listaEspera[0]
                        auxiliarProcesos.setEstaEjecutandose(true)

                        if (auxiliarProcesos.getEstado() == 0) {
                            auxiliarProcesos.setEstado(1)
                            auxiliarProcesos.setTiempoPrimeraEjecucion(contador)
                            color = "#800080"
                        }
                        auxiliarProcesos.setRafaga(auxiliarProcesos.getRafaga() - 1)
                        auxiliarProcesos.setQuantumCounsumido(auxiliarProcesos.getQuantumCounsumido() + 1)

                        //reiniciar contador si no se alcanza a consumir todo el quantun
                        val quantumAux = quantum - 1
                        val auxiliarQuantumConsumido = auxiliarProcesos.getQuantumCounsumido()

                        println("Auxiliar quantum consumido " + auxiliarQuantumConsumido)
                        println("Quantum " + quantum)
                        println("quantum aux " + quantumAux)
                        if ((auxiliarQuantumConsumido < quantum) && (i == quantumAux)) {
                            i = i - 1
                        }

                        if (auxiliarProcesos.getRafaga() == 0) {
                            auxiliarProcesos.setEstado(2)
                            auxiliarProcesos.setTiempoSalida(contador)
                            color ="#FF0000"
                        }
                    }

                    // Actualiza la lista de espera
                    if (listaEspera.isNotEmpty()) {
                        listaEspera.removeAt(0)
                        listaEspera.add(0, auxiliarProcesos)
                        if (auxiliarProcesos.getEstado() == 2) {
                            listaFinalizados.add(auxiliarProcesos)
                        }
                    }

                    // Mostrar la información
                    val enListaDeEspera = toStringProcesosEnEspera(listaEspera)
                    val enEjecucion = toStringProcesoEnEjecucion(listaEspera)
                    val listaTerminados = toStringProcesosTerminados(listaFinalizados)




                    // Llama a agregarTupla con delay
                    agregarTupla(contador.toString(), enListaDeEspera, enEjecucion, listaTerminados , color)

                    if (listaEspera.isNotEmpty() && listaEspera[0].getEstado() == 2) {
                        listaEspera.removeAt(0)
                        break
                    }
                    i++
                    delay(200)
                }
                if(auxiliarProcesos.getNombre() != ""){
                    ordenProcesos += "   "+auxiliarProcesos.getNombre() + "   -"
                }

                // Si hay procesos en espera, actualiza el estado de ejecución
                if (listaEspera.isNotEmpty() && (listaEspera[0] == auxiliarProcesos)) {
                    auxiliarProcesos.setEstaEjecutandose(false)
                    auxiliarProcesos.setAcabaDeEjecutarse(true)
                    auxiliarProcesos.setQuantumCounsumido(0)
                    listaEspera.removeAt(0)
                    listaEspera.add(auxiliarProcesos)
                }
            }
            val handler = android.os.Handler()
            handler.postDelayed({
                btnListo.visibility = View.VISIBLE
                btnListo.isClickable = true
                txtOrdenProcesos.visibility = View.VISIBLE
                ordenProcesos = ordenProcesos.dropLast(1)
                ordenProcesos = "Orden real de ejecucion \n" + ordenProcesos
                txtOrdenProcesos.text = ordenProcesos
            }, contador.toLong() * 50)

        }



        // Configura el listener para el botón
        btnListo.setOnClickListener {
            txtValorCuantum.visibility = View.INVISIBLE
            btnCasa.visibility = View.VISIBLE
            btnCasa.isClickable = true
            val DialogHelper = DialogHelperResultados(this)
            DialogHelper.showDialog(listaFinalizados)

        }
        btnCasa.setOnClickListener {
            val intent = Intent(this, PantallaDeCarga::class.java).apply {
                putExtra("proximaPagina" , "mainVacio")
            }
            startActivity(intent)

        }

        // Establece el padding para el sistema de barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun agregarTupla(contador: String, enListaDeEspera: String, enEjecucion: String, listaTerminados: String , color : String) {
        // Lanza una nueva coroutine
        launch {
            // Introduce un retraso de 200 milisegundos
            delay(500)

            val tableRow = TableRow(this@Ejecutar_procesos)

            val textView1 = crearTextView(contador , "#000000")
            val textView2 = crearTextView(enListaDeEspera , "#000000")
            val textView3 = crearTextView(enEjecucion , color)
            val textView4 = crearTextView(listaTerminados , "#000000")

            tableRow.addView(textView1)
            tableRow.addView(textView2)
            tableRow.addView(textView3)
            tableRow.addView(textView4)

            tableLayout.addView(tableRow)

            count++
            Log.d("EjecutarProcesos", "Fila agregada: $count")
        }
    }

    private fun crearTextView(text: String , color : String): TextView {
        return TextView(this).apply {
            this.text = text
            this.gravity = View.TEXT_ALIGNMENT_CENTER
            this.setPadding(10, 10, 10, 10)
            this.setBackgroundResource(R.drawable.celda_estilo)
            this.textSize = 16f
            if (color != "#000000"){
                this.setBackground(getDrawable(R.drawable.rounded_celda_magnificada))
                this.setTypeface(null, Typeface.BOLD)
            }
            this.setTextColor(Color.parseColor(color))

        }
    }

    fun toStringProcesosEnEspera(lis: List<Proceso>): String {
        if (lis.isEmpty()) {
            return ""
        }
        var dato = ""
        for (datos in lis) {
            if (!datos.getEstaEjecutandose()) {
                dato = " " + datos.getNombre() + "," + datos.getRafaga() + " -" + dato
            }
        }
        return dato.dropLast(1)
    }

    fun toStringProcesosTerminados(lis: List<Proceso>): String {
        if (lis.isEmpty()) {
            return ""
        }
        var dato = ""
        for (datos in lis) {
            dato += " " + datos.getNombre() + " -"
        }
        return dato.dropLast(1)
    }

    fun toStringProcesoEnEjecucion(lis: List<Proceso>): String {
        if (lis.isEmpty()) {
            return ""
        }
        var dato = ""
        for (datos in lis) {
            if (datos.getEstaEjecutandose()) {
                dato += datos.getNombre() + "," + datos.getRafaga()
            }
        }
        return dato
    }

    fun obtenerRafagaTotal(lis: List<Proceso>): Int {
        var rafagaTotal = 0
        for (proceso in lis) {
            rafagaTotal += proceso.getRafaga()
        }
        return rafagaTotal
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel() // Cancela las coroutines cuando la actividad se destruye
    }
}
