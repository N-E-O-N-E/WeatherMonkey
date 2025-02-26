package com.example.weathermonkey.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = koinViewModel()
) {

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val locationState = weatherViewModel.location.collectAsState()
    var shouldFetchLocation by remember { mutableStateOf(false) }
    val temperatureState by weatherViewModel.temperature.collectAsState()

    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState.status.isGranted) {
            weatherViewModel.fetchLocation()
        } else {
            println("Standortberechtigung fehlt!")
            locationPermissionState.launchPermissionRequest()
        }
    }
    LaunchedEffect(shouldFetchLocation) {
        if (shouldFetchLocation) {
            weatherViewModel.fetchLocation()
        } else {
            println("LaunchedEffect: Standort wird nicht aktualisiert (false)")
        }
    }
    LaunchedEffect(locationState.value) {
        val location = locationState.value
        if (location != null) {
            weatherViewModel.fetchTemperatureByLocation(
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        locationState.let {
            Text(text = "Latitude: ${it.value?.latitude}, Longitude: ${it.value?.longitude}")
        } ?: Text(text = "Standort nicht verfügbar")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            shouldFetchLocation = !shouldFetchLocation
        }) {
            Text(text = "Standort aktualisieren")
        }

        Card(
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Aktuelle Temperatur:")
                temperatureState?.let {
                    Text(
                        text = it,
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                    )
                } ?: Text(text = "Daten werden geladen...")
            }
        }
    }
}


