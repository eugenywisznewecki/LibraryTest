package bel.ink.bel.nibltest.repository

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "HistoryForecast")
data class HistoryForecast (
		@PrimaryKey(autoGenerate = true)
		var id: Long?,
		@ColumnInfo(name = "dateOfGetting") var dateOfGetting: Long,
		@ColumnInfo(name = "latitute") var latitute: Double,
		@ColumnInfo(name = "longiture") var longiture: Double,
		@ColumnInfo(name = "city") var city: String,
		@ColumnInfo(name = "date") var date: Long,
		@ColumnInfo(name = "description") var description: String,
		@ColumnInfo(name = "high") var high: Int,
		@ColumnInfo(name = "low") var low: Int,
		@ColumnInfo(name = "speed") var speed: Float
): Serializable {
	constructor() : this(null, 0L, 0.0, 0.0, "", 0L,
			"", 0, 0, 0f)
}