package objetos

import android.os.Parcel
import android.os.Parcelable

data class Proceso(
   private var nombre: String = "",
   private var rafaga: Int = 0,
   private var tiempoLlegada: Int = 0,
   private var orden: Int = 0,
   private var tiempoSalida: Int = 0,
   private var tiempoPrimeraEjecucion: Int = 0,
   private var estaejecutandose: Boolean = false,
   private var estado: Int = 0, //0 nunca ejecutado , 1 ejecutado , 2 terminado
   private var quantumCounsumido : Int = 0,
   private var auxiliarRafaga: Int = 0,
   private var acabaDeEjecutarse: Boolean = false
) : Parcelable {
    // Constructor para leer desde un Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",   // Leer nombre
        parcel.readInt(),            // Leer ráfaga
        parcel.readInt(),            // Leer tiempo de llegada
        parcel.readInt()             // Leer orden

    )

    // Escribir los valores en un Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)   // Escribir nombre
        parcel.writeInt(rafaga)      // Escribir ráfaga
        parcel.writeInt(tiempoLlegada) // Escribir tiempo de llegada
        parcel.writeInt(orden)       // Escribir orden
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Proceso> {
        override fun createFromParcel(parcel: Parcel): Proceso {
            return Proceso(parcel)
        }

        override fun newArray(size: Int): Array<Proceso?> {
            return arrayOfNulls(size)
        }
    }




    fun setTiempoSalida(tiempo: Int) {
        tiempoSalida = tiempo
    }
    fun getTiempoSalida(): Int {
        return tiempoSalida
    }
    fun setTiempoPrimeraEjecucion(tiempo: Int) {
        tiempoPrimeraEjecucion = tiempo
    }
    fun getTiempoPrimeraEjecucion(): Int {
        return tiempoPrimeraEjecucion
    }
    fun setEstado(estado: Int) {
        this.estado = estado
    }
    fun getEstado(): Int {
        return estado
    }
    fun setOrden(orden: Int) {
        this.orden = orden
    }
    fun getOrden(): Int {
        return orden
    }
    fun setNombre(nombre: String) {
        this.nombre = nombre
    }
    fun getNombre(): String {
        return nombre
    }
    fun setRafaga(rafaga: Int) {
        this.rafaga = rafaga
    }
    fun getRafaga(): Int {
        return rafaga
    }
    fun setTiempoLlegada(tiempoLlegada: Int) {
        this.tiempoLlegada = tiempoLlegada
    }
    fun getTiempoLlegada(): Int {
        return tiempoLlegada
    }
    fun setEstaEjecutandose(estaEjecutandose: Boolean) {
        this.estaejecutandose = estaEjecutandose
    }
    fun getEstaEjecutandose(): Boolean {
        return estaejecutandose
    }
    fun setQuantumCounsumido(quantumCounsumido: Int) {
        this.quantumCounsumido = quantumCounsumido
    }
    fun getQuantumCounsumido(): Int {
        return quantumCounsumido
    }fun setAuxiliarRafaga(auxiliarRafaga: Int) {
        this.auxiliarRafaga = auxiliarRafaga
    }
    fun getAuxiliarRafaga(): Int {
        return auxiliarRafaga
    }
    fun setAcabaDeEjecutarse(valor : Boolean){
        acabaDeEjecutarse = valor
    }
    fun getAcabaDeEjecutarse(): Boolean{
        return acabaDeEjecutarse
    }


    }
