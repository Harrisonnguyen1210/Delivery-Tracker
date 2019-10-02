package fi.metropolia.deliverytracker.model

data class Request(
    val id: Int,
    val status: String,
    val startingDate: String,
    val deadline: String,
    val destination: String,
    val customer: String,
    val info: String
)