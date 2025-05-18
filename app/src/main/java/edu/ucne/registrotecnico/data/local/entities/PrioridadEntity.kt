package edu.ucne.registrotecnico.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prioridades")
data class PrioridadEntity(
    @PrimaryKey(autoGenerate = true)
    val PrioridadId: Int? = null,
    val Nivel: String,
    val Color: String
) {
    companion object {
        val PrioridadesDemo = listOf(
            PrioridadEntity(Nivel = "Alta", Color = "#FF0000"),
            PrioridadEntity(Nivel = "Media", Color = "#FFFF00"),
            PrioridadEntity(Nivel = "Baja", Color = "#00FF00")
        )
    }
}