package com.example.formulariospantallas.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formulariospantallas.screens.PantallaBotones
import com.example.formulariospantallas.screens.agregarDiario
import com.example.formulariospantallas.screens.login
import com.example.formulariospantallas.screens.portadaAPP
import com.example.formulariospantallas.screens.registro

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation () {
    var navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.PantallaBotones.ruta){
        composable(AppScreens.PantallaBotones.ruta) { PantallaBotones(navigationController)}
        composable(AppScreens.registro.ruta) { registro(navigationController)}
        composable(AppScreens.Login.ruta) { login(navigationController) }
        composable(AppScreens.portadaAplicacion.ruta) { portadaAPP(navigationController)}
        composable(AppScreens.agregarDiario.ruta) { agregarDiario(navigationController) }
    }
}

