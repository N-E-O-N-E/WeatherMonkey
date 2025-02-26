package com.example.weathermonkey.ui.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.R
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
    var updateLocation by remember { mutableStateOf(false) }

    val currentTemperatureState by weatherViewModel.currentTemperature.collectAsState()
    val temperatureState by weatherViewModel.temperature.collectAsState()
    val weatherDescriptionState by weatherViewModel.weatherDescription.collectAsState()
    val precipitationProbabilityState by weatherViewModel.precipitationProbability.collectAsState()

    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState.status.isGranted) {
            weatherViewModel.fetchLocation()
        } else {
            println("Standortberechtigung fehlt!")
            locationPermissionState.launchPermissionRequest()
        }
    }
    LaunchedEffect(updateLocation) {
        if (updateLocation) {
            weatherViewModel.fetchLocation()
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholderback),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dein Standort",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                )

                Row {
                    IconButton(
                        onClick = { updateLocation = !updateLocation }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Aktuelle Temperatur:")
                        currentTemperatureState?.let {
                            Text(
                                text = it,
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                            )
                        } ?: Text(text = "Daten werden geladen...")

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Wetterbeschreibung:")
                        weatherDescriptionState?.let {
                            Text(
                                text = it,
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                            )
                        } ?: Text(text = "Daten werden geladen...")

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Höchst- und Tiefsttemperaturen:")
                        temperatureState?.let {
                            Text(
                                text = it,
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                            )
                        } ?: Text(text = "Daten werden geladen...")

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Niederschlagswahrscheinlichkeit:")
                        precipitationProbabilityState?.let {
                            Text(
                                text = it,
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                            )
                        } ?: Text(text = "Daten werden geladen...")
                    }
                }
            }
        }
    }
}