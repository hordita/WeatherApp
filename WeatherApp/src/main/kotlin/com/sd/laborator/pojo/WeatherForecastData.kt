package com.sd.laborator.pojo

import java.net.URL

data class WeatherForecastData (
    var location: String,
    var date: String,
    var weatherState: String,
    var weatherStateIconURL: URL,
    var windDirection: String,
    var windSpeed: Int, // km/h
    var minTemp: Int, // grade celsius
    var maxTemp: Int,
    var currentTemp: Int,
    var humidity: Int // procent
)
