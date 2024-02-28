package com.game.doodlingdoods.internetConnection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

import kotlinx.coroutines.flow.*


object NetWorkConnectivityObserver {

    private lateinit var applicationContext: Context
    private lateinit var connectivityManager: ConnectivityManager

    private val _internetStatus = MutableStateFlow<CheckConnectivity.InternetStatus>(
        CheckConnectivity.InternetStatus.Unavailable
    )
    val internetStatus: StateFlow<CheckConnectivity.InternetStatus>
        get() = _internetStatus.asStateFlow()

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
        connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        observeConnectivity()
    }

    private fun observeConnectivity() {
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                updateInternetStatus(CheckConnectivity.InternetStatus.Available)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                updateInternetStatus(CheckConnectivity.InternetStatus.Losing)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                updateInternetStatus(CheckConnectivity.InternetStatus.Lost)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                updateInternetStatus(CheckConnectivity.InternetStatus.Unavailable)
            }
        })
    }

    private fun updateInternetStatus(status: CheckConnectivity.InternetStatus) {
        _internetStatus.value = status
    }

    fun isConnected(): Boolean {
        return _internetStatus.value == CheckConnectivity.InternetStatus.Available
    }
}
