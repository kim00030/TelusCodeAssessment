package com.example.teluscodeassesmentfromdankim.presentation.navgraph

/**
 * Author: Dan Kim
 *
 * Defines a type-safe navigation route system using a sealed interface and Kotlin serialization.
 *
 */
interface ScreenRoute {
    @kotlinx.serialization.Serializable
    data object Home : ScreenRoute

    @kotlinx.serialization.Serializable
    data class Details(val id: Int) : ScreenRoute
}