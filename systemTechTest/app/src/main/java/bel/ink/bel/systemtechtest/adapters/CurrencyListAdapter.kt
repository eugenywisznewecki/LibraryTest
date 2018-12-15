package bel.ink.bel.systemtechtest.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bel.ink.bel.systemtechtest.R
import bel.ink.bel.systemtechtest.entities.Currency
import kotlinx.android.synthetic.main.row_currency.view.*
import timber.log.Timber


class CurrencyListAdapter(private val currenciesList: MutableList<Currency>) :
    RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>(), ActionComplete {

    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency, parent, false)
        return CurrencyListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(currenciesList[position]) {
            holder.itemView.text.text = "$charCode ${rate.toString()} BYN $name за ${scale.toString()} ед."

            holder.itemView.text.setOnTouchListener { view, motionEvent ->
                if (motionEvent.actionMasked == android.view.MotionEvent.ACTION_DOWN) {
                    touchHelper.startDrag(holder)
                }
                return@setOnTouchListener false
            }
        }
    }

    override fun getItemCount(): Int {
        Timber.d("+++ ${currenciesList.size}")
        return currenciesList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        val currencyTarget = currenciesList[oldPosition]
        val currencyNew = currencyTarget
        currenciesList.removeAt(oldPosition)
        currenciesList.add(newPosition, currencyNew)


        notifyItemMoved(oldPosition, newPosition)
    }

    fun setTouch(itemTouch: ItemTouchHelper) {
        this.touchHelper = itemTouch
    }
}