package com.example.tmdbpeople.datasource.populardatasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.networkutils.ConnectionUtils
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.RetrofitService.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//DataSource class for popular persons pagination
class PersonDataSource(private val loadingLiveData: MutableLiveData<Int>, private val errorLiveData: MutableLiveData<String>) : PageKeyedDataSource<Int?, PersonModel?>() {

    //Function Loads the data for first time (page number 1)
    override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, PersonModel?>) {
        if (ConnectionUtils.isOnline()) {
            loadingLiveData.postValue(Constants.FIRST_LOAD_STATE)
            service.listPopularPersons(Constants.API_KEY_VALUE, Constants.FIRST_PAGE)
                .enqueue(object : Callback<PopularPersonResponse?> {
                    override fun onResponse(call: Call<PopularPersonResponse?>, response: Response<PopularPersonResponse?>) {
                        if (response.body()?.people != null) {
                            callback.onResult(response.body()?.people!!, null, Constants.FIRST_PAGE + 1)
                            loadingLiveData.postValue(Constants.SUCCESS_STATE)
                        } else {
                            errorLiveData.postValue(Constants.SERVER_ERROR_MESSAGE)
                        }
                    }

                    override fun onFailure(call: Call<PopularPersonResponse?>, t: Throwable) {
                        errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
                    }
                })
        } else {
            errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
    }

    //Function to load previous data before current page number
    override fun loadBefore(params: LoadParams<Int?>, callback: LoadCallback<Int?, PersonModel?>) {

    }

    //function to load more data when user scroll to bottom and check the page number to be lower than total pages
    //to increase it
    override fun loadAfter(params: LoadParams<Int?>, callback: LoadCallback<Int?, PersonModel?>) {
        if (ConnectionUtils.isOnline()) {
            loadingLiveData.postValue(Constants.LOAD_MORE_STATE)
            service.listPopularPersons(Constants.API_KEY_VALUE, params.key)
                .enqueue(object : Callback<PopularPersonResponse?> {
                    override fun onResponse(call: Call<PopularPersonResponse?>, response: Response<PopularPersonResponse?>) {
                        if (response.body()?.totalPages != null) {
                            val key = if (response.body()?.totalPages!! > params.key) params.key + 1 else null
                            callback.onResult(response.body()?.people!!, key)
                            loadingLiveData.postValue(Constants.SUCCESS_STATE)
                        } else {
                            errorLiveData.postValue(Constants.SERVER_ERROR_MESSAGE)
                        }
                    }

                    override fun onFailure(call: Call<PopularPersonResponse?>, t: Throwable) {
                        t.printStackTrace()
                        errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
                    }
                })
        } else {
            errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
    }
}