package com.example.weathermonkey.ui.composables

import WeatherModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.data.repository.mockData.mockResponse

@Composable
fun WeeklyWeatherComposable(
    data: WeatherModel,
    getWeatherDescriptionByCode: (Int?) -> String
) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.daily.time.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.daily.time.getOrNull(index) ?: "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                    Text(
                        text = getWeatherDescriptionByCode(data.hourly.weatherCode.getOrNull(index)),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                    Text(
                        text = "${data.daily.temperature2mMin.getOrNull(index)?.toInt()}°-${data.daily.temperature2mMax.getOrNull(index)?.toInt()}°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewWeeklyWeatherComposable() {
    WeeklyWeatherComposable(
        data = mockResponse,
        getWeatherDescriptionByCode = { code ->
            when (code) {
                0 -> "Sonnig"
                1, 2, 3 -> "Überwiegend sonnig"
                45, 48 -> "Nebel"
                51, 53, 55 -> "Leichter Nieselregen"
                61, 63, 65 -> "Regen"
                66, 67 -> "Gefrierender Regen"
                71, 73, 75 -> "Schnee"
                80, 81, 82 -> "Schauer"
                85, 86 -> "Schneeschauer"
                95 -> "Gewitter"
                96, 99 -> "Gewitter mit Hagel"
                else -> "Wetterlage unbekannt"
            }
        }
    )
}