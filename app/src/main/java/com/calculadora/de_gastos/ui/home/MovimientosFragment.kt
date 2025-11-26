package com.calculadora.de_gastos.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calculadora.de_gastos.R
import com.calculadora.de_gastos.data.AppDB
import com.calculadora.de_gastos.data.Movimiento
import com.calculadora.de_gastos.data.MovimientoRepository
import com.calculadora.de_gastos.ui.transaction.addTransaction
import com.calculadora.de_gastos.viewmodel.MovimientoViewModel
import com.calculadora.de_gastos.viewmodel.MovimientoViewModelFactory

class MovimientosFragment : Fragment(R.layout.fragment_movimientos) {

    private lateinit var viewModel: MovimientoViewModel
    private lateinit var adapter: MovimientosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDB.getDatabase(requireContext())
        val buttonT = view.findViewById<Button>(R.id.button2)

        val repository = MovimientoRepository(database.movimientoDao())

        val factory = MovimientoViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[MovimientoViewModel::class.java]

        buttonT.setOnClickListener {
            val intent =
                Intent(requireContext(), addTransaction::class.java)
            startActivity(intent)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerMovimientos)
        val tvContador = view.findViewById<TextView>(R.id.tvContador)

        adapter = MovimientosAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnItemLongClickListener { movimiento ->
            mostrarMenuOpciones(movimiento)
        }

        viewModel.movimientos.observe(viewLifecycleOwner) { lista ->
            adapter.setData(lista)

            tvContador.text = "Total: ${lista.size} movimientos"

            println("📊 Movimientos cargados: ${lista.size}")
            lista.forEach { mov ->
                println("  - ${mov.descripcion}: $${mov.cantidad}")
            }
        }
    }
    private fun mostrarMenuOpciones(movimiento: Movimiento) {
        val opciones = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(requireContext())
            .setTitle("Acciones para \"${movimiento.descripcion}\"")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> editarMovimiento(movimiento)
                    1 -> eliminarMovimiento(movimiento)
                }
            }
            .show()
    }

    private fun editarMovimiento(movimiento: Movimiento) {
        val bundle = Bundle().apply {
            putParcelable("movimiento", movimiento)
        }
        findNavController().navigate(
            R.id.eTransactionFragment,
            bundle
        )
    }

    private fun eliminarMovimiento(movimiento: Movimiento) {
        viewModel.eliminar(movimiento)
        Toast.makeText(requireContext(), "Movimiento eliminado", Toast.LENGTH_SHORT).show()
    }
}