package com.example.nutriritmo.model
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserDto
)
data class UserDto(
    val id_usuario: Int,
    val nombre: String,
    val email: String
)

data class IniciarAyunoRequest(
    val id_usuario: Int,
    val inicio_timestamp: Long
)

data class DetenerAyunoRequest(
    val id_ayuno: Int,
    val fin_timestamp: Long,
    val duracion_horas: Double
)
