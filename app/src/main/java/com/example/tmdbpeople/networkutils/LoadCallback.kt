package com.example.tmdbpeople.networkutils

interface LoadCallback {
    fun onLoadMore()
    fun onFirstLoad()
    fun onError(message : String)
    fun onSuccess()
}