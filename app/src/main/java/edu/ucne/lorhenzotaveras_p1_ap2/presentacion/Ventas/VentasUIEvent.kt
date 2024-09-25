package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas

sealed interface VentasUIEvent {
    data class ClienteChange(val Cliente: String) : VentasUIEvent
    data class GalonesChange(val Galones: Double) : VentasUIEvent
    data class DescuentoPorGalonChange(val DescuentoPorGalon: Double) : VentasUIEvent
    data class PrecioGalonChange(val Precio: Double) : VentasUIEvent
    data class TotalDescuentoChange(val TotalDescontado: Double) : VentasUIEvent
    data class TotalChange(val Total: Double) : VentasUIEvent

}