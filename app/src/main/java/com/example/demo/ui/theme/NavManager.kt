package com.example.demo.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.DragScreen
import com.example.demo.StackedCards
import com.example.demo.ui.WalletScreen
import com.example.demo.ui.login.LoginScreen

@Composable
fun NavManager() {

    val navController = rememberNavController()

    Scaffold { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "Cards"
        ){

            composable (
                route = "Cards"
            ) {
                StackedCards(
                    navToDrag = {
                        navController.navigate("wallet")
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

            composable(
                route = "wallet"
            ){
                WalletScreen()
            }

        }
    }


}