package com.example.tmdbpeople.datasource.searchdatasource

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.networkutils.ConnectionUtils
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonSearchDataSource(private val context: Context,private val query: String, private val loadingLiveData: MutableLiveData<Int>, private val errorLiveData: MutableLiveData<String> , private val compositeDisposable: CompositeDisposable?) : PageKeyedDataSource<Int?, PersonModel?>() {

    //Function Loads the data for first time (page number 1)
    override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, PersonModel?>) {
        if (ConnectionUtils.isOnline(context) && query.isNotEmpty()) {
            loadingLiveData.postValue(Constants.FIRST_LOAD_STATE)
            compositeDisposable?.add(RetrofitService.service.listPopularPersonsForSearch(Constants.API_KEY_VALUE,Constants.FIRST_PAGE,query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.people!!, null, Constants.FIRST_PAGE + 1)
                    loadingLiveData.postValue(Constants.SUCCESS_STATE)
                },{
                    errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
                }))
        } else if (query.isNotEmpty()) {
            errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
    }

    //Function to load previous data before current page number
    override fun loadBefore(params: LoadParams<Int?>, allback: LoadCallback<Int?, PersonModel?>) {

    }

    //Function to load more data when user scroll to bottom and check the page number to be lower than total pages
    //to increase it
    override fun loadAfter(params: LoadParams<Int?>, callback: LoadCallback<Int?, PersonModel?>) {
        if (ConnectionUtils.isOnline(context) && query.isNotEmpty()){
            loadingLiveData.postValue(Constants.LOAD_MORE_STATE)
            compositeDisposable?.add(RetrofitService.service.listPopularPersonsForSearch(Constants.API_KEY_VALUE, params.key, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val key = if (it.totalPages!! > params.key) params.key + 1 else null
                    callback.onResult(it.people!!, key)
                    loadingLiveData.postValue(Constants.SUCCESS_STATE)
                },{
                    errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
                }))
        } else if (query.isNotEmpty()) {
            errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
    }
}