package com.example.nutriritmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nutriritmo.ui.NutriNav
import com.example.nutriritmo.ui.theme.NutriRitmoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NutriRitmoTheme {
                NutriNav(idUsuario = 1) // 👈 por ahora usuario 1
            }
        }
    }
}
