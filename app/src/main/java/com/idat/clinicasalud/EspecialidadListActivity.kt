package com.idat.clinicasalud

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EspecialidadListActivity  : ComponentActivity(){

    private lateinit var addSpecialtyFAB: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    private lateinit var searchEditText: EditText
    private lateinit var adapter: SpecialtyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitivity_especialidad_list)

        searchEditText = findViewById(R.id.searchEditText)
        recyclerView = findViewById(R.id.specialtyRecyclerView)
        addSpecialtyFAB = findViewById(R.id.fab_add_specialty)

        // Configurar el evento de clic para abrir la actividad de formulario
        addSpecialtyFAB.setOnClickListener {
            //val intent = Intent(this, SpecialtyActivity::class.java)
           // startActivity(intent)
        }

        val specialties = mutableListOf("Especialidad 1", "Especialidad 2") // Ejemplo de datos
        adapter = SpecialtyAdapter(this, specialties)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

}