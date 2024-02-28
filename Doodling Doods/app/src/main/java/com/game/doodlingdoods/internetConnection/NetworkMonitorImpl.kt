package com.game.doodlingdoods.internetConnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.runtime.ProvidableCompositionLocal


interface NetworkMonitor {
    val isNetAvailable: Boolean
    @RequiresApi(VERSION_CODES.N)
    fun registerNetworkChangeCallback(onNetAvailable: () -> Unit)
}

class NetworkMonitorImpl(val globalContext: Context): NetworkMonitor {
    private var netAvailabilityFlagAboveAndroidN = false

    override val isNetAvailable: Boolean
        get() {
            return if (VERSION.SDK_INT < VERSION_CODES.N) {
                val connectivityManager = globalContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager.activeNetworkInfo?.isConnected ?: false
            } else {
                netAvailabilityFlagAboveAndroidN
            }
        }

    @RequiresApi(VERSION_CODES.N)
    override fun registerNetworkChangeCallback(onNetAvailable: () -> Unit) {
        val connectivityManager = globalContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback: ConnectivityManager.NetworkCallback
        networkCallback = object: ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                netAvailabilityFlagAboveAndroidN = false
//                KotlinUtils.log("net",
//                    "registerNetworkChangeCallback onLost")
                super.onLost(network)
            }

            override fun onAvailable(network: Network) {
                netAvailabilityFlagAboveAndroidN = true
//                KotlinUtils.log("net",
//                    "registerNetworkChangeCallback onAvailable")

                onNetAvailable.invoke()
                super.onAvailable(network)
            }

            override fun onUnavailable() {
                netAvailabilityFlagAboveAndroidN = false
//                KotlinUtils.log("net",
//                    "registerNetworkChangeCallback onUnavailable")
                super.onUnavailable()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}