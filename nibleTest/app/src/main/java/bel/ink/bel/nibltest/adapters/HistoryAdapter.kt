package bel.ink.bel.nibltest.adapters


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bel.ink.bel.nibltest.R
import bel.ink.bel.nibltest.activities.DetailActivity
import bel.ink.bel.nibltest.repository.HistoryForecast
import bel.ink.bel.nibltest.utils.HISTORY_FOR
import bel.ink.bel.nibltest.utils.toDateString
import kotlinx.android.synthetic.main.row_customer.view.*

class HistoryAdapter(applicationContext: Context, private val historyList: List<HistoryForecast>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.row_customer, parent, false)
		return HistoryAdapter.ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.setHistoryInList(historyList[position])
	}

	override fun getItemCount() = historyList.size

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		fun setHistoryInList(historyForecast: HistoryForecast) {
			with(historyForecast) {
				itemView.dateAndTimeView.text = dateOfGetting.toDateString()
				itemView.coordinates.text = "$longiture / $latitute"
				itemView.cityView.text = city
			}

			itemView.setOnClickListener {
				val intent = Intent(itemView.context, DetailActivity::class.java).putExtra(HISTORY_FOR, historyForecast)
				itemView.context.startActivity(intent)
			}
		}
	}

}