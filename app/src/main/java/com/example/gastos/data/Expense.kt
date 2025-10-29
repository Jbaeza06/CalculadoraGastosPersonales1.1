package com.example.gastos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa un gasto individual en la base de datos.
 */
@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val amount: Double,
    val date: String
)
