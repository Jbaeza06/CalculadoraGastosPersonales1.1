package com.example.gastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions // ← Mantén este import (para Compose viejo)
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gastos.data.Expense
import com.example.gastos.ui.theme.CalculadoraGastosPersonalesTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val vm: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraGastosPersonalesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ExpenseApp(vm)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseApp(vm: ExpenseViewModel = viewModel()) {
    val expenses by vm.expenses.collectAsState()
    val total by vm.total.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Gastos") },
                actions = {
                    if (expenses.isNotEmpty()) {
                        TextButton(onClick = { vm.clearAll() }) { Text("Limpiar") }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) { Text("+") }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TotalCard(total)
            Spacer(Modifier.height(8.dp))
            if (expenses.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no hay gastos. Toca + para agregar.")
                }
            } else {
                ExpenseList(
                    expenses = expenses.sortedByDescending { it.date },
                    onDelete = vm::remove
                )
            }
        }

        if (showDialog) {
            AddExpenseDialog(
                onDismiss = { showDialog = false },
                onSave = { d, a, dt ->
                    vm.add(d, a, dt)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun TotalCard(total: Double) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total gastado")
            Text(formatCOP(total), style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun ExpenseList(expenses: List<Expense>, onDelete: (Expense) -> Unit) {
    LazyColumn {
        items(expenses, key = { it.id }) { e ->
            ExpenseItem(e, onDelete)
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun ExpenseItem(e: Expense, onDelete: (Expense) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(e.description, style = MaterialTheme.typography.titleMedium)
                    if (e.date.isNotBlank()) Text(e.date, color = MaterialTheme.colorScheme.outline)
                }
                Text(formatCOP(e.amount), style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { onDelete(e) }) { Text("Eliminar") }
            }
        }
    }
}

@Composable
fun AddExpenseDialog(onDismiss: () -> Unit, onSave: (String, Double, String) -> Unit) {
    var description by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val focus = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar gasto") },
        text = {
            Column {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Monto (COP)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Fecha (YYYY-MM-DD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val amount = amountText.replace(",", "").toDoubleOrNull() ?: -1.0
                onSave(description, amount, date)
                focus.clearFocus()
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

private fun formatCOP(value: Double): String =
    NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
        maximumFractionDigits = 0
        currency = java.util.Currency.getInstance("COP")
    }.format(value)
