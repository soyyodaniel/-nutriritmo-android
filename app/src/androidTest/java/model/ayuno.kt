// model/Ayuno.kt
data class Ayuno(
    val id_ayuno: Int,
    val inicio_timestamp: Long,
    val fin_timestamp: Long?,
    val duracion_horas: Double?,
    val estado: String
)