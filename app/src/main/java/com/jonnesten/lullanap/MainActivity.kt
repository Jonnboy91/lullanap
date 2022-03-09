package com.jonnesten.lullanap

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jonnesten.lullanap.bottomnav.BottomNavigation
import com.jonnesten.lullanap.bottomnav.NavigationGraph
import com.jonnesten.lullanap.screens.DayDetails
import com.jonnesten.lullanap.ui.theme.LullaNapTheme
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity(), SensorEventListener {

    companion object{
        private lateinit var sm: SensorManager
        private var sTemp: Sensor? = null
        private var sLight: Sensor? = null
        private val sensorViewModel = SensorViewModel()
        private lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)





        sm = getSystemService(Context.SENSOR_SERVICE) as
                SensorManager
        sTemp = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        sLight = sm.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            sensorViewModel.updateTempSensor(true)
        } else {
            sensorViewModel.updateTempSensor(false)
        }
        if (sm.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            sensorViewModel.updateLightSensor(true)
        } else {
            sensorViewModel.updateLightSensor(false)
        }
        sharedPreferences = getSharedPreferences("SavedData", Context.MODE_PRIVATE)

        setContent {
            //TODO problem atm is that when changing in settings, it won't affect the theme before you restart application, since sharedPreferences.getBoolean is not a state that changes, so that has to be done differently
            LullaNapTheme(sharedPreferences.getBoolean("theme", isSystemInDarkTheme())) {
                MainScreenView()
            }

        }
    }

    private fun isYesterday(date: Date): Boolean {
        return DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS);
    }

    override fun onResume() {
        super.onResume()
        if (sTemp != null) {
            sm.registerListener(this, sTemp, SensorManager.SENSOR_DELAY_UI)
        }

        if (sLight != null) {
            sm.registerListener(this, sTemp, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        val buttonIsClicked = sensorViewModel.clicked.value
        // && buttonIsClicked == true Right now everything is working as should, except that if for some reason the light or temp wouldn't change during the scanning even a bit, then the onSensorChanged wouldn't be read and thus no value for the user. Option is to just not think when user is getting the scan, but to keep saving the values to list and just use the latest one from there.
        if (event.sensor == sTemp ) {
            Log.d("onSensorChangedTemp", "On sensorChanged, $event: ${event.values[0]}")
            sensorViewModel.updateTemp(event.values[0])
        }
        if (event.sensor == sLight) {
            Log.d("onSensorChangedLight", "On sensorChanged, $event: ${event.values[0]}")
            sensorViewModel.updateLux(event.values[0])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d("onAccuracyChanged", "onAccuracyChanged ${p0?.name}: $p1")
    }

    @Composable
    fun MainScreenView() {

        var addReview by remember { mutableStateOf(false) }
        val comment: MutableState<String?> = remember { mutableStateOf(null) }
        val review: MutableState<Int?> = remember { mutableStateOf(null) }
        val editor = sharedPreferences.edit()
        lateinit var yesterdayData: SavedData
        val allEntries: Map<String, *> = sharedPreferences.all
        val gson = Gson()

        val filteredEntries = allEntries.filter { it.key != "theme" && it.key != "notifications" && it.key != "showInFah" }.toSortedMap()

        val jsonData = sharedPreferences.getString(filteredEntries.firstKey(), null)
        val type = object : TypeToken<SavedData>() {}.type
        if(jsonData != null) {
            yesterdayData = gson.fromJson(jsonData, type);
            if(!isYesterday(date = yesterdayData.date) && yesterdayData.review == null){
                Log.d("ASK FOR REVIEW", "NEED TO REVIEW")
                addReview = true;
            }
        }

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
                bottomBar = {
                    BottomNavigation(navController = navController)
                    // TODO Get current height of a navigation bar and use it as Spacer bottom padding
                    val navHeight = applicationContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                    Log.d("test", navHeight.toString())
                    Spacer(Modifier.padding(0.dp, 50.dp))
                },
            ) {
                NavigationGraph(
                    navController = navController,
                    sensorViewModel = sensorViewModel,
                    sharedPreferences = sharedPreferences
                )
            }
            if (addReview) {
                AlertDialog(
                    onDismissRequest = {
                        Log.d("addReview", "YOU NEED TO ADD REVIEW")
                        Toast.makeText(applicationContext, "Add review and press save review", Toast.LENGTH_SHORT).show()
                    },
                    title = {
                        Text("Add yesterdays review (1-5) and a comment if you want to", color = MaterialTheme.colors.onPrimary)
                    },
                    text = {
                        Column {
                            TextField(
                                value = "",
                                onValueChange = { review.value = it.toInt()},
                                label = { Text("1-5 (Mandatory)", color = MaterialTheme.colors.onPrimary) },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType =
                                    KeyboardType.Number
                                ),
                                // TODO INPUT text not showing???
                                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.onPrimary),
                                textStyle = TextStyle(color = MaterialTheme.colors.onPrimary)
                            )
                            TextField(
                                value = "",
                                onValueChange = { comment.value = it },
                                label = { Text("Comment (Voluntary)", color = MaterialTheme.colors.onPrimary) },
                                // TODO INPUT text not showing???
                                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.onPrimary),
                                textStyle = TextStyle(color = MaterialTheme.colors.onPrimary)
                            )
                        }

                    },
                    buttons = {
                        OutlinedButton(
                            onClick = {
                                if(review.value != null) {
                                    addReview = false
                                    Log.d("UPDATED", "REVIEW GIVEN ${review.value} ${comment.value}")
                                    val data = SavedData(date = yesterdayData.date, day = yesterdayData.day, lux = yesterdayData.lux, temp = yesterdayData.temp, noise = yesterdayData.noise, review = review.value, comment = comment.value)
                                    val gson = Gson()
                                    val dataJson = gson.toJson(data)
                                    editor.putString(filteredEntries.firstKey(), dataJson)
                                    editor.apply()
                                }else {
                                    Toast.makeText(applicationContext, "Add review and then press save", Toast.LENGTH_SHORT).show()
                                    Log.d("UPDATE REVIEW", "UPDATE REVIEW")
                                }
                            }
                        ) {
                            Text(text = "Save Review & comment", color = MaterialTheme.colors.onPrimary,)
                        }
                    }
                )
            }
        }
    }
}
