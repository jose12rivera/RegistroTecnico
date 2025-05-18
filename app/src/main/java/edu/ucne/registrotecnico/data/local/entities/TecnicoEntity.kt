package edu.ucne.registrotecnico.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tecnicos")
data class TecnicoEntity(
    @PrimaryKey(autoGenerate = true)
    val TecnicoId: Int? = null,
    val Nombre: String = "",
    val Sueldo: Double = 0.0
)
