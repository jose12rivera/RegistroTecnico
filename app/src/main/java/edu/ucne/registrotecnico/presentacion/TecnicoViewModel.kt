package edu.ucne.registrotecnico.presentacion

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import edu.ucne.registrotecnico.data.local.entities.TecnicoEntity

class TecnicoViewModel : ViewModel() {
    private var nextId = 1
    val tecnicoList = mutableStateListOf<TecnicoEntity>()

    fun addTecnico(nombre: String, sueldo: String) {
        tecnicoList.add(
            TecnicoEntity(
                TecnicoId = nextId++,
                Nombre = nombre,
                Sueldo = sueldo
            )
        )
    }

    fun updateTecnico(updatedTecnico: TecnicoEntity) {
        val index = tecnicoList.indexOfFirst { it.TecnicoId == updatedTecnico.TecnicoId }
        if (index != -1) {
            tecnicoList[index] = updatedTecnico
        }
    }

    fun deleteTecnico(tecnico: TecnicoEntity) {
        tecnicoList.remove(tecnico)
    }
}