package com.example.weathermonkey.ui.composables

import WeatherModel
import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.weathermonkey.data.repository.mockData.mockResponse


@Composable
fun CurrentWeatherComposable(
    modifier: Modifier = Modifier,
    data: WeatherModel
) {

    Surface(modifier = modifier, color = Color.Cyan) {



    }


}

@Preview(name = "CurrentWeatherComp", device = "id:pixel_7_pro", showSystemUi = true, backgroundColor = 0xFF7BC1CC,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun CurrentWeatherComposablePreview() {
    CurrentWeatherComposable(data = mockResponse)
}


