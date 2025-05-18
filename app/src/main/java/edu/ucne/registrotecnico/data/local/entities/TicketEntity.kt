package edu.ucne.registrotecnico.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val TicketId: Int = 0,
    val Fecha: String,
    val Cliente: String,
    val Asunto: String,
    val Descripcion: String,
    val Prioridad: String
)

