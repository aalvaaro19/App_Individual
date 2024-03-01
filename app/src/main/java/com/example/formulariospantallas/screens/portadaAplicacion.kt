package com.example.formulariospantallas.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.formulariospantallas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.TextStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun portadaAPP(navController: NavController) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val diariosTexto = remember { mutableListOf<String>() }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(4, 104, 249, 255),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Bienvenido a tu diario personal")
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Espaciado inicial
                        Spacer(modifier = Modifier.weight(1f))

                        // Icono Home
                        BottomNavigationItem(
                            selected = false,
                            onClick = {navController.navigate("portadaAplicacion")},
                            modifier = Modifier.weight(1f),
                            icon = {
                                Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color.White)

                            },
                        )
                        if (isDialogOpen) {
                            AlertDialog(
                                onDismissRequest = { isDialogOpen = false },
                                buttons = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Button(
                                            onClick = { isDialogOpen = false },
                                            modifier = Modifier.padding(8.dp)
                                        ) {
                                            Text(text = "Cerrar")
                                        }
                                    }
                                },
                                text = {
                                    val calendar = LocalDate.now()
                                    val month = calendar.month.getDisplayName(TextStyle.FULL, java.util.Locale.ENGLISH)
                                    Text(text = "El mes actual es: $month")
                                }
                            )
                        }

                        // Espaciado entre iconos
                        Spacer(modifier = Modifier.weight(2.5f))

                        // Icono Search
                        BottomNavigationItem(
                            selected = false,
                            onClick = {isDialogOpen = true},
                            modifier = Modifier.weight(1f),
                            icon = {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Search", tint = Color.White)
                            },
                        )

                        // Espaciado entre iconos
                        Spacer(modifier = Modifier.weight(2.5f))

                        // Icono Notifications
                        BottomNavigationItem(
                            selected = false,
                            onClick = {},
                            modifier = Modifier.weight(1f),
                            icon = {
                                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                            },
                        )

                        // Espaciado entre iconos
                        Spacer(modifier = Modifier.weight(2.5f))

                        // Icono Person
                        BottomNavigationItem(
                            selected = false,
                            onClick = { logoutAuth(navController) },
                            modifier = Modifier.weight(0.7f),
                            icon = {
                                Icon(painterResource(R.drawable.image__5_), contentDescription = "Profile", tint = Color.White)
                            },
                        )

                        // Espaciado final
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                backgroundColor = Color(4, 104, 249, 255)
            )
        }
    ) {
        LaunchedEffect(Unit) {
            val diariosSnapshot = obtenerDiariosUser()
            diariosSnapshot?.forEach { diario ->
                val texto = diario.getString("texto") ?: ""
                val fecha = diario.getString("fecha") ?: ""
                diariosTexto.add("$texto $fecha")
                Log.d("DiarioDef2", "Fecha: $fecha, Texto: $texto")
            }
        }
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp, 63.dp, 20.dp, 210.dp)
        ){
            if (diariosTexto.isEmpty()) {
                item{
                    Log.d("DiarioDef", diariosTexto.toString())
                    Text(
                        text = "Empieza a escribir un diario",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(20.dp, 300.dp, 0.dp, 0.dp)
                    )
                    Text(text = "Crea tu diario personal", fontSize = 16.sp, modifier = Modifier.padding(110.dp, 5.dp, 0.dp, 0.dp))
                    Text(text = "Toca el botón del signo mas para empezar", fontSize = 15.sp, modifier = Modifier.padding(45.dp, 5.dp, 0.dp, 0.dp))
                }
            }else {
                items(diariosTexto) { texto ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 10.dp,
                                    bottomStart = 10.dp,
                                    bottomEnd = 10.dp
                                )
                            )

                    ){
                        Text(text = texto, fontSize = 15.sp, modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp))
                    }
                }
            }
        }
        Clickable(
            onClick = {
                navController.navigate("agregarDiario")
            },
        )
    }
}

@Composable
private fun Clickable(onClick: () -> Unit){
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(1000.dp)
            .padding(0.dp, 575.dp, 0.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.mas2_removebg_preview),
            contentDescription = "Imagen de portada"
        )
    }
}

fun logoutAuth(navController: NavController) {
    FirebaseAuth.getInstance().signOut()
    navController.navigate("PantallaBotones")
}

suspend fun obtenerDiariosUser(): QuerySnapshot? {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    val diarios = db.collection("usuarios").document(user!!.uid).collection("diario").get().await()
    for (diario in diarios) {
        Log.d("Diario", diario.data.toString())
    }
    return diarios
}
