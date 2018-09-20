/*
package bel.ink.bel.nibltest.di

import android.app.Application
import android.content.Context
import bel.ink.bel.nibltest.utils.BASE_URL
import bel.ink.bel.nibltest.utils.NetChecker
import bel.ink.bel.nibltest.weatherServerApi.Communicator
import bel.ink.bel.nibltest.weatherServerApi.WeatherAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {


	constructor(app: Application) {
		this.context = app
	}

	private val context: Context

	@Provides
	fun providesContext(): Context = context

	@Provides
	@Singleton
	fun provideCommunicator(context: Context): Communicator = Communicator(context)

	@Provides
	@Singleton
	fun provideWifiChecker(context: Context): NetChecker = NetChecker(context)

	@Provides
	@Singleton
	fun provideUrl() = BASE_URL

	@Provides
	@Singleton
	fun provideRetrofit(url: String) = Retrofit.Builder()
			.baseUrl(url)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

	@Provides
	@Singleton
	fun provideWeatherAPI(retrofit: Retrofit) = retrofit.create(WeatherAPI::class.java)


}*/
