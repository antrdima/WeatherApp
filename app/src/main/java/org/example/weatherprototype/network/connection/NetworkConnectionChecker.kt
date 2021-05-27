package org.example.weatherprototype.network.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkConnectionChecker @Inject constructor(@ApplicationContext private val context: Context) {
    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            return isNetworkConnectedDeprecated(cm)
        }
    }

    @Suppress("DEPRECATION")
    private fun isNetworkConnectedDeprecated(cm: ConnectivityManager): Boolean {
        val activeNetwork = cm.activeNetworkInfo ?: return false
        return activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
            activeNetwork.type == ConnectivityManager.TYPE_MOBILE
    }
}
