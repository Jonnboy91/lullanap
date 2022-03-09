package com.jonnesten.lullanap.bottomnav

import android.content.SharedPreferences
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(29.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                selectedContentColor = MaterialTheme.colors.secondaryVariant,
                unselectedContentColor = MaterialTheme.colors.onPrimary.copy(0.6f),
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
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