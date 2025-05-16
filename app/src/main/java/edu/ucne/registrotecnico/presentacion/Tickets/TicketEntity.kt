package edu.ucne.registrotecnico.presentacion.Tickets

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val TicketId: Int? = null,
    val Fecha: Date,
    val PrioridadId: Int,
    val Cliente: String,
    val Asunto: String,
    val Descripcion: String,
    val TecnicoId: Int
)
