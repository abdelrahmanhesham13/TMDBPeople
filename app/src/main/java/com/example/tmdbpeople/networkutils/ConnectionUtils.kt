package com.example.tmdbpeople.networkutils

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class ConnectionUtils {
    companion object {
        fun isOnline(): Boolean {
            return try {
                val urlc: HttpURLConnection =
                    URL("https://www.google.com").openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.setConnectTimeout(10000)
                urlc.connect()
                urlc.getResponseCode() === 200
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

    }
}