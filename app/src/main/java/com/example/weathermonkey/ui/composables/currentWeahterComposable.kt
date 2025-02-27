package com.example.weathermonkey.ui.composables

import WeatherModel
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathermonkey.R
import com.example.weathermonkey.data.repository.mockData.mockResponse
import com.example.weathermonkey.utils.indexedTempForCurrentHourAsString
import java.util.Calendar

@Composable
fun CurrentWeatherComposable(
    modifier: Modifier = Modifier,
    data: WeatherModel,
    getWeatherIconXLByCode: (Int?, Int) -> Int,
    dayState: Int?
) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val sunPosition = currentHour / 24f

    Card(
        modifier = Modifier
            .padding(6.dp)
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically

            ) {
            Image(
                modifier = Modifier
                    .padding(30.dp)
                    .scale(1.7f),
                painter = painterResource(id = getWeatherIconXLByCode(data.daily.weatherCode.firstOrNull(), dayState ?: 0)),
                contentDescription = "",
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    modifier = Modifier,
                    text = indexedTempForCurrentHourAsString.getCurrent(data = data),
                    color = Color.White,
                    fontSize = 80.sp
                )
                SunPositionComposable(hour = currentHour, dayState = dayState)
            }
        }
    }
}

@Preview(
    name = "CurrentWeatherComp",
    device = "id:pixel_7_pro",
    showSystemUi = false,
    backgroundColor = 0xFF1BC5F1,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun CurrentWeatherComposablePreview() {
    CurrentWeatherComposable(
        data = mockResponse, getWeatherIconXLByCode = { code, _ ->
            when (code) {
                0 -> R.drawable.sunicon
                1, 2, 3 -> R.drawable.cloudicon
                45, 48 -> R.drawable.fogicon
                51, 53, 55 -> R.drawable.rainicon
                61, 63, 65 -> R.drawable.rainicon
                66, 67 -> R.drawable.snowicon
                71, 73, 75 -> R.drawable.snowicon
                80, 81, 82 -> R.drawable.rainicon
                85, 86 -> R.drawable.snowicon
                95 -> R.drawable.thundericon
                96, 99 -> R.drawable.thundericon
                else -> R.drawable.cloudicon
            }
        },
        dayState = 1
    )
}
