package bel.ink.bel.nibltest.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(HistoryForecast::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

	abstract fun forecastDao(): ForecastDAO

	companion object {

		private var INSTANCE: AppDatabase? = null

		@Synchronized
		fun getInstance(context: Context): AppDatabase {
			if (INSTANCE == null) {
				INSTANCE = Room.databaseBuilder(context.applicationContext,
						AppDatabase::class.java, "weather")
						.fallbackToDestructiveMigration()
						.build()
			}
			return INSTANCE!!
		}
		fun destroyInstance() {
			INSTANCE = null
		}
	}

}