package com.example.tmdbpeople.datasource.populardatasource

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.dagger.component.network.DaggerNetworkServiceComponent
import com.example.tmdbpeople.dagger.component.network.NetworkServiceComponent
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.utils.networkutils.ConnectionUtils
import com.example.tmdbpeople.utils.networkutils.Constants
import com.example.tmdbpeople.service.PersonsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//DataSource class for popular persons pagination
class PersonDataSource(private val context: Context, private val loadingLiveData: MutableLiveData<Constants.State>, private val errorLiveData: MutableLiveData<Int>, val compositeDisposable: CompositeDisposable?) : PageKeyedDataSource<Int?, PersonModel?>() {

    @Inject
    lateinit var service : PersonsService

    init {
        val networkServiceComponent : NetworkServiceComponent = DaggerNetworkServiceComponent.builder()
            .build()
        networkServiceComponent.inject(this)
    }

    //Function Loads the data for first time (page number 1)
    override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, PersonModel?>) {
        if (ConnectionUtils.isOnline(context)) {
            loadingLiveData.postValue(Constants.State.FIRST_LOAD_STATE)
            compositeDisposable?.add(service.listPopularPersons(Constants.FIRST_PAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.people!!, null, Constants.FIRST_PAGE + 1)
                    loadingLiveData.postValue(Constants.State.SUCCESS_STATE)
                }, {
                    errorLiveData.postValue(Constants.SERVER_ERROR_MESSAGE)
                }))
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
        if (ConnectionUtils.isOnline(context)) {
            loadingLiveData.postValue(Constants.State.LOAD_MORE_STATE)
            compositeDisposable?.add(service.listPopularPersons(params.key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val key = if (it?.totalPages!! > params.key) params.key + 1 else null
                    callback.onResult(it.people!!, key)
                    loadingLiveData.postValue(Constants.State.SUCCESS_STATE)
                },{
                    errorLiveData.postValue(Constants.SERVER_ERROR_MESSAGE)
                }))
        } else {
            errorLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
    }
}