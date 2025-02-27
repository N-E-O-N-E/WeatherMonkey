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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathermonkey.R

@Composable
fun HourlyForecastRow(
    data: WeatherModel,
    convertToIcon: (Int?) -> Int
) {
    val sunriseTimes = data.daily.sunrise.map { it.substring(11, 16) }
    val sunsetTimes = data.daily.sunset.map { it.substring(11, 16) }

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


                when {
                    sunriseTimes.contains(time) -> Log.d("HourlyForecastRow", "Sunrise at $time")
                    sunsetTimes.contains(time) -> Log.d("HourlyForecastRow", "Sunset at $time")
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
                        sunriseTimes.contains(time) -> R.drawable.sunseticonsmall
                        sunsetTimes.contains(time) -> R.drawable.sunriseiconsmall
                        else -> convertToIcon(data.hourly.weatherCode.getOrNull(index) ?: 0)

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


//data.hourly.weatherCode.getOrNull(index)?.toString() ?: "N/A"