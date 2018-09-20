package bel.ink.bel.nibltest.weatherServerApi

import android.content.Context
import bel.ink.bel.nibltest.utils.API_KEY_STOLEN
import bel.ink.bel.nibltest.utils.BASE_URL
import bel.ink.bel.nibltest.weather.ForecastResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class Communicator(val context: Context) {

	val weatherAPI: WeatherAPI by lazy {
		Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build().create(WeatherAPI::class.java)
	}

	fun getForecastByCoordinated(days: Int, lat: Double, lonq: Double): ForecastResult? {
		val forecastCall = weatherAPI.getForecastCoordinates(lat, lonq, days, API_KEY_STOLEN)
		val response = forecastCall.execute()
		if (response.body() != null) {
			val forecastResult = response.body()
			Timber.d("+++" + response.toString())
			return forecastResult
		}
		return null
	}
}