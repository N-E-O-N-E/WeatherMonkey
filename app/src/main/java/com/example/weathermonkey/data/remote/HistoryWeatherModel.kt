import com.squareup.moshi.Json

data class HistoryWeatherModel(
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
    val daily: Daily
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
    val temperature2mMax: List<Any?>,
    @Json(name = "temperature_2m_min")
    val temperature2mMin: List<Any?>
)