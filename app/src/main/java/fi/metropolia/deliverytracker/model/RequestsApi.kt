package fi.metropolia.deliverytracker.model

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Api interface to fetch delivery request, return a single value response based on Reactive Pattern
 */
interface RequestsApi {
    @GET("Harrisonnguyen1210/API/master/DeliveryTracker.json")
    fun getRequests(): Single<List<Request>>
}