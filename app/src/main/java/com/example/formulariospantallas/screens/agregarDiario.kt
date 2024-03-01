package com.example.formulariospantallas.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.formulariospantallas.modelo.Diario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun agregarDiario(navController: NavController) {
    val fechaActual = remember { mutableStateOf(LocalDateTime.now()) }
    val formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    val fechaFormateada = fechaActual.value.format(formato)
    val textoDiario = remember { mutableStateOf("") }
    val diarios = remember { mutableStateListOf<Diario>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para ingresar el texto diario
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(4, 104, 249, 255),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(4, 104, 249, 255),
                unfocusedLabelColor = Color.Gray
            ),
            value = textoDiario.value,
            onValueChange = { textoDiario.value = it },
            label = { Text("Texto diario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val db = FirebaseFirestore.getInstance()
                val user = FirebaseAuth.getInstance().currentUser
                // Obtener el último ID insertado
                db.collection("usuarios").document(user!!.uid).collection("diario")
                    .orderBy("idDia", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .addOnSuccessListener { documents ->
                        var ultimoId = 0
                        for (document in documents) {
                            ultimoId = document.getLong("idDia")!!.toInt()
                        }

                        // Sumar 1 al último ID insertado
                        val nuevoId = ultimoId + 1

                        // Crear el diario con el ID personalizado
                        val diario = hashMapOf(
                            "idDia" to nuevoId,
                            "texto" to textoDiario.value,
                            "fecha" to fechaFormateada
                        )

                        // Agregar el diario a la base de datos
                        db.collection("usuarios").document(user.uid).collection("diario").document(nuevoId.toString()).set(diario)
                            .addOnSuccessListener {
                                textoDiario.value = ""
                                // Agregar la entrada al array diarios
                                diarios.add(Diario(nuevoId, textoDiario.value, fechaFormateada))
                                Log.d("Comprobacion", "Agregando diario a ${user.email}")
                                navController.navigate("portadaAplicacion")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Comprobacion", "Error al agregar diario", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Comprobacion", "Error al obtener el último ID", e)
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(Color(4, 104, 249, 255)),
        ) {
            Text("Agregar")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

