package edu.ucne.lorhenzotaveras_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentasDao {

    @Upsert()
    suspend fun save(Ventas: VentasEntity)

    @Query("""
        SELECT * 
        FROM Ventas
        WHERE Id = :id
        LIMIT 1
    """)

    suspend fun find(id: Int): VentasEntity?

    @Update
    suspend fun update(Ventas: VentasEntity)

    @Delete
    suspend fun delete(Ventas: VentasEntity)

    @Query("DELETE FROM Ventas")
    suspend fun deleteAll()

    @Query("SELECT * FROM Ventas")
    fun getAll(): Flow<List<VentasEntity>>

    @Query("SELECT * FROM Ventas WHERE Id = :Id")
    suspend fun getById(Id: Int): VentasEntity?

}
