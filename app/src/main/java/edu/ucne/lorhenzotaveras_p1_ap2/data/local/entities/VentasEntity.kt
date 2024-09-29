package edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Ventas")
data class VentasEntity(
    @PrimaryKey
    val Id: Int? = null,
    val Cliente: String = "",
    val Galones: Double,
    val DescuentoPorGalon: Double,
    val Precio: Double,
    val TotalDescontado: Double,
    val Total: Double

)