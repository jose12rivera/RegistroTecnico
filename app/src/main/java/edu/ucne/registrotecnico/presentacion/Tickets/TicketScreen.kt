package edu.ucne.registrotecnico.ui.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnico.data.local.entities.TicketEntity
import edu.ucne.registrotecnico.data.local.entities.PrioridadEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    ticket: TicketEntity?,
    agregarTicket: (String, String, String, String, Int, Int) -> Unit,
    onCancel: () -> Unit
) {
    // Campos normales
    var fecha by remember { mutableStateOf(ticket?.Fecha ?: "") }
    var cliente by remember { mutableStateOf(ticket?.Cliente ?: "") }
    var asunto by remember { mutableStateOf(ticket?.Asunto ?: "") }
    var descripcion by remember { mutableStateOf(ticket?.Descripcion ?: "") }
    var tecnicoId by remember { mutableStateOf(ticket?.TecnicoId?.toString() ?: "") }
    var error by remember { mutableStateOf<String?>(null) }

    // Lista demo de prioridades
    val prioridades = PrioridadEntity.PrioridadesDemo

    // Estado para dropdown
    var expanded by remember { mutableStateOf(false) }
    var prioridadSeleccionada by remember {
        mutableStateOf(
            ticket?.PrioridadId?.let { id ->
                prioridades.find { it.PrioridadId == id } ?: prioridades.firstOrNull()
            } ?: prioridades.firstOrNull()
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (ticket == null) "Registrar Ticket" else "Editar Ticket",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFEDE7F6), Color(0xFF7E57C2))
                    )
                )
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.95f), shape = MaterialTheme.shapes.medium)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cliente,
                    onValueChange = { cliente = it },
                    label = { Text("Cliente") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = asunto,
                    onValueChange = { asunto = it },
                    label = { Text("Asunto") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Dropdown de prioridad
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = prioridadSeleccionada?.Nivel ?: "Selecciona Prioridad",
                        onValueChange = {},
                        label = { Text("Prioridad") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        prioridades.forEach { prioridad ->
                            DropdownMenuItem(
                                text = { Text(prioridad.Nivel) },
                                onClick = {
                                    prioridadSeleccionada = prioridad
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = tecnicoId,
                    onValueChange = { tecnicoId = it },
                    label = { Text("Técnico ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onCancel() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            when {
                                fecha.isBlank() -> error = "La fecha es requerida"
                                cliente.isBlank() -> error = "El cliente es requerido"
                                asunto.isBlank() -> error = "El asunto es requerido"
                                descripcion.isBlank() -> error = "La descripción es requerida"
                                prioridadSeleccionada == null -> error = "Debe seleccionar una prioridad"
                                tecnicoId.isBlank() -> error = "El Técnico ID es requerido"
                                tecnicoId.toIntOrNull() == null -> error = "Técnico ID debe ser un número"
                                else -> {
                                    error = null
                                    agregarTicket(
                                        fecha,
                                        cliente,
                                        asunto,
                                        descripcion,
                                        prioridadSeleccionada?.PrioridadId ?: 0,
                                        tecnicoId.toInt()
                                    )
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketScreen(
        ticket = null,
        agregarTicket = { fecha, cliente, asunto, descripcion, prioridadId, tecnicoId ->
            println("Nuevo ticket: $fecha, $cliente, $asunto, $descripcion, PrioridadId: $prioridadId, TecnicoId: $tecnicoId")
        },
        onCancel = { println("Cancelado") }
    )
}
