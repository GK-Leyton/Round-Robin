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

class DialogHelperVideoTutorial(private val context: Context) {

    // Método para mostrar el diálogo
    fun showDialog() {
        // Crear un nuevo diálogo
        val dialog = Dialog(context)

        // Infla el diseño del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_video_tutorial, null)

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
        val closeButton: ImageButton = dialogView.findViewById(R.id.imageButton2)
        closeButton.setOnClickListener {
            dialog.dismiss() // Cerrar el diálogo al presionar el botón
        }

        // WebView en el diálogo
        val webView: WebView = dialogView.findViewById(R.id.WVVideo)

        // Habilitar JavaScript
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient() // Evitar abrir el navegador externo

        // Cargar el video de YouTube
        val videoId = "ZNPFx-kVbjU" // ID del video
        webView.loadUrl("https://www.youtube.com/embed/$videoId")

        // Mostrar el diálogo
        dialog.show()
    }
}
