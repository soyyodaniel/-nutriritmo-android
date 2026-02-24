package com.example.nutriritmo.model

import com.google.gson.annotations.SerializedName

data class AyunoDto(
    @SerializedName("id_ayuno") val idAyuno: Int,
    @SerializedName("inicio_timestamp") val inicioTimestamp: Long,
    @SerializedName("fin_timestamp") val finTimestamp: Long?,
    @SerializedName("duracion_horas") val duracionHoras: Double?,
    @SerializedName("estado") val estado: String
)
