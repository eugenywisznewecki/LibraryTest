package bel.ink.bel.systemtechtest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class NetChecker(val context: Context) {

    fun isNetworkAwailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo

        if (netInfo != null && netInfo.isConnected) {
            return true
        }
        return false
    }
}