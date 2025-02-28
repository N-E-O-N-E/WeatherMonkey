package com.example.weathermonkey.ui.composables

import WeatherModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathermonkey.R
import com.example.weathermonkey.data.repository.mockData.mockResponse

/**
 * Composable function to display the hourly weather forecast in a horizontal row.
 *
 * @param data WeatherModel containing weather data.
 * @param convertToIcon Function to fetch the appropriate weather icon based on weather code and day state.
 */
@Composable
fun HourlyForecastRow(
    data: WeatherModel,
    convertToIcon: (Int?, Int) -> Int
) {
    val sunriseTimes = data.daily.sunrise.map { it.substring(11, 13) }
    val sunsetTimes = data.daily.sunset.map { it.substring(11, 13) }

    Card(
        modifier = Modifier.padding(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.19f))
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.hourly.time.size) { index ->

                val time = data.hourly.time.getOrNull(index)?.substring(11, 16) ?: "00:00"
                val hourOnly = time.substring(0, 2)


                when {
                    sunriseTimes.contains(hourOnly) -> Log.d("HourlyForecastRow", "Sunrise at $time")
                    sunsetTimes.contains(hourOnly) -> Log.d("HourlyForecastRow", "Sunset at $time")
                    else -> Log.d("HourlyForecastRow", "Regular hour: $time")
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .padding(vertical = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = time,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    val imgRes = when {
                        sunriseTimes.contains(hourOnly) -> R.drawable.sunseticonsmall
                        sunsetTimes.contains(hourOnly) -> R.drawable.sunriseiconsmall
                        else -> convertToIcon(data.hourly.weatherCode.getOrNull(index) ?: 0, data.hourly.isDay.getOrNull(index) ?: 0)

                    }
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)

                    )

                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "${data.hourly.temperature2m.getOrNull(index) ?: "N/A"}°C",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
/**
 * Preview function for HourlyWForecastRowComposable.
 */
@Preview
@Composable
private fun HourlyForecastRowPreview() {
    HourlyForecastRow(
        data = mockResponse, convertToIcon = { code, _ ->
            when (code) {
                0, 1 -> R.drawable.suniconsmall
                2 -> R.drawable.cloudiconsmall
                3 -> R.drawable.cloudmaxiconsmall
                45, 48 -> R.drawable.fogiconsmall
                51, 53, 55 -> R.drawable.rainiconsmall
                61, 63, 65 -> R.drawable.rainiconsmall
                66, 67 -> R.drawable.snowiconsmall
                71, 73, 75 -> R.drawable.snowiconsmall
                80, 81, 82 -> R.drawable.rainiconsmall
                85, 86 -> R.drawable.snowiconsmall
                95 -> R.drawable.thundericonsmall
                96, 99 -> R.drawable.thundericonsmall
                else -> R.drawable.cloudiconsmall
            }
        }
    )
}