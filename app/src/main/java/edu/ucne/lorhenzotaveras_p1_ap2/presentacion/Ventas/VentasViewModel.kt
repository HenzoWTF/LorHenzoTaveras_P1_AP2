package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity
import edu.ucne.lorhenzotaveras_p1_ap2.data.repository.VentasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentasViewModel @Inject constructor(
    private val repository: VentasRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(VentasUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVentas()
    }

    fun onEvent(event: VentasUIEvent){
        viewModelScope.launch {
            when(event){
                is VentasUIEvent.ClienteChange ->  _uiState.update { it.copy(Cliente = event.Cliente) }
                is VentasUIEvent.DescuentoPorGalonChange ->  _uiState.update { it.copy(DescuentoPorGalon = event.DescuentoPorGalon.toDouble()?: 0.0) }
                is VentasUIEvent.GalonesChange -> _uiState.update { it.copy(Galones = event.Galones.toDouble()?: 0.0) }
                is VentasUIEvent.PrecioGalonChange -> _uiState.update { it.copy(Precio = event.Precio.toDouble()?: 0.0) }
                is VentasUIEvent.TotalChange -> _uiState.update { it.copy(Total = event.Total.toDouble()?: 0.0) }
                is VentasUIEvent.TotalDescuentoChange -> _uiState.update { it.copy(TotalDescontado = event.TotalDescontado.toDouble()?: 0.0) }
                VentasUIEvent.Delete -> repository.delete(_uiState.value.toEntity())

                VentasUIEvent.Save ->{
                    _uiState.value.errorMessages = validateInput()
                    if (_uiState.value.errorMessages == null) {
                        repository.save(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true) }
                    }
                }
                is VentasUIEvent.SelectedVentas -> {
                    if (event.VentaId > 0) {
                        val venta = repository.find(event.VentaId)
                        if (venta != null) {
                            _uiState.update {
                                it.copy(
                                    Id = venta.Id,
                                    Cliente = venta.Cliente,
                                    Galones = venta.Galones,
                                    DescuentoPorGalon = venta.DescuentoPorGalon,
                                    Precio = venta.Precio,
                                    TotalDescontado = venta.TotalDescontado,
                                    Total = venta.Total
                                )
                            }
                        }
                    }
                }

                is VentasUIEvent.VentasIdChanged -> {
                    _uiState.update {
                        it.copy(Id = event.ventasId)
                    }
                }
            }
        }
    }

    private fun getVentas() {
        viewModelScope.launch {
            repository.getAll().collect {ventas ->
                _uiState.update {
                    it.copy(Ventas = ventas)
                }
            }
        }
    }


    private fun validateInput(): String? {
        return when {
            _uiState.value.Cliente.isNullOrBlank() -> "EL CLiente no puede estar vacía."
            _uiState.value.Galones <= 0 -> "Los Galones deben ser mayores que 0."
            _uiState.value.DescuentoPorGalon <= 0 -> "El Descuento Por Galón debe ser mayor que 0."
            _uiState.value.Precio <= 0 -> "El Precio debe ser mayor que 0."
            else -> null
        }
    }
}

private fun VentasUiState.toEntity(): VentasEntity {
    return VentasEntity(
        Id = Id,
        Cliente = Cliente,
        Galones = Galones,
        DescuentoPorGalon = DescuentoPorGalon,
        Precio = Precio,
        TotalDescontado = TotalDescontado,
        Total = Total
    )
}
