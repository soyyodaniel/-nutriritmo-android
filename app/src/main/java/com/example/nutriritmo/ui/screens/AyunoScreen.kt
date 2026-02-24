package com.example.nutriritmo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nutriritmo.model.DetenerAyunoRequest
import com.example.nutriritmo.model.IniciarAyunoRequest
import com.example.nutriritmo.network.RetrofitClient
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AyunoScreen(idUsuario: Int) {
    val scope = rememberCoroutineScope()
    var mensaje by remember { mutableStateOf("") }
    var ayunoActivoId by remember { mutableStateOf<Int?>(null) }
    var inicio by remember { mutableStateOf<Long?>(null) }
    var loading by remember { mutableStateOf(false) }

    fun cargarEstado() {
        scope.launch {
            try {
                val ayunos = RetrofitClient.api.obtenerAyunos(idUsuario)
                val activo = ayunos.firstOrNull { it.estado == "activo" }
                ayunoActivoId = activo?.idAyuno
                inicio = activo?.inicioTimestamp
            } catch (_: Exception) { }
        }
    }

    LaunchedEffect(idUsuario) { cargarEstado() }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Control de Ayuno", style = MaterialTheme.typography.headlineSmall)

        ElevatedCard {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(if (ayunoActivoId != null) "Estado: ACTIVO" else "Estado: INACTIVO")
                if (ayunoActivoId != null && inicio != null) {
                    val horas = ((System.currentTimeMillis() - inicio!!) / 3600000.0)
                    Text("Tiempo aprox: ${(horas * 10).roundToInt() / 10.0} horas")
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    scope.launch {
                        loading = true
                        mensaje = ""
                        try {
                            val now = System.currentTimeMillis()
                            RetrofitClient.api.iniciarAyuno(
                                IniciarAyunoRequest(id_usuario = idUsuario, inicio_timestamp = now)
                            )
                            mensaje = "Ayuno iniciado ✅"
                            cargarEstado()
                        } catch (e: Exception) {
                            mensaje = "Error: ${e.message}"
                        } finally {
                            loading = false
                        }
                    }
                },
                enabled = !loading && ayunoActivoId == null,
                modifier = Modifier.weight(1f)
            ) { Text("Iniciar") }

            Button(
                onClick = {
                    scope.launch {
                        loading = true
                        mensaje = ""
                        try {
                            val now = System.currentTimeMillis()
                            val horas = if (inicio != null) ((now - inicio!!) / 3600000.0) else 0.0
                            RetrofitClient.api.detenerAyuno(
                                DetenerAyunoRequest(
                                    id_ayuno = ayunoActivoId!!,
                                    fin_timestamp = now,
                                    duracion_horas = horas
                                )
                            )
                            mensaje = "Ayuno detenido ✅"
                            cargarEstado()
                        } catch (e: Exception) {
                            mensaje = "Error: ${e.message}"
                        } finally {
                            loading = false
                        }
                    }
                },
                enabled = !loading && ayunoActivoId != null,
                modifier = Modifier.weight(1f)
            ) { Text("Detener") }
        }

        if (mensaje.isNotBlank()) {
            Text(mensaje)
        }
    }
}
