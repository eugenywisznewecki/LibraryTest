package bel.ink.bel.systemtechtest.serverApi

import android.content.Context
import bel.ink.bel.systemtechtest.entities.ApiError
import bel.ink.bel.systemtechtest.entities.CurrencyResponse
import bel.ink.bel.systemtechtest.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import timber.log.Timber


class Communicator(val context: Context) {


    val interfaceApi: InterfaceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build().create(InterfaceApi::class.java)
    }


    fun getDailyCurrencies(): CurrencyResponse? {

        try {
            val call = interfaceApi.getData()
            val response = call.execute()
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    return CurrencyResponse(apiError = null, dailyExRates = result)
                }
                Timber.d("+++ isSuccessful")
            } else {
                //hardcode text TODO
                return CurrencyResponse(
                    apiError = ApiError(statusCode = response.code(), message = "server response error"),
                    dailyExRates = null
                )
            }
        } catch (e: Exception) {
            return CurrencyResponse(
                apiError = ApiError(statusCode = 0, message = e.localizedMessage),
                dailyExRates = null
            )
        }
        return null
    }

}