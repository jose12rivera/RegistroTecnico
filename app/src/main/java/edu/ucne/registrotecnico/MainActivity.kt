package edu.ucne.registrotecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.ucne.registrotecnico.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoListScreen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoScreen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp(tecnicoViewModel: TecnicoViewModel = viewModel()) {
    var showForm by remember { mutableStateOf(false) }
    var tecnicoToEdit by remember { mutableStateOf<TecnicoEntity?>(null) }

    if (showForm) {
        TecnicoScreen(
            tecnico = tecnicoToEdit,
            onSave = { nombre, sueldo ->
                if (tecnicoToEdit == null) {
                    tecnicoViewModel.addTecnico(nombre, sueldo)
                } else {
                    tecnicoViewModel.updateTecnico(
                        tecnicoToEdit!!.copy(Nombre = nombre, Sueldo = sueldo)
                    )
                }
                showForm = false
                tecnicoToEdit = null
            },
            onCancel = {
                showForm = false
                tecnicoToEdit = null
            }
        )
    } else {
        TecnicoListScreen(
            tecnicoList = tecnicoViewModel.tecnicoList,
            onCreate = {
                tecnicoToEdit = null
                showForm = true
            },
            onDelete = { tecnicoViewModel.deleteTecnico(it) },
            onEdit = {
                tecnicoToEdit = it
                showForm = true
            }
        )
    }
}
