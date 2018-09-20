package bel.ink.bel.nibltest.router

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import bel.ink.bel.nibltest.activities.DetailActivity
import bel.ink.bel.nibltest.activities.HistoryActivity
import bel.ink.bel.nibltest.activities.WeatherActivity
import bel.ink.bel.nibltest.repository.HistoryForecast
import bel.ink.bel.nibltest.utils.HISTORY_FOR


class AppRouter(val contextIn: Context) {

	fun openHistory(activity: AppCompatActivity) {
		activity.startActivity(Intent(contextIn, HistoryActivity::class.java))
	}

	fun openWeather(activity: AppCompatActivity) {
		activity.startActivity(Intent(contextIn, WeatherActivity::class.java))
	}

	fun openDetail(activity: AppCompatActivity, historyForecast: HistoryForecast) {
		val intent = Intent(contextIn, DetailActivity::class.java).putExtra(HISTORY_FOR, historyForecast)
		activity.startActivity(intent)
	}


}