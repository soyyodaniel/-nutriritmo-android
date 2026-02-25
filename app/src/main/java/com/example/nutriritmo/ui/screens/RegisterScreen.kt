package com.example.nutriritmo.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nutriritmo.model.RegisterRequest
import com.example.nutriritmo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    onRegisterOk: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    // opcionales
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(22.dp))
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackToLogin) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Spacer(Modifier.width(6.dp))
                Text("Crear cuenta", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(Modifier.height(10.dp))

            Text("Regístrate para empezar", style = MaterialTheme.typography.titleMedium)
            Text(
                "Puedes completar tus datos después.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666)
            )

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(14.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = objetivo,
                    onValueChange = { objetivo = it },
                    label = { Text("Objetivo") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    label = { Text("Peso (kg)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = altura,
                    onValueChange = { altura = it },
                    label = { Text("Altura (m)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(Modifier.height(12.dp))

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    error = null

                    if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
                        error = "Completa nombre, email y contraseña"
                        return@Button
                    }
                    if (password.length < 6) {
                        error = "La contraseña debe tener mínimo 6 caracteres"
                        return@Button
                    }
                    if (password != confirm) {
                        error = "Las contraseñas no coinciden"
                        return@Button
                    }

                    loading = true
                    scope.launch {
                        try {
                            val req = RegisterRequest(
                                nombre = nombre.trim(),
                                edad = edad.toIntOrNull(),
                                peso_inicial = peso.toDoubleOrNull(),
                                altura = altura.toDoubleOrNull(),
                                objetivo = objetivo.ifBlank { null },
                                email = email.trim(),
                                password = password
                            )

                            val resp = RetrofitClient.api.register(req)

                            if (resp.isSuccessful) {
                                onRegisterOk() // ✅ regresar a Login
                            } else {
                                error = "No se pudo registrar (¿correo ya existe?)"
                            }
                        } catch (e: Exception) {
                            Log.e("REGISTER", "Error", e)
                            error = "Error de red"
                        } finally {
                            loading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                enabled = !loading
            ) {
                Text(if (loading) "Creando..." else "Crear cuenta")
            }

            Spacer(Modifier.height(10.dp))

            Text(
                "¿Ya tienes cuenta? Inicia sesión",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onBackToLogin() },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}