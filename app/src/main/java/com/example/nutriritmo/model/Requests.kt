package com.example.nutriritmo.model

data class IniciarAyunoRequest(
    val id_usuario: Int,
    val inicio_timestamp: Long
)

data class DetenerAyunoRequest(
    val id_ayuno: Int,
    val fin_timestamp: Long,
    val duracion_horas: Double
)
