package com.idat.clinicasalud

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idat.clinicasalud.config.AppDatabase
import com.idat.clinicasalud.dtos.CitaDto
import com.idat.clinicasalud.entidades.Cita
import com.idat.clinicasalud.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitasListActivity  : ComponentActivity(){

    private lateinit var addSpecialtyFAB: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: SpecialtyAdapter
    private lateinit var db: AppDatabase ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitivity_citas_list)

        recyclerView = findViewById(R.id.specialtyRecyclerView)
        addSpecialtyFAB = findViewById(R.id.fab_add_specialty)

        addSpecialtyFAB.setOnClickListener {
            //val intent = Intent(this, SpecialtyActivity::class.java)
           // startActivity(intent)
        }
        db = AppDatabase.getDatabase(applicationContext)
        obtenerCitas(this)

    }


    fun obtenerCitas(context: Context){
        ProgressDialogUtil.showProgressDialog(this)
        lifecycleScope.launch(Dispatchers.IO) {
            try {

                val response = RetrofitInstance.api.getCitas()
                if(response.isSuccessful) {
                    val citasBody = response.body()
                    ProgressDialogUtil.hideProgressDialog()
                    if(citasBody != null){
                        guardarMisCitas(citasBody)
                        val citas = citasBody.toMutableList();
                        withContext(Dispatchers.Main) {
                            adapter = SpecialtyAdapter(context, citas)
                            recyclerView.adapter = adapter
                            recyclerView.layoutManager = LinearLayoutManager(context)
                        }
                    }
                }
            }catch (e: Exception){
                ProgressDialogUtil.hideProgressDialog()
                val citas = listarCitasDeBDLocal();

                withContext(Dispatchers.Main) {
                    adapter = SpecialtyAdapter(context, citas.toMutableList())
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                }
            }

        }
    }

    fun listarCitasDeBDLocal(): List<CitaDto>{
        val citas: List<Cita> = db.citaDao().getAll()


        println("oteniendo "+ citas.size)
        return citas.map { mapToDTO(it) }

    }

    fun mapToDTO(cita: Cita): CitaDto {
        return CitaDto(
            id = cita.id,
            medicoNombre = cita.medico,
            horaCita = cita.horaCita,
            medicoId = cita.medicoId,
            estadoCita = cita.estadoCita
        )
    }
    fun mapfromDTO(cita: CitaDto): Cita {
        return Cita(
            id = cita.id,
            medico = cita.medicoNombre,
            horaCita = cita.horaCita,
            medicoId = cita.medicoId,
            estadoCita = cita.estadoCita
        )
    }

    fun guardarMisCitas( citasDto: List<CitaDto>) {

        // Primero borramos las citas previas

        println("guardando "+ citasDto.size)
        lifecycleScope.launch(Dispatchers.IO) {
            db.citaDao().deleteAll()
            val citas: List<Cita> = citasDto.map { mapfromDTO(it) }
            db.citaDao().insertAll(*citas.toTypedArray())
        }


    }

}