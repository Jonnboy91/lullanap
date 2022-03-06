package com.jonnesten.lullanap.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jonnesten.lullanap.R
import com.jonnesten.lullanap.SavedData

@Composable
fun HistoryScreen(sharedPreferences: SharedPreferences) {
    val gson = Gson()
    val allEntries: Map<String, *> = sharedPreferences.all
    val allEntriesDescending = allEntries.toSortedMap()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 50.dp, 0.dp, 120.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for ((key, value) in allEntriesDescending) {
            Log.d("SAVED", "key: $key, value: $value")
            if(key != "theme" && key != "notifications" && key != "showInFah"){
                Log.d("SAVED", "I GOT HERE WITH $key")
                val jsonData = sharedPreferences.getString(key, null)
                val type = object : TypeToken<SavedData>() {}.type
                if(jsonData != null) {
                    val data: SavedData = gson.fromJson(jsonData,type);
                    Log.d("SAVED", data.toString());
                    DayDetails(review= data.review, day = data.day, lux = data.lux, temp = data.temp, noise = data.noise, comment = data.comment)
                }
            }
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
fun DayDetails(
    review: Int?,
    day: String,
    lux: Float?,
    temp: Float?,
    noise: Float?,
    comment: String?
) {
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
            if (comment != null) {
                Text(
                    text = comment,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 5.dp)
                )
            }
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
                    text = lux.toString() + stringResource(R.string.lux),
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
                    text = temp.toString() + stringResource(R.string.celsius),
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
                    text = noise.toString() + stringResource(R.string.db),
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
                if (review != null) {
                    ReviewIcon(review)
                } else {
                    Text("No review yet, review tomorrow morning")
                }
            }
        }
    }
}