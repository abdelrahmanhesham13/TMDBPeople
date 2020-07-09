package com.example.tmdbpeople.utils.networkutils

import android.content.Context
import android.net.ConnectivityManager


class ConnectionUtils {
    companion object {
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
        }
    }
}