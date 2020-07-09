package com.example.tmdbpeople.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object PrintUtils {
    fun printMessage(ctx : Context, message : String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Toast.makeText(ctx , message , Toast.LENGTH_LONG).show()
        }
    }

    fun printMessage(ctx : Context, resId : Int) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Toast.makeText(ctx , resId , Toast.LENGTH_LONG).show()
        }
    }
}