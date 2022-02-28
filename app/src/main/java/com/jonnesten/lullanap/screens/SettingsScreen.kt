package com.jonnesten.lullanap.screens

import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonnesten.lullanap.R

@Composable
// TODO Make clicking text a selector too and figure out CheckBox styling
fun SettingsScreen(sharedPreferences: SharedPreferences) {
    val editor = sharedPreferences.edit()
    val darkTheme = isSystemInDarkTheme()
    val darkModeState = remember { mutableStateOf(sharedPreferences.getBoolean("theme", darkTheme)) }
    val isFahrenheit = remember { mutableStateOf(sharedPreferences.getBoolean("showInFah", false)) }
    val notificationsState = remember { mutableStateOf(sharedPreferences.getBoolean("notifications", true)) }
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
                onCheckedChange = {
                    darkModeState.value = it
                    editor.putBoolean("theme", it)
                    editor.apply()
                                  },
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
                onCheckedChange = {
                    notificationsState.value = it
                    editor.putBoolean("notifications", it)
                    editor.apply()
                }
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
                onCheckedChange = { isFahrenheit.value = it
                    editor.putBoolean("showInFah", it)
                    editor.apply()}
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