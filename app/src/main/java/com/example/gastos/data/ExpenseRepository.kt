package com.example.gastos.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepository(private val dao: ExpenseDao) {
    val expenses: Flow<List<Expense>> = dao.getAll()
    val total: Flow<Double> = dao.total().map { it ?: 0.0 }

    suspend fun add(expense: Expense) = dao.insert(expense)
    suspend fun remove(expense: Expense) = dao.delete(expense)
    suspend fun clear() = dao.clear()
}
