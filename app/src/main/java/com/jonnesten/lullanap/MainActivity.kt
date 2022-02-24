package com.jonnesten.lullanap

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import com.jonnesten.lullanap.bottomnav.BottomNavigation
import com.jonnesten.lullanap.bottomnav.NavigationGraph

class MainActivity : ComponentActivity(), SensorEventListener {

    companion object{
        private lateinit var sm: SensorManager
        private var sTemp: Sensor? = null
        private var sLight: Sensor? = null
        private val sensorViewModel = SensorViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        setContent {
            MainScreenView(sensorViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        if (sTemp == null) {
            // This will give a toast message to the user if there is no sensor in the device
            Toast.makeText(this, "No TYPE_AMBIENT_TEMPERATURE sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sm.registerListener(this, sTemp, SensorManager.SENSOR_DELAY_UI)
        }

        if (sLight == null) {
            // This will give a toast message to the user if there is no sensor in the device
            Toast.makeText(this, "No TYPE_LIGHT sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sm.registerListener(this, sLight, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        val buttonIsClicked = sensorViewModel.clicked.value
        //Right now everything is working as should, except that if for some reason the light or temp wouldn't change during the scanning even a bit, then the onSensorChanged wouldn't be read and thus no value for the user. Option is to just not think when user is getting the scan, but to keep saving the values to list and just use the latest one from there.
        if (event.sensor == sTemp && buttonIsClicked == true) {
            Log.d("onSensorChangedTemp", "On sensorChanged, $event: ${event.values[0]}")
            sensorViewModel.updateValue(event.values[0])
        }
        if (event.sensor == sLight && buttonIsClicked == true) {
            Log.d("onSensorChangedLight", "On sensorChanged, $event: ${event.values[0]}")
            sensorViewModel.updateValue2(event.values[0])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d("onAccuracyChanged", "onAccuracyChanged ${p0?.name}: $p1")
    }
}

@Composable
fun MainScreenView(SensorViewModel: SensorViewModel){
    val tempValue by SensorViewModel.value.observeAsState()
    val lightValue by SensorViewModel.value2.observeAsState()

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController, tempValue = tempValue, lightValue = lightValue, SensorViewModel = SensorViewModel)
    }
}