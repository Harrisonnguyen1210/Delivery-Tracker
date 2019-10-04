package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.Request
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val requestDetail = MutableLiveData<Request>()

    fun fetch(requestId: Int) {
        launch {
            val request = DeliveryTrackDatabase(getApplication()).requestDao().getRequest(requestId)
            requestDetail.value = request
        }
    }
}