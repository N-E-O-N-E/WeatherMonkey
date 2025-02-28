package com.example.weathermonkey.ui.composables

import WeatherModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.data.repository.mockData.mockResponse
import com.example.weathermonkey.utils.WeeklyDisplayedTextFormatter
import com.example.weathermonkey.utils.dateFormatter
import com.example.weathermonkey.utils.weekDays
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
/**
 * Composable function that displays the weekly weather forecast in a vertical list.
 *
 * This function uses a `LazyColumn` inside a `Card` to present daily weather details, including
 * the day, weather description, and temperature range.
 *
 * @param data WeatherModel containing daily weather data.
 * @param getWeatherDescriptionByCode Function that returns a weather description based on a given weather code.
 */
@Composable
fun WeeklyWeatherComposable(
    data: WeatherModel,
    getWeatherDescriptionByCode: (Int?) -> String
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.18f)
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(data.daily.time.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.width(50.dp),horizontalAlignment = Alignment.Start) {
                        Text(
                            text = WeeklyDisplayedTextFormatter(index, data.daily.time.getOrNull(index) ?: "", dateFormatter, weekDays),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                    Column(modifier = Modifier.width(180.dp),horizontalAlignment = Alignment.Start) {

                        Text(
                            text = getWeatherDescriptionByCode(data.daily.weatherCode.getOrNull(index)),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                    }
                    Column(modifier = Modifier.width(65.dp),horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "${
                                data.daily.temperature2mMin.getOrNull(index)?.toInt()
                            }°-${data.daily.temperature2mMax.getOrNull(index)?.toInt()}°",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
/**
 * Preview function for WeeklyWeatherComposable, displaying mock data for design verification.
 */
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