package bel.ink.bel.systemtechtest.serverApi

import bel.ink.bel.systemtechtest.entities.DailyExRates
import retrofit2.Call
import retrofit2.http.GET

interface InterfaceApi {

    @GET("XmlExRates.aspx")
    fun getData(): Call<DailyExRates>
}