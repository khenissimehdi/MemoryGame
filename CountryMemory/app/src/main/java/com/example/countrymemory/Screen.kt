package com.example.countrymemory

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Game: Screen(route = "game/{number}")
}