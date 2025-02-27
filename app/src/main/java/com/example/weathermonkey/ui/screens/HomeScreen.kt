package com.example.weathermonkey.ui.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.data.repository.mockData.mockResponse
import com.example.weathermonkey.ui.composables.CurrentWeatherComposable
import com.example.weathermonkey.ui.composables.HourlyForecastRow
import com.example.weathermonkey.ui.composables.WeeklyWeatherComposable
import com.example.weathermonkey.utils.indexedTempForCurrentHourAsString
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
    val weatherData by weatherViewModel.weatherResponseForecast.collectAsState()
    val weatherDataDaily by weatherViewModel.weatherResponseDaily.collectAsState()

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
            weatherViewModel.fetchWeatherResponseDaily(
                latitude = location.latitude,
                longitude = location.longitude
            )
            weatherViewModel.fetchWeatherResponseForecast(
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val test = weatherViewModel.getWeatherWallpaperByCode(
            indexedTempForCurrentHourAsString.getCurrentHoureAsInt(weatherData ?: mockResponse)
        )

        Image(
            painter = painterResource(id = test),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .padding(vertical = 5.dp)
                    .height(70.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.11f))

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
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )


                    Row {
                        IconButton(
                            onClick = { updateLocation = !updateLocation }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CurrentWeatherComposable(
                    data = weatherData ?: mockResponse,
                    getWeatherIconXLByCode = weatherViewModel::getWeatherIconXLByCode
                )

                HourlyForecastRow(
                    data = weatherDataDaily ?: mockResponse,
                    convertToIcon = weatherViewModel::getWeatherIconByCode
                )

                WeeklyWeatherComposable(
                    data = weatherData ?: mockResponse,
                    getWeatherDescriptionByCode = weatherViewModel::getWeatherDescriptionByCode
                )

                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}