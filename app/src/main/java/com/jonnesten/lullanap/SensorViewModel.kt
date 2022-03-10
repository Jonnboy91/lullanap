package com.jonnesten.lullanap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorViewModel: ViewModel() {
    private val _temp: MutableLiveData<Float?> = MutableLiveData()
    private val _lux: MutableLiveData<Float?> = MutableLiveData()
    private val _dB: MutableLiveData<Float?> = MutableLiveData()
    private val _clicked: MutableLiveData<Boolean> = MutableLiveData()
    private val _hasTempSensor: MutableLiveData<Boolean> = MutableLiveData()
    private val _hasLightSensor: MutableLiveData<Boolean> = MutableLiveData()
    val temp: LiveData<Float?> = _temp
    val lux: LiveData<Float?> = _lux
    val dB: LiveData<Float?> = _dB
    val clicked: LiveData<Boolean> = _clicked
    val hasTempSensor: LiveData<Boolean> = _hasTempSensor
    val hasLightSensor: LiveData<Boolean> = _hasLightSensor


    fun updateTemp(value: Float?) {
        _temp.value = value
    }

    fun updateLux(value: Float?) {
        _lux.value = value
    }

    fun updateDB(value: Float?) {
        _dB.value = value
    }

    fun updateTempSensor(value: Boolean) {
        _hasTempSensor.value = value
    }
    fun updateLightSensor(value: Boolean) {
        _hasLightSensor.value = value
    }

}