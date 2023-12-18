package com.idat.clinicasalud

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idat.clinicasalud.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitasListActivity  : ComponentActivity(){

    private lateinit var addSpecialtyFAB: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    private lateinit var searchEditText: EditText
    private lateinit var adapter: SpecialtyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitivity_citas_list)

        recyclerView = findViewById(R.id.specialtyRecyclerView)
        addSpecialtyFAB = findViewById(R.id.fab_add_specialty)

        // Configurar el evento de clic para abrir la actividad de formulario
        addSpecialtyFAB.setOnClickListener {
            //val intent = Intent(this, SpecialtyActivity::class.java)
           // startActivity(intent)
        }

        obtenerCitas(this)


    }

    fun obtenerCitas(context: Context){
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getCitas()
            if(response.isSuccessful) {
                val citasBody = response.body()
                if(citasBody != null){
                    val citas = citasBody.toMutableList();
                    withContext(Dispatchers.Main) {
                        adapter = SpecialtyAdapter(context, citas)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(context)
                    }

                }

                // TODO: use users list with the RecyclerView
            } else {
                // TODO: handle error
            }
        }
    }

}