package com.idat.clinicasalud
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : ComponentActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var mapaButton: Button
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarFirebase()
        setContentView(R.layout.activity_login)


        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        val goToRegister = findViewById<TextView>(R.id.registroBtn)
        loginButton = findViewById(R.id.loginButton)
        mapaButton = findViewById(R.id.btnMapa)
        auth = FirebaseAuth.getInstance()



        goToRegister.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            ProgressDialogUtil.showProgressDialog(this)
            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task ->
                    ProgressDialogUtil.hideProgressDialog()
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(this, CitasListActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Si el ingreso falla, muestro un mensaje al usuario.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }


        mapaButton.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)

        }


    }


    fun inicializarFirebase() {
        FirebaseApp.initializeApp(this)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference()
    }


    private fun validacion() {
        val dniInput = usernameEditText.text.toString()
        val passwordInput = passwordEditText.text.toString()

        if (dniInput == "") {
            usernameEditText.error = "Required"
        }

        if (passwordInput == "") {
            passwordEditText.error = "Required"
        }
    }






}