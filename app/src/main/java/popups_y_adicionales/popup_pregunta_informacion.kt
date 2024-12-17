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
import com.example.parcialroundrobin.PantallaDeCarga
import com.example.parcialroundrobin.R
import objetos.Proceso

class PopupHelperPreguntaInformacion(private val context: Context) {

    fun show(anchorView: View, listaRecibida: MutableList<Proceso>) {
        // Infla el layout del popup desde el archivo XML
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val popupView: View = inflater.inflate(R.layout.popup_pregunta_informacion, null)

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
        val closeButton = popupView.findViewById<ImageButton>(R.id.imageButton2)

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
