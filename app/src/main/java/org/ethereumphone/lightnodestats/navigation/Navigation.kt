package org.ethereumphone.lightnodestats.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ethereumphone.lightnodestats.ui.MainStatsScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController=navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(route = Screen.MainScreen.route){
            //MainStatsScreen()
        }
    }
}