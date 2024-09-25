package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity

data class VentasUiState(
    val Id: Int? = 0,
    val Cliente: String = "",
    val Galones: Double = 0.0,
    val DescuentoPorGalon: Double = 0.0,
    val Precio: Double = 0.0,
    val TotalDescontado: Double = 0.0,
    val Total: Double = 0.0,
    var errorMessages: String? = null,
    val Ventas: List<VentasEntity> = emptyList(),
    val success: Boolean = false
) {
}