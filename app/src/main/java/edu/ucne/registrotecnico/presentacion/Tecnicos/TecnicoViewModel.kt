package edu.ucne.registrotecnico.presentacion.Tecnicos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrotecnico.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnico.data.respository.TecnicosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TecnicoViewModel(
    private val tecnicosRepository: TecnicosRepository
) : ViewModel() {

    private val _tecnicoList = MutableStateFlow<List<TecnicoEntity>>(emptyList())
    val tecnicoList: StateFlow<List<TecnicoEntity>> get() = _tecnicoList

    init {
        loadTecnicos()
    }

    private fun loadTecnicos() {
        viewModelScope.launch {
            tecnicosRepository.getAll().collect { lista ->
                Log.d("TecnicoViewModel", "Lista recibida: ${lista.size}")
                _tecnicoList.value = lista
            }
        }
    }


    fun saveTecnico(tecnico: TecnicoEntity) {
        viewModelScope.launch {
            tecnicosRepository.save(tecnico)
            loadTecnicos()
        }
    }

    fun agregar(nombre: String, sueldo: Double) {
        val tecnico = TecnicoEntity(
            TecnicoId = null,
            Nombre = nombre,
            Sueldo = sueldo
        )
        saveTecnico(tecnico)
    }

    fun update(tecnico: TecnicoEntity) {
        saveTecnico(tecnico)
    }

    fun delete(tecnico: TecnicoEntity) {
        viewModelScope.launch {
            tecnicosRepository.delete(tecnico)
            loadTecnicos()
        }
    }

    fun getTecnicoById(id: Int?): TecnicoEntity? {
        return _tecnicoList.value.find { it.TecnicoId == id }
    }
}
