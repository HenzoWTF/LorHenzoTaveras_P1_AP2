package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun LorHenzoTaveras_P1_AP2NavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.ListScreen
    ) {
        composable<Screen.ListScreen> {
            Button(
                onClick = { navHostController.navigate(Screen.RegistroScreen(0)) }
            ) {
                Text("Primera ventana")
            }
        }
        composable<Screen.RegistroScreen> {
            Button(
                onClick = {navHostController.navigate(Screen.ListScreen)}
            ) {
                Text("Segunda pantalla")
            }
        }
    }
}