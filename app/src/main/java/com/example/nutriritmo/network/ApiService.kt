package com.example.nutriritmo.network

import com.example.nutriritmo.model.AyunoDto
import com.example.nutriritmo.model.DetenerAyunoRequest
import com.example.nutriritmo.model.IniciarAyunoRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("api/ayunos/{idUsuario}")
    suspend fun obtenerAyunos(@Path("idUsuario") idUsuario: Int): List<AyunoDto>

    @POST("api/ayunos/iniciar")
    suspend fun iniciarAyuno(@Body body: IniciarAyunoRequest): Map<String, Any>

    @POST("api/ayunos/detener")
    suspend fun detenerAyuno(@Body body: DetenerAyunoRequest): Map<String, Any>
}
