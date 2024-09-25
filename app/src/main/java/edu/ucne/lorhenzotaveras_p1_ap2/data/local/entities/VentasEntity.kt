package edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentasEntity(
    @PrimaryKey
    val Id: Int? = null,
    val Nombre: String,
)