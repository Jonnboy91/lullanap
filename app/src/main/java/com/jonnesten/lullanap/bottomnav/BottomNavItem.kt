package com.jonnesten.lullanap.bottomnav

import com.jonnesten.lullanap.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object History: BottomNavItem("History", R.drawable.ic_my_network,"history")
    object Knowledge: BottomNavItem("Knowledge", R.drawable.ic_post,"knowledge")
    object Settings: BottomNavItem("Settings", R.drawable.ic_post, "settings")
    object Scanning: BottomNavItem("Scanning", R.drawable.ic_post, "scanning")
    object Results: BottomNavItem("Results", R.drawable.ic_post, "results")
}