package edu.ucne.lorhenzotaveras_p1_ap2.presentacion.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ListScreen : Screen()

    @Serializable
    data class RegistroScreen(val Id: Int) : Screen()
}