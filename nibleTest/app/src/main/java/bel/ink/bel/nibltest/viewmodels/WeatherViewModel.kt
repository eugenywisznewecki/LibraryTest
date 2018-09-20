package bel.ink.bel.nibltest.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.support.v7.app.AppCompatActivity
import bel.ink.bel.nibltest.repository.AppDatabase
import bel.ink.bel.nibltest.repository.HistoryForecast
import bel.ink.bel.nibltest.router.AppRouter
import bel.ink.bel.nibltest.weather.ForecastList
import bel.ink.bel.nibltest.weather.WeatherMapConverter
import bel.ink.bel.nibltest.weatherServerApi.Communicator
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.doAsync
import timber.log.Timber

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

	private val router by lazy { AppRouter(application.applicationContext) }
	private val communicator by lazy { Communicator(application.applicationContext) }
	private val database by lazy { AppDatabase.getInstance(application.applicationContext) }

	var liveData: MutableLiveData<ForecastList?>? = null

	var lat: Double = 0.0
	var loq: Double = 0.0
	var days: Int = 0
	var mapAddressIn: Map<String, String> = emptyMap()

	fun setCoordinatesDay(day: Int, lat: Double, lonq: Double, mapAddress: Map<String, String>) {
		this.days = day
		this.lat = lat
		this.loq = lonq
		mapAddressIn = mapAddress
		loadForecastByCoordinates(day, lat, lonq, mapAddress)
	}

	fun getLive(): MutableLiveData<ForecastList?>? {
		if (liveData == null) {
			liveData = MutableLiveData()
			loadForecastByCoordinates(this.days, this.lat, this.loq, this.mapAddressIn)
		}
		return liveData
	}


	private fun loadForecastByCoordinates(day: Int, lat: Double, lonq: Double, mapAddress: Map<String, String>) {
		launch(UI) {
			val result = async {
				communicator.getForecastByCoordinated(day, lat, lonq)
			}
			try {
				val forecastList = WeatherMapConverter().convertResultToForList("", result.await())
				liveData?.value = forecastList

				forecastList?.let {
					addToDb(makeBDEntity(forecastList, lat, loq, mapAddress))
				}
			} catch (e: Exception) {
				Timber.d("+++  $e")
			}
		}
	}

	private fun makeBDEntity(forecastslist: ForecastList, lat: Double, lon: Double, mapAddress: Map<String, String>): HistoryForecast {

		val historyDBForecast = HistoryForecast(
				null,
				System.currentTimeMillis(),
				lat,
				lon,
				"Minsk",
				forecastslist.get(0).date,
				forecastslist.get(0).description,
				forecastslist.get(0).high,
				forecastslist.get(0).low,
				forecastslist.get(0).speed
		)
		return historyDBForecast
	}

	private fun addToDb(historyforecast: HistoryForecast) {

		doAsync {
			database.forecastDao().insert(historyforecast)
		}
	}

	internal fun route(activity: AppCompatActivity) {
		router.openHistory(activity)
	}
}