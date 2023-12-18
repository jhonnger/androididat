package com.idat.clinicasalud.dtos

data class Cita (
    val medicoNombre: String,
    val medicoId: Long,
    val horaCita: String,
    val estadoCita: String
)