import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class WeatherModel(
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "generationtime_ms")
    val generationtimeMs: Double,
    @Json(name = "utc_offset_seconds")
    val utcOffsetSeconds: Int,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_abbreviation")
    val timezoneAbbreviation: String,
    @Json(name = "elevation")
    val elevation: Double,
    @Json(name = "daily_units")
    val dailyUnits: DailyUnits,
    @Json(name = "daily")
    val daily: Daily,
    @Json(name = "hourly_units")
    val hourlyUnits: HourlyUnits,
    @Json(name = "hourly")
    val hourly: Hourly
)

data class HourlyUnits(
    @Json(name = "time")
    val time: String,
    @Json(name = "temperature_2m")
    val temperature2m: String,
    @Json(name = "apparent_temperature")
    val apparentTemperature: String,
    @Json(name = "precipitation_probability")
    val precipitationProbability: String,
    @Json(name = "rain")
    val rain: String,
    @Json(name = "showers")
    val showers: String,
    @Json(name = "snowfall")
    val snowfall: String,
    @Json(name = "weather_code")
    val weatherCode: String,
    @Json(name = "cloud_cover_low")
    val cloudCoverLow: String,
    @Json(name = "cloud_cover_mid")
    val cloudCoverMid: String,
    @Json(name = "cloud_cover_high")
    val cloudCoverHigh: String
)

data class Hourly(
    @Json(name = "time")
    val time: List<String>,
    @Json(name = "temperature_2m")
    val temperature2m: List<Double>,
    @Json(name = "apparent_temperature")
    val apparentTemperature: List<Double>,
    @Json(name = "precipitation_probability")
    val precipitationProbability: List<Int>,
    @Json(name = "rain")
    val rain: List<Double>,
    @Json(name = "showers")
    val showers: List<Double>,
    @Json(name = "snowfall")
    val snowfall: List<Double>,
    @Json(name = "weather_code")
    val weatherCode: List<Int>,
    @Json(name = "cloud_cover_low")
    val cloudCoverLow: List<Int>,
    @Json(name = "cloud_cover_mid")
    val cloudCoverMid: List<Int>,
    @Json(name = "cloud_cover_high")
    val cloudCoverHigh: List<Int>
)

data class DailyUnits(
    @Json(name = "time")
    val time: String,
    @Json(name = "temperature_2m_max")
    val temperature2mMax: String,
    @Json(name = "temperature_2m_min")
    val temperature2mMin: String
)

data class Daily(
    @Json(name = "time")
    val time: List<String>,
    @Json(name = "temperature_2m_max")
    val temperature2mMax: List<Double>,
    @Json(name = "temperature_2m_min")
    val temperature2mMin: List<Double>
)
