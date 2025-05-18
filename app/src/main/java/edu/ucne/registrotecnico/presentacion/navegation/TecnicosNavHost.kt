package edu.ucne.registrotecnicos.presentation.navigation

import TicketScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrotecnico.presentacion.Home.DashboardScreen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoViewModel
import edu.ucne.registrotecnico.presentacion.navegation.Screen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoListScreen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoScreen
import edu.ucne.registrotecnico.presentacion.Tickets.TicketListScreen
import edu.ucne.registrotecnico.presentacion.Tickets.TicketViewModel

@Composable
fun TecnicosNavHost(
    navHostController: NavHostController,
    tecnicoViewModel: TecnicoViewModel,
    ticketViewModel: TicketViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = "dashboard"
    ) {

        composable("dashboard") { //Aqui se dirije al componente principal de mi aplicacion
            DashboardScreen(navController =  navHostController)
        }
        composable("tecnicoList") {
            val tecnicoList =   tecnicoViewModel.tecnicoList.collectAsState().value

            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onEdit = { tecnico ->
                    navHostController.navigate(Screen.Tecnico(tecnico.TecnicoId))
                },
                onCreate = {
                    navHostController.navigate(Screen.Tecnico(null))
                },
                onDelete = { tecnico ->
                    tecnicoViewModel.delete(tecnico)
                }
            )
        }

        composable<Screen.Tecnico> { backStackEntry ->
            val tecnicoId = backStackEntry.toRoute<Screen.Tecnico>().tecnicoId
            val tecnico =   tecnicoViewModel.getTecnicoById(tecnicoId)

            TecnicoScreen(
                tecnico = tecnico,
                agregarTecnico = { nombre, sueldo ->
                    if (tecnico == null) {
                        tecnicoViewModel.agregar(nombre, sueldo)
                    } else {
                        tecnicoViewModel.update(tecnico.copy(Nombre = nombre, Sueldo = sueldo))
                    }
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }


        composable("TicketList") {
            val ticketList = ticketViewModel.ticketList.collectAsState().value

            TicketListScreen(
                TicketList = ticketList,
                onEdit = { ticket ->
                    navHostController.navigate("ticket/${ticket.TicketId}")
                },
                onCreate = {
                    navHostController.navigate("ticket/null")
                },
                onDelete = { ticket ->
                    ticketViewModel.delete(ticket)
                }
            )
        }

        composable("Ticket/{ticketId}") { backStackEntry ->
            val ticketIdParam = backStackEntry.arguments?.getString("ticketId")
            val ticketId = if (ticketIdParam == "null") null else ticketIdParam?.toIntOrNull()
            val ticket = ticketViewModel.getTicketById(ticketId)

            TicketScreen(
                ticket = ticket,
                agregarTicket = { fecha, cliente, asunto, descripcion, prioridad ->
                    if (ticket == null) {
                        ticketViewModel.agregarTicket(fecha, cliente, asunto, descripcion, prioridad)
                    } else {
                        ticketViewModel.update(
                            ticket.copy(
                                Fecha = fecha,
                                Cliente = cliente,
                                Asunto = asunto,
                                Descripcion = descripcion,
                                Prioridad = prioridad.toString()
                            )
                        )
                    }
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }


    }
}