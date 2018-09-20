package bel.ink.bel.nibltest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class NetChecker(val context: Context) {


	fun checInternet(): Boolean {
		val wifiCheck: NetworkInfo?
		val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		wifiCheck = connectionManager.getActiveNetworkInfo()

		if (wifiCheck == null) {
			return false
		} else {
			when (wifiCheck.getType()) {
				(ConnectivityManager.TYPE_WIFI) -> {
					return true
				}
				(ConnectivityManager.TYPE_MOBILE) -> {
					return true
				}
			}
		}
		return false
	}

	//i' gonna use georeverse in intent servise
	fun isNetworkForBackgroundAwailable(): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val isNetAwailable = connectivityManager.activeNetworkInfo != null
		val isNetConnected = isNetAwailable && connectivityManager.activeNetworkInfo.isConnected
		return isNetConnected
	}
}