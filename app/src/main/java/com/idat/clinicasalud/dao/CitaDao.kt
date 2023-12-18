package com.idat.clinicasalud.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.idat.clinicasalud.entidades.Cita

@Dao
interface CitaDao {
    @Query("SELECT * FROM cita")
    fun getAll(): List<Cita>

    @Insert
    fun insertAll(vararg users: Cita)

    @Query("DELETE FROM cita")
    suspend fun deleteAll()
}