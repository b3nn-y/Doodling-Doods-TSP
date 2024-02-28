package com.game.doodlingdoods.internetConnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.ProvidableCompositionLocal
//import com.game.doodlingdoods.DoodlingDoodsApplicaion
//import com.game.doodlingdoods.globalContext


//interface NetworkMonitor {
//    val isNetAvailable: Boolean
//    @RequiresApi(VERSION_CODES.N)
//    fun registerNetworkChangeCallback()
//}
//
//object NetworkMonitorImpl: NetworkMonitor {
//    private var netAvailabilityFlagAboveAndroidN = false
//
//    override val isNetAvailable: Boolean
//        get() {
//            return if (VERSION.SDK_INT < VERSION_CODES.N) {
//                Log.i("Network", "Getter")
//                val connectivityManager = globalContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//                connectivityManager.activeNetworkInfo?.isConnected ?: false
//            } else {
//                Log.i("Network", "Getter else")
//                netAvailabilityFlagAboveAndroidN
//            }
//        }
//
//    @RequiresApi(VERSION_CODES.N)
//    override fun registerNetworkChangeCallback() {
//        val connectivityManager = globalContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkCallback: ConnectivityManager.NetworkCallback
//        networkCallback = object: ConnectivityManager.NetworkCallback() {
//            override fun onLost(network: Network) {
//                Log.i("Network", "On lost")
//                netAvailabilityFlagAboveAndroidN = false
////                KotlinUtils.log("net",
////                    "registerNetworkChangeCallback onLost")
//                super.onLost(network)
//            }
//
//            override fun onAvailable(network: Network) {
//                Log.i("Network", "On available")
//                netAvailabilityFlagAboveAndroidN = true
////                KotlinUtils.log("net",
////                    "registerNetworkChangeCallback onAvailable")
//                super.onAvailable(network)
//            }
//
//            override fun onUnavailable() {
//                Log.i("Network", "On unavailable")
//                netAvailabilityFlagAboveAndroidN = false
////                KotlinUtils.log("net",
////                    "registerNetworkChangeCallback onUnavailable")
//                super.onUnavailable()
//            }
//        }
//
//        connectivityManager.registerDefaultNetworkCallback(networkCallback)
//    }
//}