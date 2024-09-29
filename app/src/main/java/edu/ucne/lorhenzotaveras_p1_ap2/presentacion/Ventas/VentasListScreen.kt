package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity

@Composable
fun VentasListScreen(
    viewModel: VentasViewModel = hiltViewModel(),
    onVentasClick: (Int) -> Unit,
    onAddVentas: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    VentaslistBodyScreen(
        uiState = uiState,
        onVentasClick = onVentasClick,
        onAddVentas = onAddVentas,
        onDeleteVentas = { ventasId ->
            viewModel.onEvent(
                VentasUIEvent.VentasIdChanged(ventasId)
            )
            viewModel.onEvent(
                VentasUIEvent.Delete
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaslistBodyScreen(
    uiState: VentasUiState,
    onVentasClick: (Int) -> Unit,
    onAddVentas: () -> Unit,
    onDeleteVentas: (Int) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ventas",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddVentas
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nueva prioridad"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (uiState.Ventas.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Lista vacía",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {

                    item {
                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Id",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(0.5f)
                            )
                            Text(
                                text = "Cliente",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(0.5f)
                            )
                            Text(
                                text = "Galones",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(0.2f)
                            )
                            Text(
                                text = "Precio",
                                modifier = Modifier.weight(0.3f),
                            )
                            Text(
                                text = "Total",
                                modifier = Modifier.weight(0.3f),
                            )
                        }
                    }

                    items(uiState.Ventas){
                        VentasRow(
                            it = it,
                            onVentasClick =onVentasClick,
                            onDeleteVentas = onDeleteVentas

                        )
                    }
                }
            }
        }
    }
}


@Composable
fun VentasRow(
    it: VentasEntity,
    onVentasClick: (Int) -> Unit,
    onDeleteVentas: (Int) -> Unit
){
    var ShowDeleteC by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    onVentasClick(it.Id ?: 0)
                }
            )
    ){
        Text(
            text = it.Id.toString(),
            modifier = Modifier.weight(2f)
        )
        Text(
            text = it.Cliente,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = it.Galones.toString(),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = it.Precio.toString(),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = it.Total.toString(),
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                onDeleteVentas(it.Id ?: 0)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Eliminar",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { ShowDeleteC = true }
            )
        }
    }
    if (ShowDeleteC) {
        AlertDialog(
            onDismissRequest = { ShowDeleteC = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta venta?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteVentas(it.Id ?: 0)
                        ShowDeleteC = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { ShowDeleteC = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
    HorizontalDivider()
}