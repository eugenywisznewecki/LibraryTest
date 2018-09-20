package bel.ink.bel.nibltest.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bel.ink.bel.nibltest.R
import bel.ink.bel.nibltest.repository.HistoryForecast
import bel.ink.bel.nibltest.utils.HISTORY_FOR
import bel.ink.bel.nibltest.utils.format
import bel.ink.bel.nibltest.utils.toDateString
import bel.ink.bel.nibltest.utils.toTimeLong
import kotlinx.android.synthetic.main.activity_detail.*


//simple activity to show text
class DetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		val historyForecast = intent.getSerializableExtra(HISTORY_FOR) as HistoryForecast

		historyForecast?.let {
			dateOfGettingView.text = "${resources.getString(R.string.dateOfgetting)}:  ${historyForecast.dateOfGetting.toDateString()} ${historyForecast.dateOfGetting.toTimeLong()} UTC"
			cityOfHistory.text = "${resources.getString(R.string.city)}: ${historyForecast.city}"
			dateHistory.text = "${resources.getString(R.string.date)}:  ${historyForecast.date.toDateString()}"
			descriptionHistory.text = "${resources.getString(R.string.description)}: ${historyForecast.description}"
			windHistory.text = "${resources.getString(R.string.windSpeed)}: ${historyForecast.speed.toString()}"
			dayTemperatureHistory.text = "${resources.getString(R.string.dayTemp)}: ${historyForecast.high.toString()}"
			nightTemperatureHistory.text = "${resources.getString(R.string.nightTemp)}: ${historyForecast.low.toString()}"
			latDetail.text = "lat: ${historyForecast.latitute.format(4)}"
			lonqDetail.text = "lat: ${historyForecast.longiture.format(4)}"
		}
	}
}
