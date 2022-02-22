package com.jonnesten.lullanap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jonnesten.lullanap.bottomnav.BottomNavigation
import com.jonnesten.lullanap.bottomnav.NavigationGraph
import com.jonnesten.lullanap.ui.theme.LullaNapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LullaNapTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView(){
    Box {
        val isLightTheme = MaterialTheme.colors.isLight
        Image(
            painterResource(if (isLightTheme) R.drawable.lulla_bg_light else R.drawable.lulla_bg_dark),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )
        val navController = rememberNavController()
        Scaffold(
            backgroundColor = Color.Transparent,
            bottomBar = { BottomNavigation(navController = navController) }
        ) {

            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LullaNapTheme {
        Greeting("Android")
    }
}