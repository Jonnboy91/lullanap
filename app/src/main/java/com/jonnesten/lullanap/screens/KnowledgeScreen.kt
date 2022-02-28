package com.jonnesten.lullanap.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.jonnesten.lullanap.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.outlined.*

@Composable
fun KnowledgeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 80.dp, 20.dp, 80.dp)
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
            text = stringResource(R.string.ideal_lux),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )
        Text(
            text = stringResource(R.string.lux_desc),
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp
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
            text = stringResource(R.string.ideal_temp),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )
        Text(
            text = stringResource(R.string.temp_desc),
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp
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
            text = stringResource(R.string.ideal_db),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 30.sp
        )
        Text(
            text = stringResource(R.string.db_desc),
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp
        )
    }
}