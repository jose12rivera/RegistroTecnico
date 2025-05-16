package edu.ucne.registrotecnico.presentacion.Tecnicos


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnico.data.local.entities.TecnicoEntity

@Composable
fun TecnicoScreen(
    tecnico: TecnicoEntity?,
    agregarTecnico: (String, Double) -> Unit,
    onCancel: () -> Unit
) {
    var nombre by remember { mutableStateOf(tecnico?.Nombre ?: "") }
    var sueldo by remember { mutableStateOf(tecnico?.Sueldo?.toString() ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (tecnico == null) "Registrar Técnico" else "Editar Técnico",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del técnico") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = sueldo,
                    onValueChange = { sueldo = it },
                    label = { Text("Sueldo") },
                    placeholder = { Text("0.0") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                errorMessage?.let {
                    Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            val sueldoDouble = sueldo.toDoubleOrNull()
                            if (nombre.isBlank()) {
                                errorMessage = "El nombre no puede estar vacío."
                            } else if (sueldoDouble == null || sueldoDouble <= 0.0) {
                                errorMessage = "El sueldo debe ser un número válido y mayor que cero."
                            } else {
                                errorMessage = null
                                agregarTecnico(nombre, sueldoDouble)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        if (tecnico == null) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Guardar")
                            Spacer(Modifier.width(8.dp))
                            Text("Guardar")
                        } else {
                            Icon(Icons.Default.Edit, contentDescription = "Actualizar")
                            Spacer(Modifier.width(8.dp))
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }
}

