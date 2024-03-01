package com.example.formulariospantallas.navigation

sealed class AppScreens (val ruta: String){
    object PantallaBotones : AppScreens("PantallaBotones")
    object Login : AppScreens("Login")
    object registro : AppScreens("registro")
    object portadaAplicacion : AppScreens("portadaAplicacion")
    object agregarDiario : AppScreens("agregarDiario")
}