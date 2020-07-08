package com.example.tmdbpeople.networkutils

//Callback to notify about network status whether loading more data or first load or error or success
interface LoadCallback {
    fun onLoadMore()
    fun onFirstLoad()
    fun onError(message : String)
    fun onSuccess()
}