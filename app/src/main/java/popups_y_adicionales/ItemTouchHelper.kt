package popups_y_adicionales

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import objetos.Proceso

class ItemDragHelperCallback(
    private var adapter: ProcesosAdapter,
    private var itemsLista: MutableList<Proceso>
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN // Permitir arrastrar hacia arriba y abajo
        return makeMovementFlags(dragFlags, 0) // No permitir deslizar
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition

        // Asegúrate de que el movimiento solo sea válido si de verdad está cambiando de lugar
        if (fromPosition != toPosition) {
            // Intercambiar elementos en la lista
            val movedItem = itemsLista[fromPosition]
            itemsLista[fromPosition] = itemsLista[toPosition]
            itemsLista[toPosition] = movedItem

            // Intercambiar los valores de tiempoLlegada
            val tempTiempoLlegada = itemsLista[fromPosition].getTiempoLlegada()
            itemsLista[fromPosition].setTiempoLlegada (itemsLista[toPosition].getTiempoLlegada())
            itemsLista[toPosition].setTiempoLlegada( tempTiempoLlegada)

            // Notificar al adaptador
            adapter.onItemMove(fromPosition, toPosition)
        }

        return true
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // No hacer nada en este caso
    }
}
