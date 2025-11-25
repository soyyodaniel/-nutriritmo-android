package com.example.nutriritmo.data.repository

import com.example.nutriritmo.data.remote.ApiService
import com.example.nutriritmo.model.Ayuno
import retrofit2.Call

class AyunoRepository(private val apiService: ApiService) {

    // Función para iniciar un nuevo ayuno
    fun iniciarAyuno(ayuno: Ayuno): Call<Ayuno> {
        return apiService.iniciarAyuno(ayuno)
    }

    // Función para obtener los ayunos de un usuario
    fun obtenerAyunos(idUsuario: Int): Call<List<Ayuno>> {
        return apiService.getAyunos(idUsuario)
    }

    // Función para detener un ayuno
    fun detenerAyuno(ayuno: Ayuno): Call<Ayuno> {
        return apiService.detenerAyuno(ayuno)
    }
}
