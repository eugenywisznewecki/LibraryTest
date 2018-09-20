package bel.ink.bel.nibltest

import android.app.Application
import bel.ink.bel.nibltest.repository.AppDatabase
import bel.ink.bel.nibltest.repository.HistoryForecast
import org.jetbrains.anko.doAsync
import timber.log.Timber

class App : Application() {


/*	companion object {
		lateinit var component: AppComponent
	}

	private fun builComponent(): AppComponent {
		return DaggerAppComponent.builder()
				.appModule(AppModule(this))
				.build()
	}*/

	override fun onCreate() {
		super.onCreate()

		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())

		} else {
			//make here smth with Fabric e.g
		}


		///TODO
		//Make sample data in DB
		doAsync {
			val database = AppDatabase.getInstance(context = this@App)
			if (database.forecastDao().all.isEmpty()) {
				val forecasts: MutableList<HistoryForecast> = mutableListOf()
				for (index: Int in 0..20) {
					val forecast = HistoryForecast(15L + index, index + 10L, 23.333, 25.444, "minsk",
							10L, "description + $index",
							15, 17, 15f)
					forecasts.add(index, forecast)
				}
				database.forecastDao().insertAll(forecasts = forecasts)
			}
		}
	}
}