package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.Request
import fi.metropolia.deliverytracker.model.RequestsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RequestViewModel(application: Application) : BaseViewModel(application) {
    private val requestsApiService = RequestsApiService()
    private val disposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val requests = MutableLiveData<List<Request>>()
    val requestsLoadError = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            //implement data retrieving on a new thread (not on main thread)
            requestsApiService.getRequests()
                .subscribeOn(Schedulers.newThread())
                //display result on UI thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Request>>() {
                    override fun onError(e: Throwable) {
                        requestsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                    override fun onSuccess(requestsList: List<Request>) {
                        requestsRetreived(requestsList)
                    }
                })
        )
    }

    private fun requestsRetreived(dogList: List<Request>) {
        requests.value = dogList
        requestsLoadError.value = false
        loading.value = false
    }


}