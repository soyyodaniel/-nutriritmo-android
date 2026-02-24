package com.example.nutriritmo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nutriritmo.network.RetrofitClient
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HistorialScreen(idUsuario: Int) {

    val scope = rememberCoroutineScope()
    var ayunos by remember { mutableStateOf<List<com.example.nutriritmo.model.AyunoDto>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(idUsuario) {
        scope.launch {
            try {
                ayunos = RetrofitClient.api.obtenerAyunos(idUsuario)
            } catch (e: Exception) {
                error = e.message ?: "Error desconocido"
            } finally {
                loading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Historial de Ayunos",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            loading -> {
                CircularProgressIndicator()
            }

            error.isNotBlank() -> {
                Text("Error: $error")
            }

            ayunos.isEmpty() -> {
                Text("No hay registros todavía")
            }

            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(ayunos) { ayuno ->

                        ElevatedCard {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {

                                Text("Estado: ${ayuno.estado}")

                                val horas = ayuno.duracionHoras ?: 0.0
                                Text("Duración: ${(horas * 10).roundToInt() / 10.0} horas")

                                Text("Inicio: ${ayuno.inicioTimestamp}")

                                ayuno.finTimestamp?.let {
                                    Text("Fin: $it")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}