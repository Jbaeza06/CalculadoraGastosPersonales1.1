package com.calculadora.de_gastos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.calculadora.de_gastos.data.Movimiento
import com.calculadora.de_gastos.data.MovimientoRepository
import kotlinx.coroutines.launch

class MovimientoViewModel(private val repository: MovimientoRepository) : ViewModel(){
    //funcion de livedata
    val movimientos = repository.obtenerTodos().asLiveData()

    //funcion para insertar
    fun insertar(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.insertar(movimiento)
            }
        }

    fun actualizar(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.actualizar(movimiento)
        }
    }
    fun eliminar(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.eliminar(movimiento)
        }
    }
}
