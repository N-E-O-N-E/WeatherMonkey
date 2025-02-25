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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
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
    val locationState = weatherViewModel.location.observeAsState()
    var shouldFetchLocation by remember { mutableStateOf(false) }

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
            shouldFetchLocation = !shouldFetchLocation        }) {
            Text(text = "Standort aktualisieren")
        }
    }
}

