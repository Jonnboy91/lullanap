package com.jonnesten.lullanap.bottomnav

import android.content.SharedPreferences
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jonnesten.lullanap.SensorViewModel
import com.jonnesten.lullanap.screens.*

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Knowledge,
        BottomNavItem.Settings,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    sensorViewModel: SensorViewModel,
    sharedPreferences: SharedPreferences
) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(navController)
        }
        composable(BottomNavItem.History.screen_route) {
            HistoryScreen(sharedPreferences)
        }
        composable(BottomNavItem.Knowledge.screen_route) {
            KnowledgeScreen()
        }
        composable(BottomNavItem.Settings.screen_route) {
            SettingsScreen(sharedPreferences)
        }
        composable(BottomNavItem.Scanning.screen_route) {
            ScanningScreen(sensorViewModel, navController)
        }
        composable(BottomNavItem.Results.screen_route) {
            ResultsScreen(navController, sharedPreferences, sensorViewModel)
        }
    }
}