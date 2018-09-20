package bel.ink.bel.nibltest.repository

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface ForecastDAO {

	@get:Query("SELECT * FROM HistoryForecast")
	val all: List<HistoryForecast>

	@Query("SELECT * FROM HistoryForecast WHERE id = :id")
	fun getById(id: Long): HistoryForecast

	@Insert
	fun insertAll(forecasts: List<HistoryForecast>)

	@Insert
	fun insert(forecast: HistoryForecast)

	@Delete
	fun delete(forecast: HistoryForecast)

}
