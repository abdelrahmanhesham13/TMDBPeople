package com.example.tmdbpeople.datasource.searchdatasource

import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonSearchDataSource(
    private val loadCallback: com.example.tmdbpeople.networkutils.LoadCallback,
    private val query: String?
) : PageKeyedDataSource<Int?, PersonDetailsResponse?>() {
    //Function Loads the data for first time (page number 1)
    override fun loadInitial(
        params: LoadInitialParams<Int?>,
        callback: LoadInitialCallback<Int?, PersonDetailsResponse?>
    ) {
        if (query != null) {
            loadCallback.onFirstLoad()
            RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                Constants.FIRST_PAGE, query
            ).enqueue(object :
                Callback<PopularPersonResponse?> {
                override fun onResponse(
                    call: Call<PopularPersonResponse?>,
                    response: Response<PopularPersonResponse?>
                ) {
                    if (response.body()?.persons != null) {
                        callback.onResult(
                            response.body()!!.persons!!,
                            null,
                            Constants.FIRST_PAGE + 1
                        )
                        loadCallback.onSuccess()
                    } else {
                        loadCallback.onError(Constants.SERVER_ERROR_MESSAGE)
                    }
                }

                override fun onFailure(
                    call: Call<PopularPersonResponse?>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                    loadCallback.onError(Constants.NETWORK_ERROR_MESSAGE)
                }
            })
        }
    }

    //Function to load previous data before current page number
    override fun loadBefore(
        params: LoadParams<Int?>,
        callback: LoadCallback<Int?, PersonDetailsResponse?>
    ) {
        if (query != null) {
            RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                params.key,
                query
            ).enqueue(object : Callback<PopularPersonResponse?> {
                override fun onResponse(
                    call: Call<PopularPersonResponse?>,
                    response: Response<PopularPersonResponse?>
                ) {
                    val adjacentKey = if (params.key > 1) params.key - 1 else null
                    if (response.body()?.persons != null) {
                        callback.onResult(
                            response.body()?.persons!!,
                            adjacentKey
                        )
                    }
                }

                override fun onFailure(
                    call: Call<PopularPersonResponse?>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
        }
    }

    //Function to load more data when user scroll to bottom and check the page number to be lower than total pages
    //to increase it
    override fun loadAfter(
        params: LoadParams<Int?>,
        callback: LoadCallback<Int?, PersonDetailsResponse?>
    ) {
        if (query != null) {
            loadCallback.onLoadMore()
            RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                params.key,
                query
            ).enqueue(object : Callback<PopularPersonResponse?> {
                override fun onResponse(
                    call: Call<PopularPersonResponse?>,
                    response: Response<PopularPersonResponse?>
                ) {
                    if (response.body()?.totalPages != null) {
                        val key =
                            if (response.body()!!.totalPages!! > params.key) params.key + 1 else null
                        callback.onResult(response.body()?.persons!!, key)
                        loadCallback.onSuccess()
                    } else {
                        loadCallback.onError(Constants.SERVER_ERROR_MESSAGE)
                    }
                }

                override fun onFailure(call: Call<PopularPersonResponse?>, t: Throwable) {
                    t.printStackTrace()
                    loadCallback.onError(Constants.NETWORK_ERROR_MESSAGE)
                }
            })
        }
    }
}