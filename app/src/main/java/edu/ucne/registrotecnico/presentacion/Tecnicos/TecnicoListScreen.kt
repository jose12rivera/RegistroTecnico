package edu.ucne.registrotecnico.presentacion.Tecnicos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnico.data.local.entities.TecnicoEntity

@Composable
fun TecnicoListScreen(
    tecnicoList: List<TecnicoEntity>,
    onCreate: () -> Unit,
    onDelete: (TecnicoEntity) -> Unit,
    onEdit: (TecnicoEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate,
                containerColor = Color(0xFF4CAF50), // Verde,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF60B5FF), Color(0xFF60B5FF)) // Azul claro
                    )

                )
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Lista de Técnicos",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tecnicoList) { tecnico ->
                    TecnicoRow(tecnico, onDelete, onEdit)
                }
            }
        }
    }
}

@Composable
fun TecnicoRow(
    tecnico: TecnicoEntity,
    onDelete: (TecnicoEntity) -> Unit,
    onEdit: (TecnicoEntity) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Nombre: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = tecnico.Nombre, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Sueldo: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "RD$${tecnico.Sueldo}", fontSize = 16.sp)

                }
            }


            Row {
                IconButton(onClick = { onEdit(tecnico) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar", tint = Color(0xFF1976D2)) // Azul
                }
                IconButton(onClick = { onDelete(tecnico) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}