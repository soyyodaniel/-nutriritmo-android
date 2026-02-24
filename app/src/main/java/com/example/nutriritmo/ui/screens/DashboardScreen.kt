package com.example.nutriritmo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nutriritmo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(idUsuario: Int) {
    val scope = rememberCoroutineScope()
    var estado by remember { mutableStateOf("Cargando...") }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(idUsuario) {
        try {
            val ayunos = RetrofitClient.api.obtenerAyunos(idUsuario)
            val activo = ayunos.firstOrNull { it.estado == "activo" }
            estado = if (activo != null) "Tienes un ayuno ACTIVO" else "No hay ayuno activo"
        } catch (e: Exception) {
            error = e.message
            estado = "No se pudo cargar"
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("NutriRitmo", style = MaterialTheme.typography.headlineMedium)
        Text("Dashboard", style = MaterialTheme.typography.titleMedium)

        ElevatedCard {
            Column(Modifier.padding(16.dp)) {
                Text("Estado actual")
                Spacer(Modifier.height(6.dp))
                Text(estado, style = MaterialTheme.typography.bodyLarge)
                if (error != null) {
                    Spacer(Modifier.height(6.dp))
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        Text("Accesos rápidos", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { /* luego mandamos navegación a Comidas */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrar comida (próximo)")
        }
        Button(onClick = { /* navegación a Ayuno */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Control de ayuno")
        }
        Button(onClick = { /* navegación a Historial */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Ver historial")
        }
    }
}
