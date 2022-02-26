package com.jonnesten.lullanap.bottomnav

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonnesten.lullanap.SensorViewModel
import com.jonnesten.lullanap.SoundMeter
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.jonnesten.lullanap.R

@Composable
fun HomeScreen(tempValue: Float?, lightValue: Float?, SensorViewModel: SensorViewModel) {
    var temp by remember { mutableStateOf("") }
    var light by remember { mutableStateOf("") }
    var addTemperature by remember { mutableStateOf(SensorViewModel.hasTempSensor.value != true) } // You can test what the alertDialog would look like, by setting this != false, so that it is showing it if you have a tempSensor (as emulators do)
    var addLight by remember { mutableStateOf(SensorViewModel.hasLightSensor.value != true) }
    // Can't be tested with Emulator, but this should be pretty much how we can get the audio recorded for a while and then used to get the dB.
    /*val soundMeter = SoundMeter()
    soundMeter.start()
    Handler().postDelayed({
        Log.d("SoundDB", "Calling stop in 5sec so has time to get the dB")
        soundMeter.stop()
    },
     5000
    )

    var dB: Double = 20 * Math.log10(soundMeter.getAmplitude() / 32767);
    Log.d("SoundDB", "value is $dB")*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
    ) {
        Text(
            text = stringResource(R.string.home_title),
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    bottom = 100.dp,
                    start = 20.dp,
                    end = 20.dp,
                ),
            textAlign = TextAlign.Center,
            fontSize = 34.sp,
        )
        val haptic = LocalHapticFeedback.current
        OutlinedButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                SensorViewModel.isClicked(true)
                Log.d("scanValues:", "${"Show the value that is $tempValue"}, ${SensorViewModel.clicked.value}")
                Log.d("scanValues:", "${"Show the value that is $lightValue"}, ${SensorViewModel.clicked.value}")
                },
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.secondary),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .height(200.dp)
        ){
            Text(
                text = stringResource(R.string.scan_button),
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }
        // TODO This AlertDialog should pop up only after clicking scanning and you would be on the scanning screen
        if(addTemperature) {
            AlertDialog(
                onDismissRequest = {
                    Log.d("addTemp", "YOU NEED TO ADD TEMPERATURE")
                },
                title = {
                    Text("No temperature sensor was detected, so add the temperature of the room manually, before scanning :) ")
                },
                text = {
                    TextField(
                        value = temp,
                        onValueChange = { temp = it },
                        label = { Text("Label") },
                        keyboardOptions = KeyboardOptions(keyboardType =
                        KeyboardType.Number)
                    )
                },
                buttons = {
                    OutlinedButton(
                        onClick = {
                            val value = temp.toFloat()
                            SensorViewModel.updateValue(value)
                            addTemperature = false
                        }
                    ){
                        Text( text = "Add temp" )
                    }
                }
            )
        }
        // TODO This AlertDialog should pop up only after clicking scanning and you would be on the scanning screen
        // TODO: Probably DELETE, since this is probably not needed and to be honest, who would know lux value anyway, so if no light sensor then that is just not available for that user.
        if(addLight) {
            AlertDialog(
                onDismissRequest = {
                    addTemperature = false
                },
                title = {
                    Text("No Light sensor was detected, so add the Lux value of the room manually, before scanning :) ")
                },
                text = {
                    TextField(
                        value = light,
                        onValueChange = { light = it },
                        label = { Text("Label") },
                        keyboardOptions = KeyboardOptions(keyboardType =
                        KeyboardType.Number)
                    )
                },
                buttons = {
                    OutlinedButton(
                        onClick = {
                            val value = light.toFloat()
                            SensorViewModel.updateValue(value)
                            addLight = false
                        }
                    ){
                        Text( text = "Add lux" )
                    }
                }
            )
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
        )
        Text(
            text = "Ideal: 80-120 Lux",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor imperdiet id non lobortis amet pellentesque consequat. Tortor ut sed congue molestie et lorem. Sit malesuada orci, metus, lectus bibendum. Pretium, aliquam donec aliquam velit amet, magna eu, pellentesque viverra. Eu interdum duis aliquet tortor. Dictum ultrices id dictum a neque, tristique. Adipiscing odio tortor quis nam nibh cursus tellus nec. Etiam tempor elit sed mattis vitae feugiat. Luctus eros, fames orci egestas pretium.",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 120.dp, start = 32.dp, end = 32.dp),
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor imperdiet id non lobortis amet pellentesque consequat. Tortor ut sed congue molestie et lorem. Sit malesuada orci, metus, lectus bibendum. Pretium, aliquam donec aliquam velit amet, magna eu, pellentesque viverra. Eu interdum duis aliquet tortor. Dictum ultrices id dictum a neque, tristique. Adipiscing odio tortor quis nam nibh cursus tellus nec. Etiam tempor elit sed mattis vitae feugiat. Luctus eros, fames orci egestas pretium.",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 120.dp, start = 32.dp, end = 32.dp),
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam porttitor imperdiet id non lobortis amet pellentesque consequat. Tortor ut sed congue molestie et lorem. Sit malesuada orci, metus, lectus bibendum. Pretium, aliquam donec aliquam velit amet, magna eu, pellentesque viverra. Eu interdum duis aliquet tortor. Dictum ultrices id dictum a neque, tristique. Adipiscing odio tortor quis nam nibh cursus tellus nec. Etiam tempor elit sed mattis vitae feugiat. Luctus eros, fames orci egestas pretium.",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 120.dp, start = 32.dp, end = 32.dp),
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