package com.example.weathermonkey.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = koinViewModel()
) {

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val location = weatherViewModel.location.value

    LaunchedEffect(Unit) {
        if (locationPermissionState.status == PermissionStatus.Granted) {
            weatherViewModel.fetchLocation()
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        location?.let {
            Text(text = "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
        } ?: Text(text = "Standort nicht verfügbar")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { weatherViewModel.fetchLocation() }) {
            Text(text = "Standort aktualisieren")
        }
    }

    Column {
        Text(text = "Test")
    }
}

