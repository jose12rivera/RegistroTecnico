package edu.ucne.registrotecnico.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tecnicos")
data class TecnicoEntity (
    @PrimaryKey
    val TecnicoId:Int?,
    val Nombre:String="",
    val Sueldo:String="",

)