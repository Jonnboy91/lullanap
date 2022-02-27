package com.jonnesten.lullanap.bottomnav

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Speaker
import androidx.compose.material.icons.outlined.Thermostat

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
                Log.d(
                    "scanValues:",
                    "${"Show the value that is $tempValue"}, ${SensorViewModel.clicked.value}"
                )
                Log.d(
                    "scanValues:",
                    "${"Show the value that is $lightValue"}, ${SensorViewModel.clicked.value}"
                )
            },
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.secondary),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .height(200.dp)
        ) {
            Text(
                text = stringResource(R.string.scan_button),
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }
        // TODO This AlertDialog should pop up only after clicking scanning and you would be on the scanning screen
        if (addTemperature) {
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType =
                            KeyboardType.Number
                        )
                    )
                },
                buttons = {
                    OutlinedButton(
                        onClick = {
                            val value = temp.toFloat()
                            SensorViewModel.updateValue(value)
                            addTemperature = false
                        }
                    ) {
                        Text(text = "Add temp")
                    }
                }
            )
        }
        // TODO This AlertDialog should pop up only after clicking scanning and you would be on the scanning screen
        // TODO: Probably DELETE, since this is probably not needed and to be honest, who would know lux value anyway, so if no light sensor then that is just not available for that user.
        if (addLight) {
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType =
                            KeyboardType.Number
                        )
                    )
                },
                buttons = {
                    OutlinedButton(
                        onClick = {
                            val value = light.toFloat()
                            SensorViewModel.updateValue(value)
                            addLight = false
                        }
                    ) {
                        Text(text = "Add lux")
                    }
                }
            )
        }

    }
}

@Composable
fun ReviewIcon(review: Int) {
    for (i in 1..5) {
        if (i <= review) {
            Icon(
                Icons.Outlined.Face,
                contentDescription = "Face",
                tint = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Icon(
                Icons.Outlined.Face,
                contentDescription = "Face",
                tint = Color.Transparent,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun DayDetails(review: Int, day: String) {
    // TODO Add correct values for measurements
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = day,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 26.sp,
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Elementum sed sollicitudin sit vulputate.",
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 18.sp,
                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 5.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.light),
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
                Text(
                    // TODO Add correct value before string resource
                    text = stringResource(R.string.lux),
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.temperature),
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
                Text(
                    // TODO Add correct value before string resource
                    text = stringResource(R.string.celsius),
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.noise),
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
                Text(
                    // TODO Add correct value before string resource
                    text = stringResource(R.string.db),
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ReviewIcon(review)
            }
        }
    }
}

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 50.dp, 0.dp, 120.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DayDetails(3, "Last night")
        DayDetails(5, "Wednesday")
        DayDetails(1, "Tuesday")
        DayDetails(3, "Monday")
        DayDetails(2, "Sunday")
    }
}

@Composable
fun KnowledgeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 80.dp, 20.dp, 80.dp)
    ) {
        Icon(
            Icons.Outlined.Lightbulb,
            contentDescription = "Light",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp),
            tint = MaterialTheme.colors.onPrimary,
        )
        Text(
            text = stringResource(R.string.ideal_lux),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )
        Text(
            text = stringResource(R.string.lux_desc),
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp
        )
        Icon(
            Icons.Outlined.Thermostat,
            contentDescription = "Temperature",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp),
            tint = MaterialTheme.colors.onPrimary,
        )
        Text(
            text = stringResource(R.string.ideal_temp),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )
        Text(
            text = stringResource(R.string.temp_desc),
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp
        )
        Icon(
            Icons.Outlined.Speaker,
            contentDescription = "Noise",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp),
            tint = MaterialTheme.colors.onPrimary,
        )
        Text(
            text = stringResource(R.string.ideal_db),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )
        Text(
            text = stringResource(R.string.db_desc),
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp
        )
    }
}

@Composable
// TODO Make clicking text a selector too and figure out CheckBox styling
fun SettingsScreen() {
    val darkThemeBoolean = isSystemInDarkTheme()
    val darkModeState = remember { mutableStateOf(darkThemeBoolean) }
    val isFahrenheit = remember { mutableStateOf(false) }
    val notificationsState = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 80.dp, 20.dp, 80.dp)
    ) {
        Row(
            modifier = Modifier.padding(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = darkModeState.value,
                onCheckedChange = { darkModeState.value = it },
            )
            Text(
                text = stringResource(R.string.dark_mode),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Row(modifier = Modifier.padding(32.dp)) {
            Checkbox(
                checked = notificationsState.value,
                onCheckedChange = { notificationsState.value = it }
            )
            Text(
                text = stringResource(R.string.notifications),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Row(modifier = Modifier.padding(32.dp)) {
            Checkbox(
                checked = isFahrenheit.value,
                onCheckedChange = { isFahrenheit.value = it }
            )
            Text(
                text = stringResource(R.string.show_in_fahrenheit),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }

    }

}