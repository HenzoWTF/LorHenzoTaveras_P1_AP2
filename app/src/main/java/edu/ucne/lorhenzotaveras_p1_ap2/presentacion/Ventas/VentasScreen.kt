package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun VentasScreen(
    viewModel: VentasViewModel = hiltViewModel(),
    Id: Int?,
    goVentasList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VentasScreenBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goVentasList = goVentasList,
        Id = Id ?: 0
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreenBody(
    uiState: VentasUiState,
    onEvent: (VentasUIEvent) -> Unit,
    goVentasList: () -> Unit,
    Id: Int
){
    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(VentasUIEvent.SelectedVentas(Id))

        if(uiState.success)
        {
            goVentasList()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ventas")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { goVentasList() }) {
                Icon(Icons.Filled.ArrowBack, "Atras")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    OutlinedTextField(
                        label = { Text("Cliente") },
                        value = uiState.Cliente ?: "",
                        onValueChange = {
                            onEvent(VentasUIEvent.ClienteChange(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        label = { Text("Galones") },
                        value = if (uiState.Galones == 0.0) "" else uiState.Galones.toString(),
                        onValueChange = {
                            val galones = it.toIntOrNull()
                            if (galones != null) {
                                onEvent(VentasUIEvent.GalonesChange(galones.toDouble()))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    OutlinedTextField(
                        label = { Text("Descuento por galón") },
                        value = if (uiState.DescuentoPorGalon == 0.0) "" else uiState.DescuentoPorGalon.toString(),
                        onValueChange = {
                            val descuentoPorGalon = it.toDoubleOrNull()
                            if (descuentoPorGalon != null) {
                                onEvent(VentasUIEvent.DescuentoPorGalonChange(descuentoPorGalon))
                                val totalDescuento = descuentoPorGalon * uiState.Galones
                                onEvent(VentasUIEvent.TotalDescuentoChange(totalDescuento))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    OutlinedTextField(
                        label = { Text("Precio") },
                        value = if (uiState.Precio == 0.0) "" else uiState.Precio.toString(),
                        onValueChange = {
                            val precio = it.toDoubleOrNull()
                            if (precio != null) {
                                onEvent(VentasUIEvent.PrecioGalonChange(precio))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    OutlinedTextField(
                        label = { Text("Total descontado") },
                        value = if (uiState.TotalDescontado == 0.toDouble()) "" else uiState.TotalDescontado.toString(),
                        onValueChange = {
                            val totalDescontado = it.toIntOrNull()
                            if (totalDescontado != null) {
                                onEvent(VentasUIEvent.TotalDescuentoChange(totalDescontado.toDouble()))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        label = { Text("Total") },
                        value = if (uiState.Total == 0.0) "" else uiState.Total.toString(),
                        onValueChange = {
                            val total = it.toDouble()
                            if (total != null) {
                                onEvent(VentasUIEvent.TotalChange(total.toDouble()))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    uiState.errorMessages?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = {
                                onEvent(VentasUIEvent.Save)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Guardar"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }



}
