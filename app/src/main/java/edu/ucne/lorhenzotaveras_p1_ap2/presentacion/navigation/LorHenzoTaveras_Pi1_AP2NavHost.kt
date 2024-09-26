package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas.VentasListScreen
import edu.ucne.lorhenzotaveras_p1_ap2.presentacion.Ventas.VentasScreen

@Composable
fun LorHenzoTaveras_P1_AP2NavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.ListScreen
    ) {
        composable<Screen.ListScreen> {
            VentasListScreen(
                onAddVentas = {
                    navHostController.navigate(Screen.RegistroScreen(0))
                },
                onVentasClick = { Id ->
                    navHostController.navigate(Screen.RegistroScreen(Id = Id))
                }
            )
        }
        composable<Screen.RegistroScreen> {argumento ->
            val id = argumento.toRoute<Screen.RegistroScreen>().Id

            VentasScreen(
                Id = id,
                goVentasList = {
                    navHostController.navigate(
                        Screen.ListScreen
                    )
                }
            )
        }
    }
}