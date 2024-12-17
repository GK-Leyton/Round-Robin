package popups_y_adicionales

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Toast
import com.example.parcialroundrobin.PantallaDeCarga
import com.example.parcialroundrobin.R
import objetos.Proceso

class PopupHelperCrearRobin(private val context: Context) {

    fun show(anchorView: View, listaRecibida: MutableList<Proceso>) {
        // Infla el layout del popup desde el archivo XML
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val popupView: View = inflater.inflate(R.layout.popup_creacion_proceso, null)

        // Crear el PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // Hacer el PopupWindow enfocable y desechable al tocar fuera de él
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(null) // Establecer fondo transparente

        // Configurar interacción con los botones del popup
        val buttonCrear = popupView.findViewById<Button>(R.id.btnCrearRobin)
        val buttonCancelar = popupView.findViewById<Button>(R.id.btnCancelarCrearRobin)
        val nombreProceso = popupView.findViewById<EditText>(R.id.txtNombreProceso)
        val rafagaProceso = popupView.findViewById<EditText>(R.id.txtRafagaProceso)
        val closeButton = popupView.findViewById<ImageButton>(R.id.imageButton2)
        val tiempoLlegada = popupView.findViewById<EditText>(R.id.txtTiempoLLegada)

        // Función para habilitar/deshabilitar el botón Crear
        fun validarCampos() {
            val nombre = nombreProceso.text.toString()
            val rafaga = rafagaProceso.text.toString()
            val llegada = tiempoLlegada.text.toString()

            buttonCrear.isEnabled = nombre.length in 2..9 && rafaga.length in 1..2 && llegada.length in 1..2 && !rafaga.equals("0")
        }

        // Agregar TextWatcher a nombreProceso
        nombreProceso.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validarCampos() // Verificar los campos al cambiar el texto
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Agregar TextWatcher a rafagaProceso
        rafagaProceso.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validarCampos() // Verificar los campos al cambiar el texto
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        tiempoLlegada.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validarCampos() // Verificar los campos al cambiar el texto
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Deshabilitar el botón Crear inicialmente
        buttonCrear.isEnabled = false

        // Lógica de los botones
        buttonCrear.setOnClickListener {
            val nombre = nombreProceso.text.toString()
            val rafaga = rafagaProceso.text.toString().toInt()
            val instanteLlegada = tiempoLlegada.text.toString().toInt()
            var band = true;

            for(i in listaRecibida){
                if(i.getNombre().equals(nombre)){
                    Toast.makeText(context,"Ya existe un proceso con ese nombre", Toast.LENGTH_SHORT).show()
                    band = false;
                    break;
                }
            }
            // Crear nuevo proceso sin necesidad de orden

            if(band){
                val proceso = Proceso(
                    nombre,
                    rafaga,
                    instanteLlegada,
                    0 // Este valor puede ser cualquier cosa, o simplemente 0 si no lo necesitas
                )

                proceso.setAuxiliarRafaga(rafaga)


                listaRecibida.add(proceso) // Agregar al itemList

                // Ordenar la lista por tiempoLlegada
                listaRecibida.sortBy { it.getTiempoLlegada()}

                // Crear el intent y pasar la lista ordenada
                val intent = Intent(context, PantallaDeCarga::class.java).apply {
                    putExtra("itemList", ArrayList(listaRecibida))
                    putExtra("proximaPagina", "main")
                }


                context.startActivity(intent)
            }



            //popupWindow.dismiss()
        }

        buttonCancelar.setOnClickListener {
            popupWindow.dismiss() // Cerrar sin acción
        }

        closeButton.setOnClickListener {
            popupWindow.dismiss() // Cerrar el popup
        }

        // Crear y mostrar el fondo semitransparente
        val activity = context as? AppCompatActivity
        val decorView = activity?.window?.decorView as? ViewGroup

        if (decorView != null) {
            // Crear un fondo semitransparente
            val backgroundOverlay = View(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.argb(250, 255, 255, 255)) // Color semitransparente (blanco)

                visibility = View.VISIBLE
            }

            // Añadir el fondo al decorView
            decorView.addView(backgroundOverlay)

            // Mostrar el PopupWindow centrado
            val xOffset = 0  // Desplazamiento horizontal
            val yOffset = -130  // Desplazamiento vertical
            popupWindow.showAtLocation(anchorView, Gravity.CENTER, xOffset, yOffset)

            // Configurar el listener para cerrar el PopupWindow y el fondo
            popupWindow.setOnDismissListener {
                backgroundOverlay.visibility = View.GONE
                decorView.removeView(backgroundOverlay)
            }

            // Opcional: Cerrar el PopupWindow si se toca el fondo
            backgroundOverlay.setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }
}
