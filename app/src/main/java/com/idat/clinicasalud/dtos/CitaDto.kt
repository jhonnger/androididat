package com.idat.clinicasalud.dtos

data class CitaDto (
    val medicoNombre: String,
    val id: Long,
    val medicoId: Long,
    val horaCita: String,
    val estadoCita: String
)