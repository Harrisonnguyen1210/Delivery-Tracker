package fi.metropolia.deliverytracker.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Using Retrofit to create a request to the endpoint API with a BASE_URL
 */
class RequestsApiService {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RequestsApi::class.java)

    fun getRequests(): Single<List<Request>> {
        return api.getRequests()
    }
}