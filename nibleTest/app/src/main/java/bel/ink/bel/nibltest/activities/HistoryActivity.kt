package bel.ink.bel.nibltest.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import bel.ink.bel.nibltest.R
import bel.ink.bel.nibltest.adapters.HistoryAdapter
import bel.ink.bel.nibltest.repository.HistoryForecast
import bel.ink.bel.nibltest.viewmodels.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

	private lateinit var viewModel: HistoryViewModel
	private var enableNavigation: Boolean = false


	private val tab: ActionBar.TabListener by lazy {
		object : ActionBar.TabListener {
			override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction?) {
			}

			override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction?) {

			}

			override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction?) {
				if (enableNavigation) {
					when (tab.position) {
						0 -> {
							viewModel.route(this@HistoryActivity)
							finish()
						}
					}
				}
			}
		}
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_history)

		viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
		val liveHistoryForecasts = viewModel.getLiveHistoricalForecasts()

		liveHistoryForecasts?.observe(this, Observer { liveHistoryForecasts ->
			onHistoryLoaded(liveHistoryForecasts)
		})


		setUpBar()
		customerListHistory.layoutManager = LinearLayoutManager(this)

	}

	@SuppressWarnings("deprecation")
	fun setUpBar() {
		val bar = supportActionBar
		bar?.let {
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS)
			bar.addTab(bar.newTab().setText("Forecast").setTabListener(tab))
			bar.addTab(bar.newTab().setText("History").setTabListener(tab))
			bar.selectTab(bar.getTabAt(1))
			enableNavigation = true
			bar.setDisplayShowTitleEnabled(false)
			bar.setDisplayShowHomeEnabled(false)
		}
	}

	fun onHistoryLoaded(list: List<HistoryForecast>?) {
		if (list != null) {
			customerListHistory.adapter = HistoryAdapter(applicationContext, list)
			customerListHistory.adapter.notifyDataSetChanged()
			customerListHistory.visibility = View.VISIBLE
			progressHistory.visibility = View.GONE

		} else {
			customerListHistory.visibility = View.GONE
		}
	}
}
