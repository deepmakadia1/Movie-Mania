package com.moviemania.util


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

object Util {

    @SuppressLint("MissingPermission")
    fun isNetworkConnected(context: Context): Boolean {

        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = checkNetworkConnection(connectivityManager, connectivityManager.activeNetwork)
        } else {
            val networks = connectivityManager.allNetworks
            for (network in networks) {
                if (checkNetworkConnection(connectivityManager, network)) {
                    result = true
                }
            }
        }

        return result
    }

    private fun checkNetworkConnection(
        connectivityManager: ConnectivityManager,
        network: Network?
    ): Boolean {

        connectivityManager.getNetworkCapabilities(network)?.also {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            }
        }
        return false

    }


}