package com.example.tmdbpeople.networkutils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class ConnectionUtils {
    companion object {
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

        }
    }
}