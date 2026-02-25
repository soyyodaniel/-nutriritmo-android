package com.example.nutriritmo.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nutriritmo.R
import com.example.nutriritmo.model.LoginRequest
import com.example.nutriritmo.network.RetrofitClient
import com.example.nutriritmo.session.SessionStore
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    sessionStore: SessionStore,
    onLoginOk: () -> Unit,
    onCreateAccount: () -> Unit,
    onForgotPassword: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                .clip(RoundedCornerShape(22.dp))
                .background(Color.White)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo (pon el logo en res/drawable/logo_nutri.png)
           // Image(
               // painter = painterResource(id = R.drawable.logo_nutri),
              //  contentDescription = "NutriRitmo",
               // modifier = Modifier.size(90.dp)
            //)

            Spacer(Modifier.height(10.dp))

            Text("NutriRitmo", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(6.dp))

            Text("¡Bienvenido de nuevo!", style = MaterialTheme.typography.titleLarge)
            Text(
                "Accede a tu cuenta o crea una nueva",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666)
            )

            Spacer(Modifier.height(18.dp))

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

            Spacer(Modifier.height(12.dp))

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    error = null
                    loading = true
                    scope.launch {
                        try {
                            val resp = RetrofitClient.api.login(LoginRequest(email.trim(), password))
                            if (resp.isSuccessful && resp.body() != null) {
                                val body = resp.body()!!
                                sessionStore.saveSession(body.token, body.user.id_usuario)
                                onLoginOk()
                            } else {
                                error = "Credenciales inválidas"
                            }
                        } catch (e: Exception) {
                            Log.e("LOGIN", "Error", e)
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
                Text(if (loading) "Entrando..." else "Iniciar sesión")
            }

            Spacer(Modifier.height(10.dp))

            Text(
                "¿Olvidaste tu contraseña?",
                modifier = Modifier.clickable { onForgotPassword() },
                color = Color(0xFF5A5A5A)
            )

            Spacer(Modifier.height(14.dp))
            Divider()
            Spacer(Modifier.height(14.dp))

            //  Google //
            OutlinedButton(
                onClick = { /* TODO: google later */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Continuar con Google")
            }



            Spacer(Modifier.height(14.dp))

            Row {
                Text("¿Eres nuevo? ")
                Text(
                    "Crea una cuenta",
                    modifier = Modifier.clickable { onCreateAccount() },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}