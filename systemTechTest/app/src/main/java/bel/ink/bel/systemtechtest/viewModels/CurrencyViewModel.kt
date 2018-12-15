package bel.ink.bel.systemtechtest.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import bel.ink.bel.systemtechtest.entities.CurrencyResponse
import bel.ink.bel.systemtechtest.serverApi.Communicator
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import timber.log.Timber


class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    //too small to use DI
    private val communicator by lazy { Communicator(application.applicationContext) }


    var liveData: MutableLiveData<CurrencyResponse?>? = null

    fun getCurrencies(): MutableLiveData<CurrencyResponse?>? {
        if (liveData == null) {
            liveData = MutableLiveData()
            loadCurrencies()
        }
        return liveData
    }


    private fun loadCurrencies() {
        launch(UI) {
            val result = async {
                communicator.getDailyCurrencies()
            }
            try {
                liveData?.value = result.await()
            } catch (e: Exception) {
                Timber.d("+++  $e")
            }
        }
    }


}