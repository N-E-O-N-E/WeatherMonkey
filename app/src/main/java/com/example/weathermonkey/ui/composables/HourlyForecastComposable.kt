package com.example.weathermonkey.ui.composables

import WeatherModel
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.weathermonkey.data.repository.mockData.mockResponse

@Composable
fun HourlyForecastRow(
    data: WeatherModel,
    convertToIcon: (Int?) -> Int
) {
    Card(
        modifier = Modifier.padding(6.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.hourly.time.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = data.hourly.time[index].substring(11, 16),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Image(
                        painter = painterResource(id = convertToIcon(data.hourly.weatherCode.getOrNull(index))),
                        contentDescription = null
                    )
                    Text(
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