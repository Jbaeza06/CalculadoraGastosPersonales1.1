package com.calculadora.de_gastos.data
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Aquí va la entidad (la tabla)
@Parcelize
@Entity(tableName = "movimientos")
data class Movimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descripcion: String,
    val cantidad: Double,
    val tipo: Int,       // 0 = gasto, 1 = ingreso
    val fecha: String,
    val categoria: String
): Parcelable
