package com.jonnesten.lullanap.bottomnav
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon:ImageVector, var screen_route:String){

    object Home : BottomNavItem("Home", Icons.Rounded.Home,"home")
    object History: BottomNavItem("History", Icons.Rounded.CalendarToday,"history")
    object Knowledge: BottomNavItem("Knowledge", Icons.Rounded.School,"knowledge")
    object Settings: BottomNavItem("Settings", Icons.Rounded.Settings, "settings")
    object Scanning: BottomNavItem("Scanning", Icons.Outlined.Lightbulb, "scanning")
    object Results: BottomNavItem("Results", Icons.Outlined.Lightbulb, "results")
}