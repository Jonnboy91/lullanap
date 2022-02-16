package com.jonnesten.lullanap.bottomnav

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonnesten.lullanap.R
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
    ) {
        Text(
            text = "Place your phone close to the sleeping area and start scanning",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp) .padding(bottom = 100.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        OutlinedButton(
            onClick = {  },
            border = BorderStroke(1.dp, Color.Red),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
            modifier = Modifier .align(Alignment.CenterHorizontally) .width(150.dp) .height(150.dp)
        ){
            Text( text = "Start" )
        }
    }
}

@Composable
fun reviewIcon() {
    Icon(
        Icons.Filled.Favorite,
        contentDescription = "Favorite",
    )
}

@Composable
fun dayDetails(review: Int, day: String) {
    Text(
        text = day,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        fontSize = 20.sp
    )
    Text(
        text = "Light 100 Lux",
        textAlign = TextAlign.Left,
        fontSize = 20.sp
    )
    Text(
        text = "Temperature 20 Celsius",
        textAlign = TextAlign.Left,
        fontSize = 20.sp
    )
    Text(
        text = "Noise 55 dB",
        textAlign = TextAlign.Left,
        fontSize = 20.sp
    )
    Row (modifier = Modifier .padding(bottom = 16.dp)){
        for (i in 1..review){
            reviewIcon()
        }
    }
}

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        dayDetails(3, "Last night")
        dayDetails(5, "Wednesday")
        dayDetails(1, "Tuesday")
    }
}

@Composable
fun KnowledgeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = Modifier .align(Alignment.CenterHorizontally) .padding(top = 32.dp),
        )
        Text(
            text = "Ideal: 80-120 Lux",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally) .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor imperdiet id non lobortis amet pellentesque consequat. Tortor ut sed congue molestie et lorem. Sit malesuada orci, metus, lectus bibendum. Pretium, aliquam donec aliquam velit amet, magna eu, pellentesque viverra. Eu interdum duis aliquet tortor. Dictum ultrices id dictum a neque, tristique. Adipiscing odio tortor quis nam nibh cursus tellus nec. Etiam tempor elit sed mattis vitae feugiat. Luctus eros, fames orci egestas pretium.",
            modifier = Modifier.align(Alignment.CenterHorizontally) .padding(bottom = 120.dp, start = 32.dp, end = 32.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = Modifier .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Ideal: 19 - 21 Celsius",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally) .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor imperdiet id non lobortis amet pellentesque consequat. Tortor ut sed congue molestie et lorem. Sit malesuada orci, metus, lectus bibendum. Pretium, aliquam donec aliquam velit amet, magna eu, pellentesque viverra. Eu interdum duis aliquet tortor. Dictum ultrices id dictum a neque, tristique. Adipiscing odio tortor quis nam nibh cursus tellus nec. Etiam tempor elit sed mattis vitae feugiat. Luctus eros, fames orci egestas pretium.",
            modifier = Modifier.align(Alignment.CenterHorizontally) .padding(bottom = 120.dp, start = 32.dp, end = 32.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = Modifier .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Ideal: 50 - 60 dB",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally) .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor imperdiet id non lobortis amet pellentesque consequat. Tortor ut sed congue molestie et lorem. Sit malesuada orci, metus, lectus bibendum. Pretium, aliquam donec aliquam velit amet, magna eu, pellentesque viverra. Eu interdum duis aliquet tortor. Dictum ultrices id dictum a neque, tristique. Adipiscing odio tortor quis nam nibh cursus tellus nec. Etiam tempor elit sed mattis vitae feugiat. Luctus eros, fames orci egestas pretium.",
            modifier = Modifier.align(Alignment.CenterHorizontally) .padding(bottom = 120.dp, start = 32.dp, end = 32.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SettingsScreen() {
    val darkThemeBoolean = isSystemInDarkTheme()
    val darkModeState = remember { mutableStateOf(darkThemeBoolean) }
    val isFahrenheit = remember { mutableStateOf(false)}
    val notificationsState = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier .padding(32.dp)) { Checkbox(
            checked = darkModeState.value,
            onCheckedChange = { darkModeState.value = it }
        )
        Text("Dark mode", modifier = Modifier .padding(start = 16.dp))
        }
        Row (modifier = Modifier .padding(32.dp)){ Checkbox(
            checked = notificationsState.value,
            onCheckedChange = { notificationsState.value = it }
        )
            Text("Notifications", modifier = Modifier .padding(start = 16.dp))
        }
        Row (modifier = Modifier .padding(32.dp)){ Checkbox(
            checked = isFahrenheit.value,
            onCheckedChange = { isFahrenheit.value = it }
        )
            Text("Show in Fahrenheit", modifier = Modifier .padding(start = 16.dp))
        }

    }

}