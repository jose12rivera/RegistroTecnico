package edu.ucne.registrotecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import edu.ucne.registrotecnico.data.local.database.TecnicoDb
import edu.ucne.registrotecnico.data.respository.TecnicosRepository
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoViewModel
import edu.ucne.registrotecnicos.presentation.navigation.TecnicosNavHost
import androidx.navigation.compose.rememberNavController
import edu.ucne.registrotecnico.ui.theme.RegistroTecnicoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tecnicoDb = Room.databaseBuilder(
                applicationContext,
                TecnicoDb::class.java,
                "Tecnico.db"
            ).fallbackToDestructiveMigration(false)
            .build()

        val tecnicosRepository = TecnicosRepository(tecnicoDb.TecnicoDao())
        val tecnicoViewModel = TecnicoViewModel(tecnicosRepository)

        setContent() {
            RegistroTecnicoTheme {
                val navController = rememberNavController()
                TecnicosNavHost(
                    navHostController = navController,
                    viewModel = tecnicoViewModel
                )
            }
        }
    }
}
