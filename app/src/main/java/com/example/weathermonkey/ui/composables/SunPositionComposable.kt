package com.example.weathermonkey.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunPositionComposable(modifier: Modifier = Modifier) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val sunPosition = currentHour / 24f

    val isDayTime = currentHour in 6..20
    val sunColor = if (isDayTime) Color.Yellow else Color(0xFF23293B)

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ){
        val width = size.width
        val height = size.height * 0.8f

        val path = Path().apply {
            moveTo(0f, height)
            for (i in 0..100) {
                val x = i / 100f * width
                val y = height - (sin(i / 100f * PI) * height * 0.1f).toFloat()
                lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 4.dp.toPx())
        )

        val sunX = sunPosition * width
        val sunY = height - (sin(sunPosition * PI) * height * 0.1f).toFloat()

        drawCircle(
            color = sunColor,
            radius = 30f,
            center = Offset(sunX, sunY)
        )
if(isDayTime)
            for (i in 0 until 8) {
                val angle = (i * 45) * (PI / 180f)
                val rayStart = Offset(
                    x = (sunX + cos(angle) * 35f).toFloat(),
                    y = (sunY + sin(angle) * 35f).toFloat()
                )
                val rayEnd = Offset(
                    x = (sunX + cos(angle) * 55f).toFloat(),
                    y = (sunY + sin(angle) * 55f).toFloat()
                )

                drawLine(
                    color = sunColor,
                    start = rayStart,
                    end = rayEnd,
                    strokeWidth = 4.dp.toPx()
                )
        }
    }
}


@Preview(
    name = "SunPositionLinePreview",
    device = "id:pixel_7_pro",
    showSystemUi = false,
    backgroundColor = 0xFF1BC5F1,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun SunPositionLineComposablePreview() {
    SunPositionComposable()
}
/*
@Composable
fun SunPositionComposable(modifier: Modifier = Modifier) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val sunPosition = currentHour / 24f

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height * 0.8f

        val path = Path().apply {
            moveTo(0f, height)
            for (i in 0..100) {
                val x = i / 100f * width
                val y = height - (sin(i / 100f * PI) * height * 0.1f).toFloat()
                lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Color.White,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 4.dp.toPx()
            )
        )

        val sunX = sunPosition * width
        val sunY = height - (sin(sunPosition * PI) * height * 0.1f).toFloat()

        drawCircle(
            color = Color.Yellow,
            radius = 35f,
            center = Offset(sunX, sunY)
        )
        for (i in 0 until 8) {
            val angle = (i * 45) * (PI / 180f)
            val rayStart = Offset(
                x = (sunX + cos(angle) * 40f).toFloat(),
                y = (sunY + sin(angle) * 45f).toFloat()
            )
            val rayEnd = Offset(
                x = (sunX + cos(angle) * 65f).toFloat(),
                y = (sunY + sin(angle) * 65f).toFloat()
            )

            drawLine(
                color = Color.Yellow,
                start = rayStart,
                end = rayEnd,
                strokeWidth = 4.dp.toPx()
            )
        }
    }
}
 */