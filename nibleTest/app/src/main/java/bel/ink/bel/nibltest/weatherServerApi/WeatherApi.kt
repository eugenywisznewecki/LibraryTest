package bel.ink.bel.nibltest.weatherServerApi

import bel.ink.bel.nibltest.weather.ForecastResult
import bel.ink.bel.nibltest.weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

	@GET("weather/?mode=json&units=metric")
	fun getWeather(@Query("q") city: String, @Query("APPID") appId: String): Call<Weather>

	@GET("forecast/daily?mode=json&units=metric")
	fun getForecast(@Query("q") city: String, @Query("cnt") days: Int, @Query("APPID") appId: String):
			Call<ForecastResult>

	@GET("forecast/daily?mode=json&units=metric")
	fun getForecastCoordinates(@Query("lat") latitude: Double, @Query("lon") lonqitude: Double,
	                           @Query("cnt") days: Int, @Query("APPID") appId: String):
			Call<ForecastResult>

}