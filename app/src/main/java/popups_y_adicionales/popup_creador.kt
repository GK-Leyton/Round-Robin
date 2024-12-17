import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.parcialroundrobin.R

class PopupHelperCreador(private val context: Context) {

    // Método para mostrar el popup
    fun showPopup(parentView: View) {
        // Obtener el LayoutInflater del contexto
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_creador, null)

        // Crear el PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT, // Ancho del PopupWindow
            ViewGroup.LayoutParams.MATCH_PARENT, // Altura del PopupWindow
            true // Focusable
        )

        // Configurar las propiedades del PopupWindow
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(null) // Para permitir clic fuera y cerrarlo


        val btnCerrar  = popupView.findViewById<ImageButton>(R.id.btnCerrar)
        val btnDonar = popupView.findViewById<Button>(R.id.btnDonar)
        val btnVolver = popupView.findViewById<Button>(R.id.btnVolver)

        btnCerrar.setOnClickListener {
            popupWindow.dismiss()
        }
        btnDonar.setOnClickListener {
            Toast.makeText(context, "Me encantaria.....", Toast.LENGTH_SHORT).show()
        }
        btnVolver.setOnClickListener {
            popupWindow.dismiss()
        }





        // Obtener la ubicación de la vista ancla en la pantalla
        val location = IntArray(2)
        parentView.getLocationOnScreen(location)

        // Define el desplazamiento vertical (ajústalo según tu necesidad)
        val yOffset = 0

        // Mostrar el PopupWindow en la ubicación especificada
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, location[0], location[1] + yOffset)
    }
}
