package edu.ucne.registrotecnico.presentacion.Tickets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrotecnico.data.local.entities.TicketEntity
import edu.ucne.registrotecnico.data.respository.TicketRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketViewModel @Inject constructor(
    private val repository: TicketRepository
) : ViewModel() {

    // Lista observable de tickets
    val ticketList: StateFlow<List<TicketEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Agregar un ticket
    fun agregarTicket(
        fecha: String,
        cliente: String,
        asunto: String,
        descripcion: String,
        prioridadId: Int,
        tecnicoId: Int
    ) {
        viewModelScope.launch {
            val ticket = TicketEntity(
                Fecha = fecha,
                Cliente = cliente,
                Asunto = asunto,
                Descripcion = descripcion,
                PrioridadId = prioridadId,
                TecnicoId = tecnicoId
            )
            saveTicket(ticket)
        }
    }

    // Guardar o actualizar
    fun saveTicket(ticket: TicketEntity) {
        viewModelScope.launch {
            repository.save(ticket)
        }
    }

    // Eliminar
    fun delete(ticket: TicketEntity) {
        viewModelScope.launch {
            repository.delete(ticket)
        }
    }

    // Actualizar (alias de save)
    fun update(ticket: TicketEntity) {
        saveTicket(ticket)
    }

    // Buscar por ID
    fun getTicketById(id: Int?): TicketEntity? {
        return ticketList.value.find { it.TicketId == id }
    }
}
