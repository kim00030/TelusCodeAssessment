package com.example.teluscodeassesmentfromdankim.presentation.navgraph

/**
 * Author: Dan Kim
 */
interface ScreenRoute {
    @kotlinx.serialization.Serializable
    data object Home : ScreenRoute

    @kotlinx.serialization.Serializable
    data class Details(val id: Int) : ScreenRoute
}