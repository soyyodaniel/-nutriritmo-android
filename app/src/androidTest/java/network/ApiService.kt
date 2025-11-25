package com.example.nutriritmo.data.remote

import com.example.nutriritmo.model.Ayuno
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // Obtener todos los ayunos de un usuario
    @GET("api/ayunos/{idUsuario}")
    fun getAyunos(@Path("idUsuario") idUsuario: Int): Call<List<Ayuno>>

    // Iniciar un nuevo ayuno
    @POST("api/ayunos/iniciar")
    fun iniciarAyuno(@Body ayuno: Ayuno): Call<Ayuno>

    // Detener un ayuno
    @POST("api/ayunos/detener")
    fun detenerAyuno(@Body ayuno: Ayuno): Call<Ayuno>
}

