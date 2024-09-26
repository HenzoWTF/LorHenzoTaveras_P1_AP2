package edu.ucne.lorhenzotaveras_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.dao.VentasDao
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity

@Database(
    entities = [
        VentasEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class Parcial1Db : RoomDatabase(){
    abstract fun VentasDao(): VentasDao
}