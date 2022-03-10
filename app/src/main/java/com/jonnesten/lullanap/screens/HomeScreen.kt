package com.jonnesten.lullanap.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jonnesten.lullanap.R

@Composable
fun HomeScreen(
    navController: NavController,
) {
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
                navController.navigate("scanning")
            },
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            elevation = ButtonDefaults.elevation(10.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .height(200.dp)
        ) {
            Text(
                text = stringResource(R.string.scan_button),
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }
    }
}