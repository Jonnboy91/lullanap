package com.jonnesten.lullanap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorViewModel: ViewModel() {
    private val _value: MutableLiveData<Float> = MutableLiveData()
    private val _value2: MutableLiveData<Float> = MutableLiveData()
    private val _clicked: MutableLiveData<Boolean> = MutableLiveData()
    private val _hasTempSensor: MutableLiveData<Boolean> = MutableLiveData()
    private val _hasLightSensor: MutableLiveData<Boolean> = MutableLiveData()
    val value: LiveData<Float> = _value
    val value2: LiveData<Float> = _value2
    val clicked: LiveData<Boolean> = _clicked
    val hasTempSensor: LiveData<Boolean> = _hasTempSensor
    val hasLightSensor: LiveData<Boolean> = _hasLightSensor


    fun updateValue(value: Float) {
        _value.value = value
    }

    fun updateValue2(value: Float) {
        _value2.value = value
    }

    fun isClicked(value: Boolean) {
        _clicked.value = value
    }

    fun updateTempSensor(value: Boolean) {
        _hasTempSensor.value = value
    }
    fun updateLightSensor(value: Boolean) {
        _hasLightSensor.value = value
    }

}