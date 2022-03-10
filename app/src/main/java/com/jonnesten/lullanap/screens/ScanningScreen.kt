package com.jonnesten.lullanap.screens

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.jonnesten.lullanap.*
import com.jonnesten.lullanap.R
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ScanningScreen(
    sensorViewModel: SensorViewModel,
    navController: NavHostController,
) {
    var dB: Float? by remember { mutableStateOf(null) }
    var addTemperature by remember { mutableStateOf(false) }
    val luxScanned = remember { mutableStateOf(false) }
    val luxValue: MutableState<Float?> = remember { mutableStateOf(null) }
    val tempScanned = remember { mutableStateOf(false) }
    val tempValue: MutableState<Float?> = remember { mutableStateOf(null) }
    val dbScanned = remember { mutableStateOf(false) }
    val dbValue: MutableState<Float?> = remember { mutableStateOf(null) }

    val tempScanValue by sensorViewModel.temp.observeAsState()
    val lightScanValue by sensorViewModel.lux.observeAsState()

    // For some reason the soundMeter.start is not working, it recognises mediarecorder/etc, but when calling the start method within the SoundMeters mediarecorder, it crashes.
    /*val soundMeter = SoundMeter()
    soundMeter.start()
    Handler().postDelayed({
        Log.d("SoundDB", "Calling stop in 5sec so has time to get the dB")
        soundMeter.stop()
    },
     3000
    )

    dB = 20 * Math.log10(soundMeter.getAmplitude() / 32767).toFloat();
    Log.d("SoundDB", "value is $dB")
    */

    if (lightScanValue != null && !luxScanned.value) {
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("VALUE", "${sensorViewModel.lux.value}")
            luxValue.value = sensorViewModel.lux.value
            luxScanned.value = true
        }, 1000)
    }
    Handler(Looper.getMainLooper()).postDelayed({
        if (lightScanValue == null) {
            luxScanned.value = true
        }
        if (tempScanValue == null && !addTemperature) {
            tempScanned.value = true
        }
        if (dB == null) {
            dbScanned.value = true
        }
    }, 5000)

    if (sensorViewModel.hasTempSensor.value != true && sensorViewModel.temp.value == null) {
        addTemperature = true
    }

    if (sensorViewModel.temp.value != null && !tempScanned.value) {
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("VALUE", sensorViewModel.temp.value.toString())
            tempValue.value = sensorViewModel.temp.value
            tempScanned.value = true
        }, 1000)
    }

    if (!dbScanned.value && dB != null) {
        Handler(Looper.getMainLooper()).postDelayed({
            dbValue.value = dB
            dbValue.value?.let { sensorViewModel.updateDB(it) }
            dbScanned.value = true
        }, 1000)
    }

    if (dbScanned.value && luxScanned.value && tempScanned.value) {
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate("results")
        }, 1000)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 80.dp, 20.dp, 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.scanning),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(bottom = 15.dp)
        ) {
            if (!luxScanned.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .alpha(0.4f)
                )
            }
            if (luxScanned.value && luxValue.value != null) {
                Icon(
                    Icons.Outlined.Done,
                    contentDescription = "Done",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
            if (luxScanned.value && luxValue.value === null) {
                Icon(
                    Icons.Outlined.Cancel,
                    contentDescription = "Error",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
            Text(
                text = stringResource(R.string.light),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(bottom = 15.dp)
        ) {
            if (!tempScanned.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .alpha(0.4f)
                )
            }
            if (tempScanned.value && tempValue.value != null) {
                Icon(
                    Icons.Outlined.Done,
                    contentDescription = "Done",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
            if (tempScanned.value && tempValue.value === null) {
                Icon(
                    Icons.Outlined.Cancel,
                    contentDescription = "Error",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
            Text(
                text = stringResource(R.string.temperature),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(bottom = 15.dp)
        ) {
            if (!dbScanned.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .alpha(0.4f)
                )
            }
            if (dbScanned.value && dbValue.value != null) {
                Icon(
                    Icons.Outlined.Done,
                    contentDescription = "Done",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
            if (dbScanned.value && dbValue.value === null) {
                Icon(
                    Icons.Outlined.Cancel,
                    contentDescription = "Error",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
            Text(
                text = stringResource(R.string.db),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        // TODO This AlertDialog should pop up only after clicking scanning and you would be on the scanning screen
        if (addTemperature) {
            val textFieldValue = remember { mutableStateOf("")}
            AlertDialog(
                onDismissRequest = {
                    Log.d("addTemp", "YOU NEED TO ADD TEMPERATURE")
                },
                text = {
                    Column {
                        Text(
                            stringResource(R.string.add_temp),
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        TextField(
                            value = textFieldValue.value,
                            onValueChange = {
                                textFieldValue.value = it
                                tempValue.value = it.toFloat()
                            },
                            label = {
                                Text(
                                    stringResource(R.string.temperature),
                                    color = MaterialTheme.colors.onPrimary
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType =
                                KeyboardType.Number
                            ),
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            addTemperature = false
                            val value = tempValue.value
                            if (value != null) {
                                sensorViewModel.updateTemp(value)
                            }
                        }
                    ) {
                        Text(
                            stringResource(R.string.save_data),
                            color = MaterialTheme.colors.secondaryVariant,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    // TODO Change Temp value optional, if user skips, do not show the temp value anywhere
                    TextButton(
                        onClick = {
                            addTemperature = false
                            val value = tempValue.value
                            if (value != null) {
                                sensorViewModel.updateTemp(0.0f)
                            }
                        }
                    ) {
                        Text(
                            stringResource(R.string.skip),
                            color = MaterialTheme.colors.secondaryVariant,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    }
}