package com.calculadora.de_gastos.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.calculadora.de_gastos.data.AppDB
import com.calculadora.de_gastos.data.Recordatorio
import com.calculadora.de_gastos.data.RecordatorioDao
import kotlinx.coroutines.launch

class InicioViewModel(application: Application) : AndroidViewModel(application) {

    private val recordatorioDao: RecordatorioDao
    val todosLosRecordatorios: LiveData<List<Recordatorio>>

    init {

        val database = AppDB.getDatabase(application)
        recordatorioDao = database.recordatorioDao()

        todosLosRecordatorios = recordatorioDao.obtenerTodos()
    }


    fun insertarRecordatorio(recordatorio: Recordatorio) {

        viewModelScope.launch {
            recordatorioDao.insertar(recordatorio)
        }
    }


    fun eliminarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            recordatorioDao.eliminar(recordatorio)
        }
    }
}