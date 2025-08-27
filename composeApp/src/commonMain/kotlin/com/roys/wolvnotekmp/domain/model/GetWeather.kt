package com.roys.wolvnotekmp.domain.model

fun getWeather(wmoCode: Int): String{
    var stringResult = ""
    when(wmoCode){
        0 ->{ stringResult = "Clear sky" }
        1,2,3 -> { stringResult = "Mainly clear, partly cloudy, and overcast" }
        45, 48 -> { stringResult = "Fog and depositing rime fog" }
        51, 53, 55 -> { stringResult = "Drizzle: Light, moderate, and dense intensity" }
        56, 57 -> { stringResult = "Freezing Drizzle: Light and dense intensity" }
        61, 63, 65 -> { stringResult = "Rain: Slight, moderate and heavy intensity" }
        66, 67 -> { stringResult = "Freezing Rain: Light and heavy intensity" }
        71, 73, 75 -> { stringResult = "Snow fall: Slight, moderate, and heavy intensity" }
        77 -> { stringResult = "Snow grains" }
        80, 81, 82 -> { stringResult = "Rain showers: Slight, moderate, and violent" }
        85, 86 -> { stringResult = "Snow showers slight and heavy" }
        95 -> { stringResult= "Thunderstorm: Slight or moderate" }
        96, 99 -> { stringResult = "Thunderstorm with slight and heavy hail" }
    }
    return stringResult
}