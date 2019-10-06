package fi.metropolia.deliverytracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RequestDao {
    @Insert
    suspend fun insertAll(requestList: List<Request>): List<Long>

    @Query("SELECT * FROM Request")
    suspend fun getAllRequests(): List<Request>

    @Query("SELECT * FROM Request WHERE id = :requestId")
    suspend fun getRequest(requestId: Int): Request

    @Query("DELETE FROM Request")
    suspend fun deleteAllRequests()

    @Query("UPDATE Request SET transporterName = :transporterName, status = :status WHERE id = :requestId")
    suspend fun acceptRequest(status: String, transporterName: String, requestId: Int)
}