package popups_y_adicionales

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parcialroundrobin.R
import objetos.Proceso

class ProcesosAdapter(
    private val context: Context,
    private val items: MutableList<Proceso>
) : RecyclerView.Adapter<ProcesosAdapter.ProcesosViewHolder>() {

    class ProcesosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreProceso: TextView = itemView.findViewById(R.id.textView2)
        val rafagaProceso: TextView = itemView.findViewById(R.id.textView3)
        val ordenLLegada: TextView = itemView.findViewById(R.id.txtOrdenLLegada)
        val btnEliminar: ImageView = itemView.findViewById(R.id.imageView) // Usamos el ImageView como botón de eliminar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcesosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_procesos, parent, false)
        return ProcesosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProcesosViewHolder, position: Int) {
        val proceso = items[position]
        holder.nombreProceso.text = proceso.getNombre()
        holder.rafagaProceso.text = proceso.getRafaga().toString()
        holder.ordenLLegada.text = proceso.getTiempoLlegada().toString() // Reflejar el tiempo actualizado

        // Configurar el listener para el botón de eliminar
        holder.btnEliminar.setOnClickListener {
            showConfirmationDialog(position)
        }
    }

    override fun getItemCount() = items.size

    private fun showConfirmationDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Eliminar ítem")
        builder.setMessage("¿Estás seguro de que deseas eliminar este ítem?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            removeItem(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size) // Actualizar los elementos restantes
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Intercambiar elementos en la lista
        val movedItem = items.removeAt(fromPosition)
        items.add(toPosition, movedItem)

        // Notificar que se ha movido el ítem
        notifyItemMoved(fromPosition, toPosition)

        // Notificar que se ha cambiado el ítem en ambas posiciones
        notifyItemChanged(fromPosition)
        notifyItemChanged(toPosition)
    }

    // Método para obtener la lista actual en su orden
    fun getCurrentOrder(): List<Proceso> {
        return items.mapIndexed { index, proceso ->
            proceso.copy(orden = index + 1) // Actualizar el orden
        }
    }
}
