package popups_y_adicionales

import DialogHelperExplicacionParte5
import DialogHelperVideoTutorial
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.parcialroundrobin.R
import objetos.Proceso

class DialogHelperResultados(private val context: Context) {

    fun showDialog(lis: List<Proceso>) {
        // Crear un nuevo diálogo
        val dialog = Dialog(context)

        // Inflar el diseño del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_resultados, null)

        // Configurar el diálogo
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // No mostrar el título del diálogo
        dialog.setContentView(dialogView) // Establecer el contenido del diálogo
        dialog.setCancelable(false) // Evita que el diálogo se cierre al tocar fuera de él
        dialog.setCanceledOnTouchOutside(false) // Asegura que no se cierre al tocar fuera

        // Configurar el tamaño del diálogo
        dialog.window?.setLayout(
            1100,
            1000
        )

        // Inicializar el TableLayout
        val tableLayout: TableLayout = dialogView.findViewById(R.id.tableLayout)
        val btnPregunta: ImageButton = dialogView.findViewById(R.id.btnPregunta)
        // Inicializar el botón y configurar su click listener
        val btnListo: Button = dialogView.findViewById(R.id.btnListo)
            var rt = 0
            var wt = 0
            var tat = 0
            var rtPromedio = 0.0
            var wtPromedio = 0.0
            var tatPromedio = 0.0
            var tSalidaPromedio = 0.0

       for (proceso in lis) {
            rt =  proceso.getTiempoPrimeraEjecucion() - proceso.getTiempoLlegada()
            tat = proceso.getTiempoSalida() - proceso.getTiempoLlegada()
            wt =  proceso.getTiempoSalida() - proceso.getAuxiliarRafaga()
            rtPromedio += rt
            wtPromedio += wt
            tatPromedio += tat
            tSalidaPromedio += proceso.getTiempoSalida()
            agregarTupla(tableLayout, proceso.getNombre() , rt.toString(), wt.toString(), tat.toString(), proceso.getTiempoSalida().toString())
        }
        rtPromedio = rtPromedio / lis.size
        wtPromedio = wtPromedio / lis.size
        tatPromedio = tatPromedio / lis.size
        tSalidaPromedio = tSalidaPromedio / lis.size
        agregarTupla(tableLayout, "Promedio" , rtPromedio.toString(), wtPromedio.toString(), tatPromedio.toString() , tSalidaPromedio.toString())


        btnListo.setOnClickListener {

          dialog.dismiss()

       }
   btnPregunta.setOnClickListener {
         val dialoghelper = DialogHelperExplicacionParte5(context)
            dialoghelper.showDialog(false)
        }


        dialog.show()
    }

    // Función para agregar una fila a la tabla
    private fun agregarTupla(tableLayout: TableLayout, proceso: String, rt: String, wt: String, tat: String , tSalida: String) {
        // Crear una nueva fila
        val newRow = TableRow(context)

        // Crear los TextViews para cada celda
        val tvProceso = TextView(context).apply {
            text = proceso
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = android.view.Gravity.CENTER
            setPadding(10, 10, 10, 10) // Establecer el padding usando setPadding
        }

        val tvRT = TextView(context).apply {
            text = rt
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = android.view.Gravity.CENTER
            setPadding(10, 10, 10, 10) // Establecer el padding usando setPadding
        }

        val tvWT = TextView(context).apply {
            text = wt
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = android.view.Gravity.CENTER
            setPadding(10, 10, 10, 10) // Establecer el padding usando setPadding
        }

        val tvTAT = TextView(context).apply {
            text = tat
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = android.view.Gravity.CENTER
            setPadding(10, 10, 10, 10) // Establecer el padding usando setPadding
        }

        val tvSalida = TextView(context).apply {
            text = tSalida
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            gravity = android.view.Gravity.CENTER
            setPadding(10, 10, 10, 10) // Establecer el padding usando setPadding
        }


        // Agregar los TextViews a la fila
        newRow.addView(tvProceso)
        newRow.addView(tvRT)
        newRow.addView(tvWT)
        newRow.addView(tvTAT)
        newRow.addView(tvSalida)

        // Agregar la fila a la tabla
        tableLayout.addView(newRow)
    }
}
