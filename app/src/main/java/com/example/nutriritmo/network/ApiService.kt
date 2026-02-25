package com.example.nutriritmo.network

import com.example.nutriritmo.model.AyunoDto
import com.example.nutriritmo.model.DetenerAyunoRequest
import com.example.nutriritmo.model.IniciarAyunoRequest
import com.example.nutriritmo.model.LoginRequest
import com.example.nutriritmo.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.nutriritmo.model.RegisterRequest
import com.example.nutriritmo.model.RegisterResponse

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): retrofit2.Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest): retrofit2.Response<RegisterResponse>

    @GET("api/ayunos/{idUsuario}")
    suspend fun obtenerAyunos(@Path("idUsuario") idUsuario: Int): List<AyunoDto>
    @GET("ping")
    suspend fun ping(): retrofit2.Response<Map<String, Any>>
    @POST("api/ayunos/iniciar")
    suspend fun iniciarAyuno(@Body body: IniciarAyunoRequest): Map<String, Any>

    @POST("api/ayunos/detener")
    suspend fun detenerAyuno(@Body body: DetenerAyunoRequest): Map<String, Any>
}
