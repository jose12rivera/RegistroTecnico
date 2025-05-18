package edu.ucne.registrotecnicos.presentation.navigation

import DashboardScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoViewModel
import edu.ucne.registrotecnico.presentacion.navegation.Screen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoListScreen
import edu.ucne.registrotecnico.presentacion.Tecnicos.TecnicoScreen
import edu.ucne.registrotecnico.presentacion.Tickets.TicketListScreen
import edu.ucne.registrotecnico.presentacion.Tickets.TicketViewModel
import edu.ucne.registrotecnico.ui.tickets.TicketScreen

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

        composable("dashboard") {
            DashboardScreen(navController =  navHostController)
        }
        composable("tecnicoList") {
            val tecnicoList = tecnicoViewModel.tecnicoList.collectAsState().value

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
            val tecnico = tecnicoViewModel.getTecnicoById(tecnicoId)

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
                    // Corregido: usar ruta 'ticket_form/{ticketId}' para editar
                    navHostController.navigate("ticket_form/${ticket.TicketId}")
                },
                onCreate = {
                    // Corregido: usar ruta 'ticket_form' para crear
                    navHostController.navigate("ticket_form")
                },
                onDelete = { ticket ->
                    ticketViewModel.delete(ticket)
                }
            )
        }



        composable("ticket_form/{ticketId}") { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId")?.toIntOrNull()
            val ticket = ticketViewModel.getTicketById(ticketId)

            TicketScreen(
                ticket = ticket,
                agregarTicket = { fecha, cliente, asunto, descripcion, prioridadId, tecnicoId ->
                    val updatedTicket = ticket?.copy(
                        Fecha = fecha,
                        Cliente = cliente,
                        Asunto = asunto,
                        Descripcion = descripcion,
                        PrioridadId = prioridadId,
                        TecnicoId = tecnicoId
                    )
                    if (updatedTicket != null) {
                        ticketViewModel.update(updatedTicket)
                    }
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }

        // Ruta para crear nuevo ticket (sin parámetro)
        composable("ticket_form") {
            TicketScreen(
                ticket = null,
                agregarTicket = { fecha, cliente, asunto, descripcion, prioridadId, tecnicoId ->
                    ticketViewModel.agregarTicket(
                        fecha, cliente, asunto, descripcion, prioridadId, tecnicoId
                    )
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}
