package bel.ink.bel.nibltest.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.support.v7.app.AppCompatActivity
import bel.ink.bel.nibltest.repository.AppDatabase
import bel.ink.bel.nibltest.repository.HistoryForecast
import bel.ink.bel.nibltest.router.AppRouter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

	private val router by lazy { AppRouter(application.applicationContext) }
	private val database by lazy { AppDatabase.getInstance(application.applicationContext) }


	var liveData: MutableLiveData<List<HistoryForecast>?>? = null

	fun getLiveHistoricalForecasts(): MutableLiveData<List<HistoryForecast>?>? {
		if (liveData == null) {
			liveData = MutableLiveData()
			loadFromDBAllForecasts()
		}
		return liveData
	}

	fun loadFromDBAllForecasts() {
		doAsync {
			val historicalForecast = database.forecastDao().all
			uiThread {
				liveData?.value = historicalForecast
			}
		}
	}


	internal fun route(activity: AppCompatActivity) {
		router.openWeather(activity)
	}

	internal fun routeToDetail(activity: AppCompatActivity, historyForecast: HistoryForecast) {
		router.openDetail(activity, historyForecast)
	}
}