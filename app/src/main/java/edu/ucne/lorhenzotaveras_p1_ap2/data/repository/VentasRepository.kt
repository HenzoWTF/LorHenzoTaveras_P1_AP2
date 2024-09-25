package edu.ucne.lorhenzotaveras_p1_ap2.data.repository

import edu.ucne.lorhenzotaveras_p1_ap2.data.local.dao.VentasDao
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity
import javax.inject.Inject

class AlgoRepository@Inject constructor(
    private val ventasDao: VentasDao)
{
    suspend fun save(algo: VentasEntity) = ventasDao.save(algo)
    suspend fun delete(algo: VentasEntity) = ventasDao.delete(algo)
    fun getAll() = ventasDao.getAll()
    suspend fun find(id: Int) = ventasDao.find(id)
}