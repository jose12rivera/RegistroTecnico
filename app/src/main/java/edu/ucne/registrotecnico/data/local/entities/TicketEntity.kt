package edu.ucne.registrotecnico.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val TicketId: Int? = null,
    val Fecha: String,
    val Cliente: String,
    val Asunto: String,
    val Descripcion: String,
    val PrioridadId: Int,
    val TecnicoId: Int
)

@Entity(tableName = "Prioridades")
data class PrioridadEntity(
    @PrimaryKey(autoGenerate = true)
    val PrioridadId: Int? = null,
    val Nivel: String,
    val Color: String
)