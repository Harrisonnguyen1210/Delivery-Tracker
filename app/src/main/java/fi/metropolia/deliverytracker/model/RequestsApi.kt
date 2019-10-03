package fi.metropolia.deliverytracker.model

import io.reactivex.Single
import retrofit2.http.GET

interface RequestsApi {
    @GET("Harrisonnguyen1210/API/master/DeliveryTracker.json")
    fun getRequests(): Single<List<Request>>
}