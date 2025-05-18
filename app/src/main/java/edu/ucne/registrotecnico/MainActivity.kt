package edu.ucne.registrotecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.registrotecnico.data.local.database.TecnicoDb
import edu.ucne.registrotecnico.data.respository.TecnicosRepository
import edu.ucne.registrotecnico.data.respository.TicketRepository
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoViewModel
import edu.ucne.registrotecnico.presentacion.Tickets.TicketViewModel
import edu.ucne.registrotecnico.ui.theme.RegistroTecnicoTheme
import edu.ucne.registrotecnicos.presentation.navigation.TecnicosNavHost

class MainActivity : ComponentActivity() {
    private lateinit var tecnicoDb: TecnicoDb
    private lateinit var tecnicosRepository: TecnicosRepository
    private lateinit var tecnicosViewModel: TecnicoViewModel
    private lateinit var ticketRepository: TicketRepository
    private lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Room.databaseBuilder(
            applicationContext,
            TecnicoDb::class.java,
            "Tecnico.db"
        ).fallbackToDestructiveMigration()
            .build()

        val tecnicosRepository = TecnicosRepository(database.TecnicoDao())
        val tecnicoViewModel = TecnicoViewModel(tecnicosRepository)

        val ticketRepository = TicketRepository(database.TicketDao())
        val ticketViewModel = TicketViewModel(ticketRepository)

        setContent {
            RegistroTecnicoTheme {
                val navController = rememberNavController()
                TecnicosNavHost(
                    navHostController = navController,
                    tecnicoViewModel = tecnicoViewModel,
                    ticketViewModel = ticketViewModel
                )
            }
        }
    }
}