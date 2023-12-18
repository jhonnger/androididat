package com.idat.clinicasalud

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegistrarActivity : ComponentActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var register_button: Button
    private lateinit var email_edittext: EditText
    private lateinit var password_edittext: EditText
    private lateinit var nombre_edittext: EditText
    private lateinit var apellido_edittext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        register_button = findViewById(R.id.btnRegistrar)
        email_edittext = findViewById(R.id.email)
        password_edittext = findViewById(R.id.txtPassword)
        nombre_edittext = findViewById(R.id.txtNombre)
        apellido_edittext = findViewById(R.id.txtApellido)

        val goToLogin = findViewById<TextView>(R.id.loginRegresarBtn)
        goToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        register_button.setOnClickListener {
            val email = email_edittext.text.toString()
            val password = password_edittext.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        // Crear un nuevo usuario con campos adicionales
                        val nuevoUsuario = hashMapOf(
                            "nombre" to nombre_edittext.text.toString(),
                            "apellido" to apellido_edittext.text.toString()
                        )

                        db.collection("users")
                            .document(user?.uid.toString())
                            .set(nuevoUsuario)
                            .addOnSuccessListener {
                                Log.d(TAG, "Usuario creado correctamente")
                                backToPreviousActivity()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error al agregar el documento", e)
                            }

                        // actualizar UI utilizando la función updateUI(user)

                    } else {
                        // Si el registro falla, muestra un mensaje al usuario.
                        Toast.makeText(baseContext, "Falló el registro.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun backToPreviousActivity() {
        this.finish()
    }

}