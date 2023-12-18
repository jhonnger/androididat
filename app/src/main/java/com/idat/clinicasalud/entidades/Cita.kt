package com.idat.clinicasalud.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cita(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "medicoId") val medicoId: Long,
    @ColumnInfo(name = "medicoNombre") val medico: String,
    @ColumnInfo(name = "estadoCita") val estadoCita: String,
    @ColumnInfo(name = "horaCita") val horaCita: String
)