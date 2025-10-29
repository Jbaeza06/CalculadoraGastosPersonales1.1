package com.example.gastos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastos.data.AppDatabase
import com.example.gastos.data.Expense
import com.example.gastos.data.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    // Repositorio (inyectado manualmente desde la DB singleton)
    private val repo: ExpenseRepository by lazy {
        val db = AppDatabase.Companion.getInstance(application)
        ExpenseRepository(db.expenseDao())
    }

    // Streams observables para la UI (Compose)
    val expenses: StateFlow<List<Expense>> =
        repo.expenses.stateIn(viewModelScope, SharingStarted.Companion.Eagerly, emptyList())

    val total: StateFlow<Double> =
        repo.total.stateIn(viewModelScope, SharingStarted.Companion.Eagerly, 0.0)

    // Acciones
    fun add(description: String, amount: Double, date: String) {
        if (description.isBlank() || amount <= 0.0) return
        viewModelScope.launch {
            repo.add(
                Expense(
                    id = 0,
                    description = description.trim(),
                    amount = amount,
                    date = date
                )
            )
        }
    }

    fun remove(expense: Expense) {
        viewModelScope.launch { repo.remove(expense) }
    }

    fun clearAll() {
        viewModelScope.launch { repo.clear() }
    }
}