package bel.ink.bel.nibltest.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bel.ink.bel.nibltest.R
import bel.ink.bel.nibltest.utils.toDateString
import bel.ink.bel.nibltest.weather.ForecastIn
import bel.ink.bel.nibltest.weather.ForecastList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherListAdapter(private val listForecast: ForecastList) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.setWeatherInList(listForecast[position])
	}

	override fun getItemCount() = listForecast.size

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		fun setWeatherInList(forecast: ForecastIn) {
			with(forecast) {
				com.bumptech.glide.Glide.with(itemView.context).load(iconUrl).into(itemView.icon)
				itemView.date.text = date.toDateString()
				itemView.description.text = description

				itemView.nightTemperature.text = "night: ${low}º"
				itemView.dayTemperature.text = "day: ${high}º"
				itemView.wind.text = "wind speed: ${speed}"

				when {
					(high > 29 || high < -10) -> itemView.dayTemperature.setBackgroundColor(android.graphics.Color.RED)
					(low > 29 || low < -10) -> itemView.nightTemperature.setBackgroundColor(android.graphics.Color.RED)
					(speed > 10) -> itemView.wind.setBackgroundColor(android.graphics.Color.RED)
				}
			}
		}
	}
}
