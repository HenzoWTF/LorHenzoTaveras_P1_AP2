package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


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
                                text = "Cliente",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(0.3f)
                            )
                            Text(
                                text = "Galones",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(0.3f)
                            )
                            Text(
                                text = "Precio",
                                modifier = Modifier.weight(0.3f),
                            )
                            Text(
                                text = "Total",
                                modifier = Modifier.weight(0.2f),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasRow(
    it: VentasEntity,
    onVentasClick: (Int) -> Unit,
    onDeleteVentas: (Int) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }


    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                showDeleteDialog = true
                false
            } else {
                false
            }
        }
    )

    SwipeToDismiss(
        state = state,
        background = {
            val color = if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Color.Red
            } else Color.Transparent
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        },
        dismissContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(onClick = { onVentasClick(it.Id ?: 0) })
                    .padding(16.dp)
            ) {
                Text(
                    text = it.Cliente,
                    modifier = Modifier.weight(1f)
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
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    )


    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta venta?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteVentas(it.Id ?: 0)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VentaListPreview() {
    val list = listOf(
        VentasEntity(
            Id = 1,
            Cliente = "Lor",
            Galones = 5.0,
            DescuentoPorGalon = 5.0,
            Precio = 10.0,
            TotalDescontado = 25.0,
            Total = 25.0
        ),
    )

    VentaslistBodyScreen(
        uiState = VentasUiState(Ventas = list),
        onVentasClick ={},
        onAddVentas = {},
        onDeleteVentas = {}
    )
}