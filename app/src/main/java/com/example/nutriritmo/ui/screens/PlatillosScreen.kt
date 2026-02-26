package com.example.nutriritmo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(onBack: () -> Unit) {
    var showRecipe by remember { mutableStateOf(false) }
    var selectedMeal by remember { mutableStateOf("") }

    // Fondo con un color sutil para que las tarjetas resalten
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState()) // Por si crecen las tarjetas
        ) {
            Text(
                text = "Tu menú del día 🍽️",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(32.dp))

            // Lista de comidas con mejores espaciados
            val meals = listOf(
                Triple("Desayuno", "🍳", "Huevos a la mexicana"),
                Triple("Comida", "🌮", "Tacos de pollo"),
                Triple("Cena", "🥗", "Ensalada ligera")
            )

            meals.forEach { (title, icon, meal) ->
                MealCard(
                    title = title,
                    icon = icon,
                    meal = meal,
                    onClick = {
                        selectedMeal = meal
                        showRecipe = true
                    }
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.weight(1f)) // Empuja el botón hacia abajo

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Regresar")
            }
        }
    }
    @Composable
    fun RecipeDialog(mealName: String, onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Receta: $mealName",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    when (mealName) {
                        "Huevos a la mexicana" -> "Huevos, tomate, cebolla, chile, sal, aceite y tortillas."
                        "Tacos de pollo" -> "Pollo, tortillas, cebolla, cilantro, limón y salsa."
                        "Ensalada ligera" -> "Lechuga, jitomate, pepino, aceite de oliva y limón."
                        else -> "Ingredientes no disponibles."
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Entendido", fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(28.dp) // Diálogo con esquinas suaves
        )
    }

    // Modal más moderno usando un estilo de hoja o diálogo centrado
    if (showRecipe) {
        RecipeDialog(
            mealName = selectedMeal,
            onDismiss = { showRecipe = false }
        )
    }
}

@Composable
fun MealCard(title: String, icon: String, meal: String, onClick: () -> Unit) {
    // Usamos clickable en la Card directamente para una mejor UX
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Diseño plano moderno
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono con contenedor circular
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(icon, fontSize = 28.sp)
                }
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = meal,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}