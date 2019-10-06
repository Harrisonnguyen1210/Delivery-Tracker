package fi.metropolia.deliverytracker.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class Request(
    @PrimaryKey
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
    val info: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val transporterName: String?
)

@Entity
data class User(
    @PrimaryKey
    val userName: String,
    val password: String
)

class UserWithRequests(
    @Embedded
    val user: User? = null,

    @Relation(parentColumn = "userName", entityColumn = "transporterName")
    val requestList: List<Request>
)