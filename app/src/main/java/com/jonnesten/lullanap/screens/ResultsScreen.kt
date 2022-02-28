package com.jonnesten.lullanap.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jonnesten.lullanap.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.outlined.*
import com.google.gson.Gson
import com.jonnesten.lullanap.SavedData
import com.jonnesten.lullanap.SensorViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ResultsScreen(navController: NavController, sharedPreferences: SharedPreferences, sensorViewModel: SensorViewModel) {

    val date: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
    val day: String = SimpleDateFormat("E", Locale.getDefault()).format(Date())
    val editor = sharedPreferences.edit()
    val lux = sensorViewModel.lux.value
    val temp = sensorViewModel.temp.value
    val dB = sensorViewModel.dB.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 80.dp, 20.dp, 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
            text = lux.toString() + " " + stringResource(R.string.lux),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
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
            text = temp.toString() + " " + stringResource(R.string.celsius),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
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
            text = dB.toString() + " " + stringResource(R.string.db),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )

        OutlinedButton(
            onClick = {
                val data = SavedData(date = date, day = day, lux = lux, temp = temp, noise = dB, review = null)
                val gson = Gson()
                val dataJson = gson.toJson(data)
                editor.putString(date, dataJson)
                editor.apply()
                navController.navigate("history")
            }
        ) {
            Text(text = stringResource(R.string.save_data), color = MaterialTheme.colors.onPrimary,)
        }

        OutlinedButton(
            onClick = {
                navController.navigate("scanning")
            }
        ) {
            Text(text = stringResource(R.string.scan_again), color = MaterialTheme.colors.onPrimary,)
        }

    }
}