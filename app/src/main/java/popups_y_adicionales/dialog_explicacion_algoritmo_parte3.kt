import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import com.example.parcialroundrobin.R

class DialogHelperExplicacionParte3(private val context: Context) {

    // Método para mostrar el diálogo
    fun showDialog() {
        // Crear un nuevo diálogo
        val dialog = Dialog(context)

        // Infla el diseño del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.popup_explicacion_algoritmo_parte3, null)

        // Configurar el diálogo
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // No mostrar el título del diálogo
        dialog.setContentView(dialogView) // Establecer el contenido del diálogo
        dialog.setCancelable(false) // Evita que el diálogo se cierre al tocar fuera de él
        dialog.setCanceledOnTouchOutside(false) // Asegura que no se cierre al tocar fuera

        // Configura un fondo semitransparente blanco alrededor del diálogo
        dialog.window?.setBackgroundDrawableResource(android.R.color.white)

        // Configurar el tamaño del diálogo
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        // Efecto de fondo semitransparente
        dialog.window?.setDimAmount(0.5f) // 0 es completamente transparente, 1 es completamente opaco

        // Lógica del botón de cierre

        val btnAtras: ImageButton = dialogView.findViewById(R.id.btnAtras)
        val btnSiguiente: ImageButton = dialogView.findViewById(R.id.btnSiguiente)

        btnAtras.setOnClickListener {
            dialog.dismiss()
            val dialogHelper = DialogHelperExplicacionParte2(context)
            dialogHelper.showDialog()
        }

        btnSiguiente.setOnClickListener {
            dialog.dismiss()
            val dialogHelper = DialogHelperExplicacionParte4(context)
            dialogHelper.showDialog()
        }

        // WebView en el diálogo



        // Mostrar el diálogo
        dialog.show()
    }
}
