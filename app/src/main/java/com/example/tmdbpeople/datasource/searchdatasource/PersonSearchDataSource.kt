package com.example.tmdbpeople.datasource.searchdatasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.networkutils.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonSearchDataSource (private val loadCallback: com.example.tmdbpeople.networkutils.LoadCallback,private val query : String?) : PageKeyedDataSource<Int?, PersonModel?>() {
    override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, PersonModel?>) {
        if (query != null) {
            Log.i("PersonSearch",RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                FIRST_PAGE, query
            ).request().toString())
            loadCallback.onFirstLoad()
            RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                FIRST_PAGE, query
            ).enqueue(object :
                Callback<PopularPersonResponse?> {
                override fun onResponse(
                    call: Call<PopularPersonResponse?>,
                    response: Response<PopularPersonResponse?>
                ) {

                    if (response.body() != null) {
                        if (response.body()!!.persons != null) {
                            callback.onResult(
                                response.body()!!.persons!!,
                                null,
                                FIRST_PAGE + 1
                            )
                            loadCallback.onSuccess()
                        } else {
                            loadCallback.onError("Server Error")
                        }
                    } else {
                        loadCallback.onError("Server Error")
                    }
                }

                override fun onFailure(
                    call: Call<PopularPersonResponse?>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                    loadCallback.onError("Network Error")
                }
            })
        }
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int?>, callback: PageKeyedDataSource.LoadCallback<Int?, PersonModel?>) {
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
                    if (response.body() != null) {
                        if (response.body()!!.persons != null) {
                            callback.onResult(
                                response.body()!!.persons!!,
                                adjacentKey
                            )
                        }
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

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int?>, callback: PageKeyedDataSource.LoadCallback<Int?, PersonModel?>) {
        if (query != null) {
            loadCallback.onLoadMore()
            Log.i("PersonSearch",RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                params.key,
                query
            ).request().toString())
            RetrofitService.service.listPopularPersonsForSearch(
                Constants.API_KEY_VALUE,
                params.key,
                query
            ).enqueue(object : Callback<PopularPersonResponse?> {
                override fun onResponse(
                    call: Call<PopularPersonResponse?>,
                    response: Response<PopularPersonResponse?>
                ) {
                    if (response.body() != null) {
                        val key =
                            if (response.body()!!.totalPages!! > params.key) params.key + 1 else null
                        callback.onResult(response.body()!!.persons!!, key)
                        loadCallback.onSuccess()
                    } else {
                        loadCallback.onError("Server Error")
                    }
                }

                override fun onFailure(call: Call<PopularPersonResponse?>, t: Throwable) {
                    t.printStackTrace()
                    loadCallback.onError("Network Error")
                }
            })
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        private const val FIRST_PAGE = 1
    }
}