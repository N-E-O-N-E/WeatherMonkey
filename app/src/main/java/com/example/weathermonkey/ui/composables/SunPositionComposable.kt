package com.example.weathermonkey.ui.composables

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathermonkey.ui.theme.moonColor
import com.example.weathermonkey.ui.theme.sunColor
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunPositionComposable(modifier: Modifier = Modifier, hour: Int, dayState: Int?) {
    val targetSunPosition = hour / 24f
    val sunPosition = remember { Animatable(targetSunPosition) }

    LaunchedEffect(targetSunPosition) {
        sunPosition.animateTo(targetSunPosition)
    }

    val isDayTime = dayState == 1
    val sunColor = if (isDayTime) Color(sunColor.value) else Color(moonColor.value)

    Canvas(
        modifier = modifier
            .background(color = Color.Transparent)
            .width(170.dp)
            .height(90.dp)
    ) {
        val width = size.width
        val height = size.height * 0.9f

        // Zeichnen der Wellenlinie
        val pathOne = Path().apply {
            moveTo(0f, height)
            for (i in 0..360) {
                val x = i / 360f * width
                val y = height - (sin(i / 360f * PI) * height * 0.5f).toFloat()
                lineTo(x, y)
            }
        }
        // Zeichnen der Wellenlinie
        val path = Path().apply {
            moveTo(0f, height)
            for (i in 0..360) {
                val x = i / 360f * width
                val y = height - (sin(i / 360f * PI) * height * 0.00f).toFloat()
                lineTo(x, y)
            }
        }

        drawPath(
            path = pathOne,
            color = Color.White.copy(alpha = 0.1f),
            style = Stroke(width = 3.dp.toPx())
        )
        drawPath(
            path = path,
            color = Color.White.copy(alpha = 0.6f),
            style = Stroke(width = 2.dp.toPx())
        )

        // Berechnung der Sonnenposition mit Animation
        val sunX = sunPosition.value * width
        val sunY = height - (sin(sunPosition.value * PI) * height * 0.6f).toFloat()

        // Zeichnen der Sonne/Mond
        drawCircle(
            color = sunColor,
            radius = 30f,
            center = Offset(sunX, sunY)
        )

        // Sonnenstrahlen nur am Tag zeichnen
        if (isDayTime) {
            for (i in 0 until 8) {
                val angle = (i * 90) * (PI / 360f)
                val rayStart = Offset(
                    x = (sunX + cos(angle) * 35f).toFloat(),
                    y = (sunY + sin(angle) * 35f).toFloat()
                )
                val rayEnd = Offset(
                    x = (sunX + cos(angle) * 65f).toFloat(),
                    y = (sunY + sin(angle) * 65f).toFloat()
                )

                drawLine(
                    color = sunColor,
                    start = rayStart,
                    end = rayEnd,
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }
}



@Preview(
    name = "SunPositionLinePreview",
    device = "id:pixel_7_pro",
    backgroundColor = 0xFF1BC5F1,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = true
)
@Composable
private fun SunPositionLineComposablePreview() {
    SunPositionComposable(hour = 12, dayState = 1)
}
