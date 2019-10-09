package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.Request
import fi.metropolia.deliverytracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val requestDetail = MutableLiveData<Request>()
    val startDeliveryState = MutableLiveData<Int>()
    private var prefHelper = SharedPreferencesHelper(getApplication())

    fun fetch(requestId: Int) {
        launch {
            val request = DeliveryTrackDatabase(getApplication()).requestDao().getRequest(requestId)
            requestDetail.value = request
            when {
                //No transporter accepted yet
                request.transporterName == null -> startDeliveryState.value = 0
                //User has accepted the request
                request.transporterName == prefHelper.getUsername() -> startDeliveryState.value = 1
                //Somebody has accepted request
                else -> startDeliveryState.value = 2
            }
        }
    }
}