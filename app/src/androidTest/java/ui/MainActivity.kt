package com.example.nutriritmo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.nutriritmo.R
import com.example.nutriritmo.viewmodel.AyunoViewModel
import com.example.nutriritmo.model.Ayuno
import com.example.nutriritmo.data.repository.AyunoRepository
import com.example.nutriritmo.network.RetrofitInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val ayunoViewModel: AyunoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Observando los ayunos
        ayunoViewModel.ayunos.observe(this, Observer { ayunos ->
            // Actualizar UI con los datos de los ayunos
        })

        // Llamada para obtener los ayunos de un usuario
        ayunoViewModel.obtenerAyunos(2)
    }
}