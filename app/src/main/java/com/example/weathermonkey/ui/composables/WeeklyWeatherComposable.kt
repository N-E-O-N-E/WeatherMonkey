package com.example.weathermonkey.ui.composables

import WeatherModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyWeatherComposable(
      data: WeatherModel
//    days: List<String>,
//    weatherDescriptions: List<String>,
//    temperatures: List<String>
) {
    Card(
        modifier = Modifier
            .padding(6.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            data.daily.time.forEachIndexed { index, day ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                    Text(
                        text = data.hourly.apparentTemperature.getOrNull(index).toString() ?: "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                    Text(
                        text = data.hourly.temperature2m.getOrNull(index).toString() ?: "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                }
            }
        }
    }
}