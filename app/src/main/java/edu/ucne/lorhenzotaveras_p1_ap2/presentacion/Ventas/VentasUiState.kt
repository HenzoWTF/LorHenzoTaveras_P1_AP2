package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity

data class VentasUiState(
    val Id: Int = 0,
    val Cliente: String = "",
    val Galones: Int = 0,
    val DescuentoPorGalon: Int = 0,
    val errorMessages: String = "",
    val Ventas: List<VentasEntity> = emptyList(),
    val success: Boolean = false
)