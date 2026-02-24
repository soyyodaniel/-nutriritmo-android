package com.example.nutriritmo.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*

import com.example.nutriritmo.ui.screens.AyunoScreen
import com.example.nutriritmo.ui.screens.DashboardScreen
import com.example.nutriritmo.ui.screens.HistorialScreen

sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    data object Dashboard : Screen("dashboard", "Inicio", Icons.Filled.Home)
    data object Ayuno : Screen("ayuno", "Ayuno", Icons.Filled.Timer)
    data object Historial : Screen("historial", "Historial", Icons.Filled.History)
}

@Composable
fun NutriNav(idUsuario: Int = 1) { // 👈 por ahora fijo, luego lo conectamos al login
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
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen(idUsuario) }
            composable(Screen.Ayuno.route) { AyunoScreen(idUsuario) }
            composable(Screen.Historial.route) { HistorialScreen(idUsuario) }
        }
    }
}
