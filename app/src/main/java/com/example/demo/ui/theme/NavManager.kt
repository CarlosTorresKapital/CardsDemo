package com.example.demo.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.DragScreen
import com.example.demo.StackedCards
import com.example.demo.ui.login.LoginScreen

@Composable
fun NavManager() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Drag"
    ){

        composable (
            route = "Cards"
        ) {
            StackedCards(
                navToDrag = {
                    navController.navigate("Drag")
                }
            )
        }

        composable(
            route = "Drag"
        ) {
            DragScreen()
        }

        composable(
            route = "Login"
        ){
            LoginScreen()
        }

    }

}