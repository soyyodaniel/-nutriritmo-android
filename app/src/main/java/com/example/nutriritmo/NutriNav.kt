package com.example.nutriritmo.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.nutriritmo.session.SessionStore
import com.example.nutriritmo.ui.screens.AyunoScreen
import com.example.nutriritmo.ui.screens.DashboardScreen
import com.example.nutriritmo.ui.screens.HistorialScreen
import com.example.nutriritmo.ui.screens.LoginScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class Screen(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    data object Dashboard : Screen("dashboard", "Inicio", Icons.Filled.Home)
    data object Ayuno : Screen("ayuno", "Ayuno", Icons.Filled.Timer)
    data object Historial : Screen("historial", "Historial", Icons.Filled.History)
}

@Composable
fun NutriNav() {
    val context = LocalContext.current
    val sessionStore = remember { SessionStore(context) }
    val scope = rememberCoroutineScope()

    // Estado de sesión
    var token by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<Int?>(null) }

    // Cargar sesión al iniciar
    LaunchedEffect(Unit) {
        token = sessionStore.tokenFlow.first()
        userId = sessionStore.userIdFlow.first()
    }

    // Si no hay token -> Login (sin bottom bar)
    if (token.isNullOrBlank() || userId == null) {
        LoginScreen(
            sessionStore = sessionStore,
            onLoginOk = {
                scope.launch {
                    token = sessionStore.tokenFlow.first()
                    userId = sessionStore.userIdFlow.first()
                }
            },
            onCreateAccount = {
                // luego hacemos RegisterScreen si quieres
            }
        )
        return
    }

    // Si hay sesión -> app normal con bottom nav
    val navController = rememberNavController()
    val items = listOf(Screen.Dashboard, Screen.Ayuno, Screen.Historial)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen(userId!!) }
            composable(Screen.Ayuno.route) { AyunoScreen(userId!!) }
            composable(Screen.Historial.route) { HistorialScreen(userId!!) }
        }
    }
}