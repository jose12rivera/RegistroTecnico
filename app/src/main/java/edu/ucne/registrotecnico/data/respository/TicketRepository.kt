package edu.ucne.registrotecnico.data.respository

import edu.ucne.registrotecnico.data.local.dao.TicketDao
import edu.ucne.registrotecnico.data.local.entities.PrioridadEntity
import edu.ucne.registrotecnico.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow



class TicketRepository (
    private val ticketDao: TicketDao,

) {
    suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)

    suspend fun find(id: Int): TicketEntity? = ticketDao.find(id)

    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)

    fun getAll(): Flow<List<TicketEntity>> = ticketDao.getAll()

}
