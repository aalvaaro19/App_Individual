package com.example.formulariospantallas.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.formulariospantallas.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registro (navController: NavController) {
    val image1 = painterResource(R.drawable.visibility)
    val image2 = painterResource(R.drawable.visibilityoff)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(4, 104, 249, 255),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Insertar usuario")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("PantallaBotones") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding() - 40.dp,
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var nombreCompleto by remember { mutableStateOf("") }
            val updatedNombreCompleto = rememberUpdatedState(nombreCompleto)

            Row {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(4, 104, 249, 255),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(4, 104, 249, 255),
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    value = updatedNombreCompleto.value,
                    onValueChange = { nombreCompleto = it },
                    singleLine = true,
                    label = {
                        Row {
                            if (nombreCompleto.isEmpty()) {
                                Icon(
                                    imageVector = if (nombreCompleto.isNotEmpty()) Icons.Default.Person else Icons.Default.Person,
                                    contentDescription = "Person Icon",
                                    tint = if (nombreCompleto.isNotEmpty()) Color.Black else Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Nombre completo")
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            var Telefono by remember { mutableStateOf("") }
            val updatedTelefono = rememberUpdatedState(Telefono)
            Row {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(4, 104, 249, 255),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(4, 104, 249, 255),
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    value = updatedTelefono.value,
                    onValueChange = { if (it.all { char -> char.isDigit() }) { Telefono = it } },
                    singleLine = true,
                    label = {
                        Row {
                            if (Telefono.isEmpty()) {
                                Icon(
                                    imageVector = if (Telefono.isNotEmpty()) Icons.Default.LocationOn else Icons.Default.LocationOn,
                                    contentDescription = "Person Icon",
                                    tint = if (Telefono.isNotEmpty()) Color.Black else Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Número de telefono")
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            var nombre by remember { mutableStateOf("") }
            val updatedNombre = rememberUpdatedState(nombre)
            Row {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(4, 104, 249, 255),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(4, 104, 249, 255),
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    value = updatedNombre.value,
                    onValueChange = { nombre = it },
                    singleLine = true,
                    label = {
                        Row {
                            if (nombre.isEmpty()) {
                                Icon(
                                    imageVector = if (nombre.isNotEmpty()) Icons.Default.Person else Icons.Default.Person,
                                    contentDescription = "Person Icon",
                                    tint = if (nombre.isNotEmpty()) Color.Black else Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Nombre de usuario")
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            var correo by rememberSaveable { mutableStateOf("") }
            val updateCorreo = rememberUpdatedState(correo)
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(4, 104, 249, 255),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(4, 104, 249, 255),
                    unfocusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
                value = updateCorreo.value,
                onValueChange = { correo = it },
                singleLine = true,
                label = {
                    Row {
                        if (correo.isEmpty()) {
                            Icon(
                                imageVector = if (correo.isNotEmpty()) Icons.Default.Email else Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = if (correo.isNotEmpty()) Color.Black else Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Email")
                    }
                },
            )
            Spacer(modifier = Modifier.size(5.dp))
            var password by rememberSaveable { mutableStateOf("") }
            var isPasswordVisible by remember { mutableStateOf(false) }
            var isPasswordFocused by remember { mutableStateOf(false) }
            val updateContraseña = rememberUpdatedState(password)
            val iconSize = 20.dp

            // Se sugiere utilizar un nombre más descriptivo para la función de cambio de contraseña
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(4, 104, 249, 255),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(4, 104, 249, 255),
                    unfocusedLabelColor = Color.Gray
                ),
                value = updateContraseña.value,
                onValueChange = {
                    // Asigna el nuevo valor directamente a 'password'
                    password = it
                },
                singleLine = true,
                label = {
                    Row {
                        if (password.isEmpty()) {
                            Icon(
                                imageVector = if (password.isNotEmpty()) Icons.Default.Lock else Icons.Default.Lock,
                                contentDescription = "Lock Icon",
                                tint = if (password.isNotEmpty()) Color.Black else Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Contraseña")
                    }
                },
                visualTransformation = if (isPasswordVisible) {
                    // Usa VisualTransformation.None para mostrar el texto de la contraseña si es visible
                    VisualTransformation.None
                } else {
                    // Usa PasswordVisualTransformation para ocultar el texto de la contraseña si no es visible
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            // Invierte el estado de isPasswordVisible al hacer clic en el icono
                            isPasswordVisible = !isPasswordVisible
                        },
                    ) {
                        Image(
                            // Utiliza operador ternario para seleccionar la imagen en función de isPasswordVisible
                            if (isPasswordVisible) image2 else image1,
                            // Utiliza operador ternario para seleccionar la descripción de la imagen
                            contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            // Utiliza el modificador size directamente en Image
                            modifier = Modifier.size(iconSize)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    .onFocusChanged { isPasswordFocused = it.isFocused }
            )

            Button(
                onClick = {
                  registroAuth(correo, password, nombreCompleto, Telefono, nombre, navController)
                },
                modifier = Modifier
                    .size(200.dp, 75.dp)
                    .padding(top = 20.dp, start = 0.8.dp, end = 20.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color(4, 104, 249, 255))
            ) {
                Text("Enviar", fontSize = 20.sp)
            }
        }
    }
}

fun registroAuth(correo: String, contraseña: String, nombreCompleto: String, telefono: String, nombreUsuario: String, navController: NavController) {
    if (contraseña.length < 6) {
        Log.d("contraseñaError", "La contraseña debe tener al menos 6 caracteres")
        return
    }
    val auth = Firebase.auth
    auth.createUserWithEmailAndPassword(correo, contraseña)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // El usuario se ha registrado correctamente
                val user = auth.currentUser
                // Guardar información adicional en Firestore
                val db = FirebaseFirestore.getInstance()
                val collection = "usuarios"
                val userDocument = user?.let { db.collection(collection).document(it.uid) }
                val userData = hashMapOf(
                    "nombreCompleto" to nombreCompleto,
                    "telefono" to telefono,
                    "nombreUsuario" to nombreUsuario,
                    "correo" to correo,
                )
                userDocument?.set(userData)
                    ?.addOnSuccessListener {
                        navController.navigate(("login"))
                    }
                    ?.addOnFailureListener { e ->
                        navController.navigate(("login"))
                    }
            } else {
                // Ha ocurrido un error al registrar el usuario
                val error = task.exception
                // Puedes mostrar un mensaje de error al usuario
                Log.d("autenticacionError", "Error al registrar el usuario: $error")
                navController.navigate(("registro"))
            }
        }
}