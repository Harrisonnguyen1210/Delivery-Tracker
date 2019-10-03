package fi.metropolia.deliverytracker.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("published_at")
    val startingDate: String,
    @SerializedName("deliver_before")
    val deadline: String,
    @SerializedName("deliver_to")
    val destination: String,
    @SerializedName("customer")
    val customer: String,
    @SerializedName("info")
    val info: String
)