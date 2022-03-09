package com.jonnesten.lullanap

import java.util.*

data class SavedData(val date: Date, val day: String, val lux: Float? = null, val temp: Float? = null, val noise: Float? = null, val review: Int? = null, val comment: String?)
