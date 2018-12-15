package bel.ink.bel.systemtechtest.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import bel.ink.bel.systemtechtest.R
import bel.ink.bel.systemtechtest.adapters.CurrencyListAdapter
import bel.ink.bel.systemtechtest.adapters.DragHelper
import bel.ink.bel.systemtechtest.entities.CurrencyResponse
import bel.ink.bel.systemtechtest.utils.NetChecker
import bel.ink.bel.systemtechtest.viewModels.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //too simple to use DI
    private lateinit var viewModel: CurrencyViewModel
    private val netChecker: NetChecker by lazy { NetChecker(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currenciesListView.layoutManager = GridLayoutManager(this, 1)
        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)

        //TODO it
        if (netChecker.isNetworkAwailable()) {
            val currenciesList = viewModel.getCurrencies()
            currenciesList?.observe(this, Observer { currencies ->
                onCurrenciesLoaded(currencies)
            })
        } else {
            textNoInternet.visibility = View.VISIBLE
        }
    }

    fun onCurrenciesLoaded(currencyResponse: CurrencyResponse?) {
        if (currencyResponse?.dailyExRates?.currencies != null
            && currencyResponse.apiError == null
        ) {

            val adapter = CurrencyListAdapter(currencyResponse.dailyExRates.currencies.toMutableList())
            val dragHelper = DragHelper(adapter)
            val itemTouch = ItemTouchHelper(dragHelper)
            adapter.setTouch(itemTouch)

            currenciesListView.adapter = adapter
            itemTouch.attachToRecyclerView(currenciesListView)

            (currenciesListView.adapter as CurrencyListAdapter).notifyDataSetChanged()
            currenciesListView.visibility = View.VISIBLE
            progress.visibility = View.GONE

        } else {
            currenciesListView.visibility = View.GONE
            textNoInternet.visibility = View.VISIBLE
            currencyResponse?.apiError.let {
                textNoInternet.text =
                        "error: ${currencyResponse?.apiError?.statusCode} ${currencyResponse?.apiError?.message} "
            }
        }
    }
}
