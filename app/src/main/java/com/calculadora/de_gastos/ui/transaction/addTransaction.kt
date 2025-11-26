package com.calculadora.de_gastos.ui.transaction

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.calculadora.de_gastos.R
import com.calculadora.de_gastos.data.AppDB
import com.calculadora.de_gastos.data.Movimiento
import com.calculadora.de_gastos.data.MovimientoRepository
import com.calculadora.de_gastos.viewmodel.MovimientoViewModel
import com.calculadora.de_gastos.viewmodel.MovimientoViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.appbar.MaterialToolbar

class addTransaction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_transaction)
        val viewModel: MovimientoViewModel

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val btnregistrarOpcion = findViewById<MaterialButtonToggleGroup>(R.id.registrarOpcion)
        val cdodescripcionT = findViewById<TextInputEditText>(R.id.descripcionT)
        val cdoValorT = findViewById<TextInputEditText>(R.id.valorT)
        val btnGuardar = findViewById<MaterialButton>(R.id.guardarT)

        val database = AppDB.getDatabase(this)
        val repository = MovimientoRepository(database.movimientoDao())
        val factory = MovimientoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MovimientoViewModel::class.java]

        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnGuardar.setOnClickListener {
            val descripcion = cdodescripcionT.text.toString()
            val cantidadTexto = cdoValorT.text.toString()
            val tipoSeleccionado = btnregistrarOpcion.checkedButtonId

            if (descripcion.isEmpty()) {
                Toast.makeText(this , "⚠️ Ingresa una descripción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cantidadTexto.isEmpty()) {
                Toast.makeText(this, "⚠️ Ingresa una cantidad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tipoSeleccionado == -1) {
                Toast.makeText(this, "⚠️ Selecciona Ingreso o Egreso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cantidad = cantidadTexto.replace(".", "").replace(",", ".").toDoubleOrNull()

            if (cantidad == null || cantidad <= 0) {
                Toast.makeText(this, "⚠️ Cantidad inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipo = if (tipoSeleccionado == R.id.ingreso) 1 else 0

            val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val nuevoMovimiento = Movimiento(
                descripcion = descripcion,
                cantidad = cantidad,
                tipo = tipo,
                fecha = fechaActual,
                categoria = ""
            )

            viewModel.insertar(nuevoMovimiento)

            Toast.makeText(
                this,
                "✅ Transacción guardada",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}