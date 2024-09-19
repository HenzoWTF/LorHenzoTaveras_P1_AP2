package edu.ucne.lorhenzotaveras_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.AlgoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlgoDao {

    @Upsert()
    suspend fun save()

    @Query("""
        SELECT * 
        FROM Algos
        WHERE Id = :id
        LIMIT 1
    """)

    suspend fun find(id: Int): AlgoEntity?

    @Update
    suspend fun update(prioridad: AlgoEntity)

    @Delete
    suspend fun delete(Prioridad: AlgoEntity)

    @Query("DELETE FROM algos")
    suspend fun deleteAll()

    @Query("SELECT * FROM algos")
    fun getAll(): Flow<List<AlgoEntity>>

    @Query("SELECT * FROM algos WHERE Id = :Id")
    suspend fun getById(Id: Int): AlgoEntity?

}
