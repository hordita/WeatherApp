package com.sd.laborator.services

import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface {
    override fun getForecastData(locationId: Int): WeatherForecastData {
        // ID-ul locaţiei nu trebuie codificat, deoarece este numeric
       // val forecastDataURL = URL("https://api.weatherapi.com/v1/search.json?key=3a1d89e575e54e8f89e160221240903&q=$locationId")
        val forecastDataURL = URL("https://api.weatherapi.com/v1/forecast.json?key=47489045415543348fc122536241103&q=id:$locationId&days=1&aqi=no&alerts=no")
        // preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()

        // parsare obiect JSON primit
        val responseRootObject = JSONObject(rawResponse)
        val locationDataObject = responseRootObject.getJSONObject("location")
        val currentObject = responseRootObject.getJSONObject("current")
        val conditionObject = currentObject.getJSONObject("condition")
        val forecastObject = responseRootObject.getJSONObject("forecast")
        val forecastDayObject = forecastObject.getJSONArray("forecastday").getJSONObject(0)
        val dayObject = forecastDayObject.getJSONObject("day")

        // construire şi returnare obiect POJO care încapsulează datele meteo
        return WeatherForecastData(
            location = locationDataObject.getString("name"),
            date = timeService.getCurrentTime(),
            weatherState = conditionObject.getString("text"),
            weatherStateIconURL = URL("http:${conditionObject.getString("icon")}"),
            windDirection = currentObject.getString("wind_dir"),
            windSpeed = currentObject.getFloat("wind_kph").roundToInt(),
            minTemp = dayObject.getFloat("mintemp_c").roundToInt(),
            maxTemp = dayObject.getFloat("maxtemp_c").roundToInt(),
            currentTemp = currentObject.getFloat("temp_c").roundToInt(),
            humidity = currentObject.getFloat("humidity").roundToInt()
        )
    }
}
