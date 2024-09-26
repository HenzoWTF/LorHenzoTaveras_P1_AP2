package edu.ucne.lorhenzotaveras_p1_ap2.data.repository

import edu.ucne.lorhenzotaveras_p1_ap2.data.local.dao.VentasDao
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.entities.VentasEntity
import javax.inject.Inject

class VentasRepository@Inject constructor(
    private val ventasDao: VentasDao)
{
    suspend fun save(ventas: VentasEntity) = ventasDao.save(ventas)
    suspend fun delete(ventas: VentasEntity) = ventasDao.delete(ventas)
    fun getAll() = ventasDao.getAll()
    suspend fun find(id: Int) = ventasDao.find(id)
}