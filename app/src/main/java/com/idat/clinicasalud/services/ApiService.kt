package com.idat.clinicasalud.services

import com.idat.clinicasalud.dtos.CitaDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("citas/listarMisCitas/1")
    suspend fun getCitas(): Response<List<CitaDto>>
}