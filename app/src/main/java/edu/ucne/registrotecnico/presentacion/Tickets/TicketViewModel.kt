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

class TicketViewModel@Inject constructor(
    private val repository: TicketRepository
) : ViewModel() {

    // Exponemos la lista de tickets como StateFlow
    val ticketList: StateFlow<List<TicketEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarTicket(fecha: String, cliente: String, asunto: String, descripcion: String, prioridad: String) {
        viewModelScope.launch {
            val ticket = TicketEntity(
                Fecha = fecha,
                Cliente = cliente,
                Asunto = asunto,
                Descripcion = descripcion,
                Prioridad = prioridad
            )
            saveTicket(ticket)
        }
    }


    // Guardar o actualizar un ticket
    fun saveTicket(ticket: TicketEntity) {
        viewModelScope.launch {
            repository.save(ticket)
        }
    }

    // Eliminar un ticket
    fun delete(ticket: TicketEntity) {
        viewModelScope.launch {
            repository.delete(ticket)
        }
    }

    // Actualizar un ticket
    fun update(ticket: TicketEntity) {
        saveTicket(ticket)
    }

    // Buscar ticket por ID en la lista actual
    fun getTicketById(id: Int?): TicketEntity? {
        return ticketList.value.find { it.TicketId == id }
    }
}