package fi.metropolia.deliverytracker.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.google.android.gms.maps.model.LatLng

/**
 * Request entity represents delivery requests
 */
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

/**
 * User entity represents app's users
 */
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

/**
 * Route object represents google direction map
 */
class Route {
    var distance: Distance? = null
    var duration: Duration? = null
    var endAddress: String? = null
    var endLocation: LatLng? = null
    var startAddress: String? = null
    var startLocation: LatLng? = null

    var points: List<LatLng>? = null
}

/**
 * Estimated time to travel from starting point to destination point
 */
class Duration(var text: String, var value: Int)

/**
 * Distance between starting point and destination point
 */
class Distance(var text: String, var value: Int)